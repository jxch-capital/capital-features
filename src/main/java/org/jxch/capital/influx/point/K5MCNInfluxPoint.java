package org.jxch.capital.influx.point;

import com.influxdb.client.domain.WritePrecision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.influx.InfluxPointMeasurement;
import org.jxch.capital.influx.InfluxPointTag;
import org.jxch.capital.influx.InfluxPointTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@InfluxPointMeasurement("k_5m_cn")
public class K5MCNInfluxPoint {
    @Builder.Default
    @InfluxPointTag
    private String code = null;
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @InfluxPointTime(writePrecision = WritePrecision.MS)
    private Date time = null;
    @Builder.Default
    private Double open = null;
    @Builder.Default
    private Double high = null;
    @Builder.Default
    private Double low = null;
    @Builder.Default
    private Double close = null;
    @Builder.Default
    private Long volume = null;
    @Builder.Default
    private Long amount = null;
}
