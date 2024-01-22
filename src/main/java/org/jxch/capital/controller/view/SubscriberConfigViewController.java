package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.subscriber.SubscriberConfigService;
import org.jxch.capital.subscriber.Subscribers;
import org.jxch.capital.utils.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping(path = "/subscriber_config_view")
@RequiredArgsConstructor
public class SubscriberConfigViewController {
    private final SubscriberConfigService subscriberConfigService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("subscriber/subscriber_config_view_index");
        modelAndView.addObject("param", new SubscriberConfigDto());
        modelAndView.addObject("allConfigs", subscriberConfigService.findAll());
        modelAndView.addObject("allSubscribers", Subscribers.allSubscriberNames());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute SubscriberConfigDto dto) {
        subscriberConfigService.save(Collections.singletonList(Subscribers.setDefaultParamsIfIsBlank(dto)));
        return Controllers.redirect("/subscriber_config_view/index");
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("subscriber/subscriber_config_view_edit");
        modelAndView.addObject("param", subscriberConfigService.findById(id));
        modelAndView.addObject("allSubscribers", Subscribers.allSubscriberNames());
        return modelAndView;
    }

    @RequestMapping(value = "/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        subscriberConfigService.delById(Collections.singletonList(id));
        return Controllers.redirect("/subscriber_config_view/index");
    }

}
