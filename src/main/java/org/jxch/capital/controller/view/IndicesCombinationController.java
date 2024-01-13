package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.IndicesCombinationDto;
import org.jxch.capital.server.IndicesCombinationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping(path = "/indices_com")
@RequiredArgsConstructor
public class IndicesCombinationController {
    private final IndicesCombinationService indicesCombinationService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("indices_com_index");
        modelAndView.addObject("indicesCom", new IndicesCombinationDto());
        modelAndView.addObject("allIndicesCom", indicesCombinationService.findAll());
        return modelAndView;
    }

    public String redirect() {
        return "redirect:/indices_com/index";
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute IndicesCombinationDto dto) {
        indicesCombinationService.save(Collections.singletonList(dto));
        return redirect();
    }

    @GetMapping("/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        indicesCombinationService.del(Collections.singletonList(id));
        return redirect();
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("indices_com_edit");
        modelAndView.addObject("indicesCom", indicesCombinationService.findById(id));
        return modelAndView;
    }

}
