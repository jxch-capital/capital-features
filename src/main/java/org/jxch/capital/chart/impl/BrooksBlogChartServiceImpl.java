package org.jxch.capital.chart.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.Charts;
import org.jxch.capital.chart.dto.BrooksBlogChartParam;
import org.jxch.capital.chart.dto.BrooksBlogChartRes;
import org.jxch.capital.server.BrooksService;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrooksBlogChartServiceImpl implements ChartPngService<BrooksBlogChartParam, BrooksBlogChartRes> {
    private final BrooksService brooksService;

    @Override
    @SneakyThrows
    public BrooksBlogChartRes chart(@NotNull BrooksBlogChartParam param) {
        BrooksBlogChartRes res = new BrooksBlogChartRes().setTimestamp(param.getTimestamp())
                .setPath(Charts.chartPath(param.getIdentifier() + getClass().getSimpleName() + param.getTimestamp()));
        URL url = Optional.ofNullable(brooksService.newBlogFirstChartUrl())
                .orElse(new URL("https://img2020.cnblogs.com/blog/1082680/202103/1082680-20210302183811933-1660667868.png"));

        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream(res.getPath())) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }

        return res;
    }

    @Override
    public void clear(@NotNull BrooksBlogChartRes res) {
        Charts.delete(res.getPath());
    }

}
