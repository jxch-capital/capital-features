package org.jxch.capital.config;

import org.jxch.capital.domain.dto.HistoryParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.Supplier;

@Configuration
public class AppControllerConfig {

    @Bean(name = "newIndexView")
    public Supplier<ModelAndView> newIndexView() {
        return () -> {
            ModelAndView view = new ModelAndView();
            view.setViewName("index");
            view.addObject("param", new HistoryParam());
            return view;
        };
    }

}
