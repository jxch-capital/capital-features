package org.jxch.capital.influx;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.DeletePredicateRequest;
import lombok.SneakyThrows;
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

    @SneakyThrows
    default void writeAll(@NotNull List<POINT> points) {
        InfluxDBConfig influxDBConfig = SpringUtil.getBean(InfluxDBConfig.class);
        for (List<POINT> pointList : Lists.partition(points, influxDBConfig.getBatch())) {
            try {
                influxDBConfig.getInfluxWriteSemaphore().acquire();
                getInfluxDBClient().getWriteApiBlocking().writePoints(pointList.stream().map(InfluxPoints::toInfluxPoint).toList());
            } finally {
                influxDBConfig.getInfluxWriteSemaphore().release();
            }
        }
    }

    default List<POINT> queryByExampleTagEntityAndTimeBetween(POINT queryExampleEntity, @NotNull Date startTime, @NotNull Date endTime, Class<POINT> resultClazz) {
        StringBuilder fluxQuery = new StringBuilder(String.format("""
                from(bucket: "%s")
                    |> range(start: time(v:"%s"), stop: time(v:"%s"))
                    |> filter(fn: (r) => r._measurement == "%s")
                """, getInfluxDBConfig().getBucket(), DateUtil.format(startTime, "yyyy-MM-dd'T'HH:mm:ss'Z'"), DateUtil.format(endTime, "yyyy-MM-dd'T'HH:mm:ss'Z'"), InfluxPoints.getMeasurement(resultClazz)));

        if (InfluxPoints.hasAnyNonNullTagValue(queryExampleEntity)) {
            for (Map.Entry<String, String> entry : InfluxPoints.getNonNullTags(queryExampleEntity).entrySet()) {
                fluxQuery.append(String.format("    |> filter(fn: (r) => r.%s == \"%s\")\n", entry.getKey(), entry.getValue()));
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
