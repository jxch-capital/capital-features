package org.jxch.capital.influx;

import cn.hutool.extra.spring.SpringUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.DeletePredicateRequest;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.config.InfluxDBConfig;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InfluxApi<POINT> {

    default InfluxDBClient getInfluxDBClient() {
        return SpringUtil.getBean(InfluxDBClient.class);
    }

    default InfluxDBConfig getInfluxDBConfig() {
        return SpringUtil.getBean(InfluxDBConfig.class);
    }

    default void write(@NotNull POINT point) {
        getInfluxDBClient().getWriteApiBlocking().writePoint(InfluxPoints.toInfluxPoint(point));
    }

    default void writeAll(@NotNull List<POINT> points) {
        getInfluxDBClient().getWriteApiBlocking().writePoints(points.stream().map(InfluxPoints::toInfluxPoint).toList());
    }

    default List<POINT> queryByTagsAndTimeBetween(@NotNull Map<String, String> tags, @NotNull Date startTime, @NotNull Date endTime, Class<POINT> resultClazz) {
        StringBuilder fluxQuery = new StringBuilder(String.format("""
                from(bucket: "%s")
                    |> range(start: %s, stop: %s)
                    |> filter(fn: (r) => r._measurement == "%s")
                """, getInfluxDBConfig().getBucket(), startTime.getTime(), endTime.getTime(), InfluxPoints.getMeasurement(resultClazz)));

        for (Map.Entry<String, String> entry : tags.entrySet()) {
            fluxQuery.append(String.format("\n    |> filter(fn: (r) => r.%s == \"%s\")\n", entry.getKey(), entry.getValue()));
        }

        return InfluxPoints.toPointDto(getInfluxDBClient().getQueryApi().query(fluxQuery.toString(), getInfluxDBConfig().getOrg()), resultClazz);
    }

    default List<POINT> queryByExampleTagEntityAndTimeBetween(POINT queryExampleEntity, @NotNull Date startTime, @NotNull Date endTime, Class<POINT> resultClazz) {
        StringBuilder fluxQuery = new StringBuilder(String.format("""
                from(bucket: "%s")
                    |> range(start: %s, stop: %s)
                    |> filter(fn: (r) => r._measurement == "%s")
                """, getInfluxDBConfig().getBucket(), startTime.getTime(), endTime.getTime(), InfluxPoints.getMeasurement(resultClazz)));

        if (InfluxPoints.hasAnyNonNullTagValue(queryExampleEntity)) {
            for (Map.Entry<String, String> entry : InfluxPoints.getNonNullTags(queryExampleEntity).entrySet()) {
                fluxQuery.append(String.format("\n    |> filter(fn: (r) => r.%s == \"%s\")\n", entry.getKey(), entry.getValue()));
            }
        }

        return InfluxPoints.toPointDto(getInfluxDBClient().getQueryApi().query(fluxQuery.toString(), getInfluxDBConfig().getOrg()), resultClazz);
    }

    default void deletePointsByTimeBetween(@NotNull Date startTime, @NotNull Date endTime, Class<POINT> pointClazz) {
        DeletePredicateRequest request = new DeletePredicateRequest()
                .start(startTime.toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime())
                .stop(endTime.toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime())
                .predicate(String.format("_measurement=\"%s\"", InfluxPoints.getMeasurement(pointClazz))); // 删除条件

        getInfluxDBClient().getDeleteApi().delete(request, getInfluxDBConfig().getOrg(), getInfluxDBConfig().getBucket());
    }

    default void deletePointByTime(@NotNull Date time, Class<POINT> pointClazz) {
        deletePointsByTimeBetween(time, time, pointClazz);
    }

}
