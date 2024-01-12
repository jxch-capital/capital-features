package org.jxch.capital.chart.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class StockPoolChartRes implements ChartRes {
    private Map<Long, Map<String, String>> stockPngPath = new HashMap<>();
    private Long timestamp = Calendar.getInstance().getTime().getTime();

    public void addPath(Long stockPoolId, String stock, String path) {
        stockPngPath.putIfAbsent(stockPoolId, new HashMap<>());
        stockPngPath.get(stockPoolId).put(stock, path);
    }

    public String getPath(Long stockPoolId, String stock) {
        return stockPngPath.get(stockPoolId).get(stock) + timestamp;
    }

    public List<String> getAllPath() {
        List<String> paths = new ArrayList<>();
        for (Map.Entry<Long, Map<String, String>> stockIdEntry : stockPngPath.entrySet()) {
            for (Map.Entry<String, String> entry : stockIdEntry.getValue().entrySet()) {
                paths.add(stockIdEntry.getKey() + entry.getKey() + timestamp);
            }
        }
        return paths;
    }

    public List<String> getStockPoolPath(Long stockPoolId) {
        List<String> paths = new ArrayList<>();
        for (Map.Entry<String, String> entry : stockPngPath.get(stockPoolId).entrySet()) {
            paths.add(stockPoolId + entry.getKey() + timestamp);
        }
        return paths;
    }

    public List<Long> getStockPools() {
        return stockPngPath.keySet().stream().toList();
    }

}
