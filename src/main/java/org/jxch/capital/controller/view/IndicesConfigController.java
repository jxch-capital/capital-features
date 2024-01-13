package org.jxch.capital.controller.view;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.IndicesConfigDto;
import org.jxch.capital.server.IndicesConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping(path = "/indices")
@RequiredArgsConstructor
public class IndicesConfigController {
    private final IndicesConfigService indicesConfigService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("indices_index");
        modelAndView.addObject("indexConfig", new IndicesConfigDto());
        modelAndView.addObject("indices", indicesConfigService.findAll());
        modelAndView.addObject("supports", indicesConfigService.allSupportIndicators());
        return modelAndView;
    }

    public String redirect() {
        return "redirect:/indices/index";
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute @NonNull IndicesConfigDto dto) {
        indicesConfigService.save(Collections.singletonList(dto.paramTypes()));
        return redirect();
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("indices_edit");
        modelAndView.addObject("indexConfig", indicesConfigService.findById(id));
        modelAndView.addObject("supports", indicesConfigService.allSupportIndicators());
        return modelAndView;
    }

    @GetMapping("/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        indicesConfigService.del(Collections.singletonList(id));
        return redirect();
    }

}
