package org.jxch.capital.controller.view.model3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.TrainConfigDto;
import org.jxch.capital.domain.vo.Model3SignalParam;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.Model3PredictionCompleteService;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.train.TrainConfigService;
import org.jxch.capital.utils.EChartsU;
import org.jxch.capital.utils.KLineSignals;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/model3_signal_view")
public class Model3SignalViewController {
    private final Model3PredictionCompleteService model3PredictionCompleteService;
    private final Model3Management model3Management;
    private final TrainConfigService trainConfigService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("model/model3_signal_view_index");
        modelAndView.addObject("param", new Model3SignalParam());
        modelAndView.addObject("all_model", model3Management.allModelMetaData());
        modelAndView.addObject("all_configs", trainConfigService.findAll().stream().collect(Collectors.toMap(TrainConfigDto::getId, Function.identity())));
        return modelAndView;
    }

    @RequestMapping("/signal")
    public ModelAndView signal(@NotNull @ModelAttribute Model3SignalParam param) {
        Model3PredictRes model3PredictRes = model3PredictionCompleteService.predictionCarry(param.getModelNames(), param.getPredictionParam());
        List<KLine> kLine = model3PredictRes.getKLine();
        List<KLineSignal> kLineSignal = KLineSignals.toKLineSignal(model3PredictRes, param);
        // todo 此页面写成模板
        ModelAndView modelAndView = new ModelAndView("model/model3_signal_view_index");
        modelAndView.addObject("param", param);
        modelAndView.addObject("kLines", kLine);
        modelAndView.addObject("ema20", EChartsU.emaXEChartsDto(kLine, 20));
        modelAndView.addObject("signals", KLineSignals.toEChartDtoSignals(kLineSignal));
        modelAndView.addObject("actionSignals", KLineSignals.toEChartDtoActionSignals(kLineSignal));
        modelAndView.addObject("statistics", KLineSignals.toKLineSignalStatistics(kLineSignal, param.getLimitAbs()));
        modelAndView.addObject("all_model", model3Management.allModelMetaData());
        modelAndView.addObject("all_configs", trainConfigService.findAll().stream().collect(Collectors.toMap(TrainConfigDto::getId, Function.identity())));
        return modelAndView;
    }

}
