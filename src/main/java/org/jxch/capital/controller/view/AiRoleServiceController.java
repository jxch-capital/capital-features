package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.AiRoleDto;
import org.jxch.capital.server.AiRoleService;
import org.jxch.capital.utils.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping(path = "/ai_role")
@RequiredArgsConstructor
public class AiRoleServiceController {
    private final AiRoleService aiRoleService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("ai_role_index");
        modelAndView.addObject("param", new AiRoleDto());
        modelAndView.addObject("ai_roles", aiRoleService.findAll());
        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute AiRoleDto aiRoleDto) {
        aiRoleService.save(Collections.singletonList(aiRoleDto));
        return Controllers.redirect("/ai_role/index");
    }

    @GetMapping("/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        aiRoleService.del(Collections.singletonList(id));
        return Controllers.redirect("/ai_role/index");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("ai_role_edit");
        modelAndView.addObject("param", aiRoleService.findById(id));
        return modelAndView;
    }

}
