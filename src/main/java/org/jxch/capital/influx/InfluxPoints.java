package org.jxch.capital.influx;

import cn.hutool.core.annotation.AnnotationUtil;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.utils.ReflectionsU;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class InfluxPoints {

    public static String getMeasurement(@NotNull Class<?> clazz) {
        return clazz.getAnnotation(InfluxPointMeasurement.class).value();
    }

    public static String getMeasurement(@NotNull Object obj) {
        return getMeasurement(obj.getClass());
    }

    public static List<Field> getAllTagFields(@NotNull Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields()).filter(field -> AnnotationUtil.hasAnnotation(field, InfluxPointTag.class)).toList();
    }

    public static boolean hasAnyNonNullTagValue(@NotNull Object obj) {
        return getAllTagFields(obj).stream().anyMatch(field -> Objects.nonNull(ReflectionsU.getFieldValueNullable(obj, field)));
    }

    public static Map<String, String> getNonNullTags(@NotNull Object obj) {
        return getAllTagFields(obj).stream().filter(field -> Objects.nonNull(ReflectionsU.getFieldValueNullable(obj, field)))
                .collect(Collectors.toMap(Field::getName, field -> ReflectionsU.getFieldValueNotNull(obj, field).toString()));
    }

    public static Map<String, String> getTags(@NotNull Object obj) {
        return getAllTagFields(obj).stream().collect(Collectors.toMap(Field::getName, field -> ReflectionsU.getFieldValueNotNull(obj, field).toString()));
    }

    private static Field getInfluxPointTimeField(@NotNull Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields()).filter(field -> AnnotationUtil.hasAnnotation(field, InfluxPointTime.class))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("必须使用 InfluxPointTime 注解标注 time 字段"));
    }

    @NotNull
    public static Long getTime(@NotNull Object obj) {
        long ms = ((Date) ReflectionsU.getFieldValueNotNull(obj, getInfluxPointTimeField(obj))).getTime();
        return switch (getWritePrecision(obj)) {
            case MS -> ms;
            case S -> TimeUnit.MILLISECONDS.toSeconds(ms);
            case US -> TimeUnit.MILLISECONDS.toMicros(ms);
            case NS -> TimeUnit.MILLISECONDS.toNanos(ms);
        };
    }

    public static WritePrecision getWritePrecision(@NotNull Object obj) {
        return getInfluxPointTimeField(obj).getAnnotation(InfluxPointTime.class).writePrecision();
    }

    public static Map<String, Object> getFields(@NotNull Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields()).filter(field ->
                (AnnotationUtil.hasAnnotation(field, InfluxPointField.class) && !AnnotationUtil.getAnnotation(field, InfluxPointField.class).ignore()) ||
                        (!AnnotationUtil.hasAnnotation(field, InfluxPointField.class) &&
                                (!AnnotationUtil.hasAnnotation(field, InfluxPointTag.class) && !AnnotationUtil.hasAnnotation(field, InfluxPointTime.class)))
        ).collect(Collectors.toMap(Field::getName, field -> ReflectionsU.getFieldValueNotNull(obj, field)));
    }

    public static boolean hasAnyNonNullFieldValue(@NotNull Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields()).filter(field ->
                (AnnotationUtil.hasAnnotation(field, InfluxPointField.class) && !AnnotationUtil.getAnnotation(field, InfluxPointField.class).ignore()) ||
                        (!AnnotationUtil.hasAnnotation(field, InfluxPointField.class) &&
                                (!AnnotationUtil.hasAnnotation(field, InfluxPointTag.class) && !AnnotationUtil.hasAnnotation(field, InfluxPointTime.class)))
        ).anyMatch(field -> Objects.nonNull(ReflectionsU.getFieldValueNullable(obj, field)));
    }

    public static Map<String, Object> getNonNullFields(@NotNull Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields()).filter(field ->
                        (AnnotationUtil.hasAnnotation(field, InfluxPointField.class) && !AnnotationUtil.getAnnotation(field, InfluxPointField.class).ignore()) ||
                                (!AnnotationUtil.hasAnnotation(field, InfluxPointField.class) &&
                                        (!AnnotationUtil.hasAnnotation(field, InfluxPointTag.class) && !AnnotationUtil.hasAnnotation(field, InfluxPointTime.class)))
                ).filter(field -> Objects.nonNull(ReflectionsU.getFieldValueNullable(obj, field)))
                .collect(Collectors.toMap(Field::getName, field -> ReflectionsU.getFieldValueNotNull(obj, field)));
    }

    @NotNull
    public static Point toInfluxPoint(Object obj) {
        return Point.measurement(getMeasurement(obj))
                .addTags(getTags(obj))
                .addFields(getFields(obj))
                .time(getTime(obj), getWritePrecision(obj));
    }

    @SneakyThrows
    public static <POINT> List<POINT> toPointDto(@NotNull List<FluxTable> fluxTables, Class<POINT> clazz) {
        Map<Instant, POINT> pointMap = fluxTables.stream().flatMap(fluxTable -> fluxTable.getRecords().stream())
                .collect(Collectors.toMap(FluxRecord::getTime, record -> ReflectionsU.newInstance(clazz), (a, b) -> a));

        for (FluxTable fluxTable : fluxTables) {
            for (FluxRecord record : fluxTable.getRecords()) {
                POINT point = pointMap.get(record.getTime());
                Date time = Date.from(Objects.requireNonNull(record.getTime()));
                ReflectionsU.setFieldValue(point, getInfluxPointTimeField(point).getName(), time);

                String field = Objects.requireNonNull(record.getValueByKey("_field")).toString();
                Object value = record.getValueByKey("_value");
                ReflectionsU.setFieldValue(point, field, value);

                for (Field tagField : getAllTagFields(point)) {
                    ReflectionsU.setFieldValue(point, tagField.getName(), record.getValueByKey(tagField.getName()));
                }
            }
        }

        return pointMap.values().stream().toList();
    }

}
