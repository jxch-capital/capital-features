package org.jxch.capital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.server.KNNAutoService;
import org.jxch.capital.server.KNNs;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/knn")
@RequiredArgsConstructor
public class KNNController {
    private final KNNAutoService knnAutoService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("knn_index");
        modelAndView.addObject("knn", KNNs.getAllDistanceServicesName());
        return modelAndView;
    }

    @GetMapping("/neighbor/{name}/{code}/{stockPoolId}/{size}")
    public ModelAndView neighbor(@PathVariable(value = "name") String name,
                                 @PathVariable(value = "code") String code,
                                 @PathVariable(value = "stockPoolId") Long stockPoolId,
                                 @PathVariable(value = "size") Integer size) {
        ModelAndView modelAndView = new ModelAndView("knn_index");
        modelAndView.addObject("knn", KNNs.getAllDistanceServicesName());
        List<KNeighbor> neighbors = knnAutoService.search(name, code, stockPoolId, size);
        modelAndView.addObject("neighbors", neighbors);
        return modelAndView;
    }

}
