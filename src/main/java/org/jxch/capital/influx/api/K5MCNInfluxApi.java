package org.jxch.capital.influx.api;

import org.jxch.capital.influx.InfluxApi;
import org.jxch.capital.influx.InfluxFluxQuery;
import org.jxch.capital.influx.point.K5MCNInfluxPoint;

import java.util.List;

public interface K5MCNInfluxApi extends InfluxApi<K5MCNInfluxPoint> {

    @InfluxFluxQuery(flux = """
            from(bucket: "?1")
                    |> range(start: ?2, stop: ?3)
                    |> filter(fn: (r) => r._measurement == "?4")
            """)
     List<K5MCNInfluxPoint> queryTest(String bucket, Long start, Long stop, String measurement);

}
