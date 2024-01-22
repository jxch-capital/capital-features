package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.UserSubscriberDto;
import org.jxch.capital.server.UserConfigService;
import org.jxch.capital.subscriber.SubscriberConfigGroupService;
import org.jxch.capital.subscriber.UserSubscriberService;
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
@RequestMapping(path = "/user_subscriber_view")
public class UserSubscriberViewController {
    private final UserConfigService userConfigService;
    private final SubscriberConfigGroupService subscriberConfigGroupService;
    private final UserSubscriberService userSubscriberService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("subscriber/user_subscriber_view_index");
        modelAndView.addObject("param", new UserSubscriberDto());
        modelAndView.addObject("allUserSubscribers", userSubscriberService.findAll());
        modelAndView.addObject("allGroups", subscriberConfigGroupService.findAll());
        modelAndView.addObject("allUsers", userConfigService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute UserSubscriberDto dto) {
        userSubscriberService.save(Collections.singletonList(dto));
        return Controllers.redirect("/user_subscriber_view/index");
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("subscriber/user_subscriber_view_edit");
        modelAndView.addObject("param", userSubscriberService.findById(id));
        modelAndView.addObject("allGroups", subscriberConfigGroupService.findAll());
        modelAndView.addObject("allUsers", userConfigService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        userSubscriberService.delById(Collections.singletonList(id));
        return Controllers.redirect("/user_subscriber_view/index");
    }

    @RequestMapping(value = "/subscribe/{id}")
    public String subscribe(@PathVariable(value = "id") Long id) {
        userSubscriberService.subscriber(id);
        return Controllers.redirect("/user_subscriber_view/index");
    }

}
