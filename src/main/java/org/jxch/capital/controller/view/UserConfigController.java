package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.UserConfigDto;
import org.jxch.capital.server.UserConfigService;
import org.jxch.capital.utils.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/user_config")
public class UserConfigController {
    private final UserConfigService userConfigService;


    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("user_config_index");
        modelAndView.addObject("param", new UserConfigDto());
        modelAndView.addObject("all_user", userConfigService.findAll());
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute UserConfigDto userConfigDto) {
        userConfigService.save(Collections.singletonList(userConfigDto));
        return Controllers.redirect("/user_config/index");
    }

    @GetMapping(value = "/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        userConfigService.del(id);
        return Controllers.redirect("/user_config/index");
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("user_config_edit_index");
        modelAndView.addObject("param", userConfigService.findById(id));
        return modelAndView;
    }

}
