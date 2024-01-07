package org.jxch.capital.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.http.vec.dto.VecDbParam;
import org.jxch.capital.http.vec.dto.VecRes;
import org.jxch.capital.server.VecService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(path = "/vec_db")
public class VecDbController {
    @Resource
    @Qualifier("vecDbService")
    private VecService vecDbService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("vec_db_index");
        modelAndView.addObject("param", new VecDbParam());
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView search(@ModelAttribute VecDbParam param) {
        VecRes vecRes = vecDbService.search(param);
        ModelAndView modelAndView = new ModelAndView("vec_db_index");
        modelAndView.addObject("param", param);
        modelAndView.addObject("vecRes", vecRes);
        return modelAndView;
    }

}
