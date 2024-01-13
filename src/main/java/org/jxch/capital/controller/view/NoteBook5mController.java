package org.jxch.capital.controller.view;

import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.NoteBook5mDto;
import org.jxch.capital.domain.dto.NoteBook5mParam;
import org.jxch.capital.exception.StockServiceNoResException;
import org.jxch.capital.notebook.NoteBook5mService;
import org.jxch.capital.server.IndexService;
import org.jxch.capital.stock.IntervalEnum;
import org.jxch.capital.stock.StockService;
import org.jxch.capital.utils.DateTimeU;
import org.jxch.capital.utils.IndicesU;
import org.jxch.capital.utils.KLines;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/notebook_5m")
@RequiredArgsConstructor
public class NoteBook5mController {
    private final NoteBook5mService noteBook5mService;
    private final StockService stockService;
    private final IndexService indexService;

    @GetMapping("/index")
    public ModelAndView index() {
        return modelAndView(new NoteBook5mParam());
    }

    @RequestMapping("/find")
    public ModelAndView find(@ModelAttribute NoteBook5mParam param) {
        return modelAndView(param);
    }

    @RequestMapping("/save")
    public ModelAndView save(@ModelAttribute @NonNull NoteBook5mDto dto) {
        noteBook5mService.save(Collections.singletonList(dto));
        return modelAndView(new NoteBook5mParam().setCode(dto.getCode()).setDate(dto.getDate()));
    }

    @RequestMapping("/del")
    public ModelAndView del(@ModelAttribute @NonNull NoteBook5mDto dto) {
        noteBook5mService.delById(dto.getId());
        return modelAndView(new NoteBook5mParam().setCode(dto.getCode()).setDate(dto.getDate()));
    }

    @NonNull
    private ModelAndView modelAndView(@NonNull NoteBook5mParam param) {
        try {
            List<KLine> kLines = stockService.history(HistoryParam.builder()
                    .code(param.getCode())
                    .interval(IntervalEnum.MINUTE_5.getInterval())
                    .start(DateTimeU.datetimeTo(DateUtil.date(param.getDate()), param.getTimeZone()))
                    .end(DateTimeU.datetimeTo(DateUtil.date(param.getDate().plusDays(1)), param.getTimeZone()))
                    .build());

            ModelAndView modelAndView = new ModelAndView("notebook_5m_index");
            modelAndView.addObject("param", param);
            modelAndView.addObject("code", param.getCode());
            modelAndView.addObject("date", param.getDate());
            modelAndView.addObject("add_param", new NoteBook5mDto().setCode(param.getCode()).setDate(param.getDate()));
            modelAndView.addObject("notes", noteBook5mService.findAllByCodeAndDate(param.getCode(), param.getDate()));
            modelAndView.addObject("kLines", kLines);
            modelAndView.addObject("kLabels", KLines.dayTradingKLabels(kLines, param.getStartTime(), param.getEndTime()));
            modelAndView.addObject("ema20", IndicesU.emaXEChartsDto(indexService, kLines, 20));
            modelAndView.addObject("openIndex", KLines.dayTradingKLinesIndexByTime(kLines, param.getStartTime()));
            modelAndView.addObject("endIndex", KLines.dayTradingKLinesIndexByTime(kLines, param.getEndTime()));

            return modelAndView;
        } catch (StockServiceNoResException e) {
            return modelAndView(param.setDate(param.getDate().plusDays(-1)));
        } catch (IllegalArgumentException e) {
            ModelAndView modelAndView = new ModelAndView("notebook_5m_index");
            modelAndView.addObject("add_param", new NoteBook5mDto().setCode(param.getCode()).setDate(param.getDate()));
            modelAndView.addObject("param", param);
            return modelAndView;
        }
    }

}
