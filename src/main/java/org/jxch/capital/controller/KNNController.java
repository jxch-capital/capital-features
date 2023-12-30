package org.jxch.capital.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.server.KNNAutoService;
import org.jxch.capital.server.KNNs;
import org.jxch.capital.server.impl.LorentzianKNNServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final LorentzianKNNServiceImpl lorentzianKNNService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("knn_index");
        modelAndView.addObject("knn", KNNs.getAllDistanceServicesName());
        modelAndView.addObject("param",
                KNNParam.builder()
                        .distanceName(lorentzianKNNService.getName())
                        .kNodeParam(lorentzianKNNService.getDefaultKNodeParam())
                        .build());
        return modelAndView;
    }

    @RequestMapping("/neighbor")
    public ModelAndView neighbor(@NonNull @ModelAttribute KNNParam knnParam) {
        ModelAndView modelAndView = new ModelAndView("knn_index");
        modelAndView.addObject("knn", KNNs.getAllDistanceServicesName());
        List<KNeighbor> neighbors = knnAutoService.search(
                knnParam.getDistanceName(), knnParam.getKNodeParam(), knnParam.getNeighborSize());
        modelAndView.addObject("neighbors", neighbors);
        modelAndView.addObject("param", knnParam);
        return modelAndView;
    }

}
