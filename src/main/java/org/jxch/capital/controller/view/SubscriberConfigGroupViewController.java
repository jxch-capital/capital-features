package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.SubscriberConfigGroupDto;
import org.jxch.capital.subscriber.SubscriberConfigGroupService;
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
@RequiredArgsConstructor
@RequestMapping(path = "/subscriber_config_group_view")
public class SubscriberConfigGroupViewController {
    private final SubscriberConfigGroupService subscriberConfigGroupService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("subscriber/subscriber_config_group_view_index");
        modelAndView.addObject("param", new SubscriberConfigGroupDto());
        modelAndView.addObject("allGroupService", Subscribers.allSubscriberGroupServiceNames());
        modelAndView.addObject("allConfigGroups", subscriberConfigGroupService.findAll());
        modelAndView.addObject("groupServiceSubscribersMap", subscriberConfigGroupService.groupServiceDBSubscriberMap());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute SubscriberConfigGroupDto dto) {
        subscriberConfigGroupService.save(Collections.singletonList(dto));
        return Controllers.redirect("/subscriber_config_group_view/index");
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("subscriber/subscriber_config_group_view_edit");
        modelAndView.addObject("param", subscriberConfigGroupService.findById(id));
        modelAndView.addObject("allGroupService", Subscribers.allSubscriberGroupServiceNames());
        modelAndView.addObject("groupServiceSubscribersMap", subscriberConfigGroupService.groupServiceDBSubscriberMap());
        modelAndView.addObject("subscriberIds", subscriberConfigGroupService.findById(id).getSubscriberConfigIds());

        return modelAndView;
    }

    @RequestMapping(value = "/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        subscriberConfigGroupService.delById(Collections.singletonList(id));
        return Controllers.redirect("/subscriber_config_group_view/index");
    }

}
