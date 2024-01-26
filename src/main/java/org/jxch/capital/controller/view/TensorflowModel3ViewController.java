package org.jxch.capital.controller.view;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.ModelEditManageParam;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.learning.train.TrainConfigService;
import org.jxch.capital.utils.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/model_view")
public class TensorflowModel3ViewController {
    private final Model3Management model3Management;
    private final TrainConfigService trainConfigService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("model/tensorflow_model_view_index");
        modelAndView.addObject("all_model", model3Management.allModelMetaData());
        modelAndView.addObject("all_train_configs", trainConfigService.findAll());
        modelAndView.addObject("all_type_metas", Model3BaseMetaData.allModelMetaDataJson());
        return modelAndView;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("metaDataJson") String metaDataJson) {
        model3Management.uploadModel(file, Model3BaseMetaData.parseOfJson(metaDataJson));
        return Controllers.redirect("/model_view/index");
    }

    @RequestMapping("/del/{name}")
    public String del(@PathVariable(value = "name") String name) {
        model3Management.delModel(name);
        return Controllers.redirect("/model_view/index");
    }

    @Deprecated
    @RequestMapping("/update")
    public String update(@NotNull @ModelAttribute ModelEditManageParam param) {
        model3Management.updateModelMetaData(param.getOldName(), param.getMetaData());
        return Controllers.redirect("/model_view/index");
    }

    @Deprecated
    @RequestMapping("/edit/{name}")
    public ModelAndView edit(@PathVariable(value = "name") String name) {
        Model3BaseMetaData modelMetaData = model3Management.findModelMetaData(name);
        ModelAndView modelAndView = new ModelAndView("model/tensorflow_model_view_edit");
        modelAndView.addObject("param", ModelEditManageParam.builder().metaData(modelMetaData).oldName(modelMetaData.getFilename()).build());
        modelAndView.addObject("all_train_configs", trainConfigService.findAll());
        return modelAndView;
    }

}
