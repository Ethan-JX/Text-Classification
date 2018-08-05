package com.ethan.action;

import com.ethan.entity.Model;
import com.ethan.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ethan on 2017/4/10.
 */
@Controller
@RequestMapping(value = "/textClassify")
public class textClassifyController {

    @Autowired
    private IModelService iModelService;


    @RequestMapping()
    public ModelAndView toModelPage() {
        ModelAndView mv = new ModelAndView("/textClassify/textClassifyPage");
        List<Model> modelList = iModelService.getAllModelList();
        mv.addObject("modelList",modelList);
        return mv;
    }


    @RequestMapping("/classify")
    @ResponseBody
    public Map<String, Object> classify(String text,String modelId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try{
            String textClassName = iModelService.classifyTextByModelId(text,modelId);
            map.put("textClassName",textClassName);
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "分类失败");
        }
        return map;
    }
}
