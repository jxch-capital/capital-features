package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.WatchConfigDto;
import org.jxch.capital.server.UserConfigService;
import org.jxch.capital.server.WatchConfigService;
import org.jxch.capital.utils.Controllers;
import org.jxch.capital.watch.Watchers;
import org.jxch.capital.watch.impl.AutoWatchMailTaskImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/watch_config")
public class WatchConfigController {
    private final WatchConfigService watchConfigService;
    private final UserConfigService userConfigService;
    private final AutoWatchMailTaskImpl autoWatchMailTask;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("watch_config_index");
        modelAndView.addObject("param", new WatchConfigDto());
        modelAndView.addObject("all_watch_configs", watchConfigService.findAll());
        modelAndView.addObject("user_configs", userConfigService.findAll());
        modelAndView.addObject("all_watcher", Watchers.allWatchMailTaskNames());
        return modelAndView;
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute WatchConfigDto watchConfigDto) {
        watchConfigService.save(Collections.singletonList(watchConfigDto));
        return Controllers.redirect("/watch_config/index");
    }

    @GetMapping(value = "/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        watchConfigService.del(id);
        return Controllers.redirect("/watch_config/index");
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("watch_config_edit_index");
        modelAndView.addObject("param", watchConfigService.findById(id));
        modelAndView.addObject("user_configs", userConfigService.findAll());
        modelAndView.addObject("all_watcher", Watchers.allWatchMailTaskNames());
        return modelAndView;
    }

    @GetMapping(value = "/email")
    public String email() {
        autoWatchMailTask.watchTask();
        return Controllers.redirect("/watch_config/index");
    }

}
