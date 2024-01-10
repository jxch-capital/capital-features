package org.jxch.capital.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.server.AiRoleService;
import org.jxch.capital.server.TextAiEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(path = "/ai")
@RequiredArgsConstructor
public class TextAiController {
    private final AiRoleService aiRoleService;

    @GetMapping("/index")
    public ModelAndView index(@NonNull HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("ai_text_ws_index");

        String serverName = request.getServerName();  // hostname.com
        int serverPort = request.getServerPort();  // 80
        String contextPath = request.getContextPath();  // /mywebapp
        String wsUrl =  serverName + ":" + serverPort + contextPath + "/textAiWSHandler";

        modelAndView.addObject("wsUrl", wsUrl);
        modelAndView.addObject("models", TextAiEnum.values());
        modelAndView.addObject("all_role", aiRoleService.findAll());
        return modelAndView;
    }

}
