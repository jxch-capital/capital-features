package org.jxch.capital.controller.view;


import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.ModelEditManageParam;
import org.jxch.capital.learning.model.dto.Model3MetaData;
import org.jxch.capital.learning.model.impl.TensorflowModel3Management;
import org.jxch.capital.learning.train.TrainConfigService;
import org.jxch.capital.utils.Controllers;
import org.jxch.capital.utils.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/model_view")
public class TensorflowModel3ViewController {
    private final TensorflowModel3Management tensorflowModel3Management;
    private final TrainConfigService trainConfigService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("model/tensorflow_model_view_index");
        modelAndView.addObject("metaDataJson", JSONUtils.toJsonAndNull(new Model3MetaData()));
        modelAndView.addObject("all_model", tensorflowModel3Management.allModelMetaData());
        modelAndView.addObject("all_train_configs", trainConfigService.findAll());
        return modelAndView;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("metaDataJson") String metaDataJson) {
        tensorflowModel3Management.uploadModel(file, JSONObject.parseObject(metaDataJson, Model3MetaData.class));
        return Controllers.redirect("/model_view/index");
    }

    @RequestMapping("/del/{name}")
    public String del(@PathVariable(value = "name") String name) {
        tensorflowModel3Management.delModel(name);
        return Controllers.redirect("/model_view/index");
    }

    @Deprecated
    @RequestMapping("/update")
    public String update(@NotNull @ModelAttribute ModelEditManageParam param) {
        tensorflowModel3Management.updateModelMetaData(param.getOldName(), param.getMetaData());
        return Controllers.redirect("/model_view/index");
    }

    @Deprecated
    @RequestMapping("/edit/{name}")
    public ModelAndView edit(@PathVariable(value = "name") String name) {
        Model3MetaData modelMetaData = tensorflowModel3Management.findModelMetaData(name);
        ModelAndView modelAndView = new ModelAndView("model/tensorflow_model_view_edit");
        modelAndView.addObject("param", ModelEditManageParam.builder().metaData(modelMetaData).oldName(modelMetaData.getFilename()).build());
        modelAndView.addObject("all_train_configs", trainConfigService.findAll());
        return modelAndView;
    }

}
