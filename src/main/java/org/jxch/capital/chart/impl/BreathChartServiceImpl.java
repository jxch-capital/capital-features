package org.jxch.capital.chart.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.Charts;
import org.jxch.capital.chart.Colors;
import org.jxch.capital.chart.dto.BreathChartParam;
import org.jxch.capital.chart.dto.BreathChartRes;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathParam;
import org.jxch.capital.server.BreathService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BreathChartServiceImpl implements ChartPngService<BreathChartParam, BreathChartRes> {
    private final BreathService breathService;

    @Override
    @SneakyThrows
    public BreathChartRes chart(@NotNull BreathChartParam param) {
        BreathDto breath = breathService.getBreath(BreathParam.builder().length(param.getLength()).build());
        List<String> types = breath.getTypes();
        Integer cellSize = param.getCellSize();
        BufferedImage image = new BufferedImage((param.getLength() + 1) * cellSize, types.size() * cellSize, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        for (int row = 0; row < types.size(); row++) {
            g.setFont(new Font("default", Font.PLAIN, 12));
            List<Integer> scores = breath.getItems(types.get(row)).stream().map(BreathDto.Item::getScore).toList();
            for (int col = 0; col < scores.size(); col++) {
                Color color = Colors.getColorFromGradientByRTG(scores.get(col) - 50, 50);
                g.setColor(color);
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(scores.get(col)), col * cellSize + cellSize / 6, row * cellSize + (int) (cellSize / 1.3));
            }
            g.setFont(new Font("default", Font.PLAIN, 9));
            g.setColor(Color.DARK_GRAY);
            g.fillRect(scores.size() * cellSize, row * cellSize, cellSize, cellSize);
            g.setColor(new Color(255, 255, 255, 60));
            g.drawString(types.get(row), scores.size() * cellSize, row * cellSize + (int) (cellSize / 1.3));
        }
        g.dispose();

        return BreathChartRes.builder()
                .path(Charts.createAwtPng(param.getIdentifier() + getClass().getSimpleName() + param.getTimestamp(), image))
                .timestamp(param.getTimestamp())
                .build();
    }

    @Override
    public void clear(@NotNull BreathChartRes res) {
        Charts.delete(res.getPath());
    }

}
