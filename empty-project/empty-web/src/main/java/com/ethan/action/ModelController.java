package com.ethan.action;

import com.ethan.entity.Model;
import com.ethan.entity.TrainData;
import com.ethan.service.IModelService;
import com.ethan.service.ITrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ethan on 2017/utf-8File/29.
 */
@Controller
@RequestMapping(value = "/model")
public class ModelController {

    @Autowired
    private IModelService iModelService;
    @Autowired
    private ITrainDataService iTrainDataService;

    @RequestMapping()
    public String toModelPage(){
        return "/model/modelList";
    }

    @RequestMapping("/loadModelList")
    @ResponseBody
    public Map<String, Object> loadModelList(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try{
            List<Model> list = iModelService.getAllModelList();
            map.put("modelList",list);
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "获取表列表失败");
        }
        return map;
    }

    @RequestMapping("/deleteModel")
    @ResponseBody
    public Map<String, Object> deleteModel(String modelId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try{
            iModelService.deleteModelById(modelId);
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "删除失败");
        }
        return map;
    }

    @RequestMapping("/toAddOrEditModelPage")
    public ModelAndView toAddOrEditModelPage(String modelId){
        ModelAndView modelAndView = new ModelAndView("/model/toAddOrEditModelPage");
        List<TrainData> handleTrainDataList = iTrainDataService.getAllHandleTrainDataList();
        modelAndView.addObject("handleTrainDataList",handleTrainDataList);
        if(modelId != null){
            Model model = iModelService.getModelById(modelId);
            modelAndView.addObject("model",model);
        }
        return modelAndView;
    }

    @RequestMapping("/editOrNewModel")
    @ResponseBody
    public Map<String, Object> editOrNewModel(Model model,String trainDataId,HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try{
            String modelID = model.getModelID();
            TrainData trainData = iTrainDataService.getTrainDataById(trainDataId);
            model.setTrainData(trainData);
            if(modelID==null||"".equals(modelID)) {
                model.setModelID(UUID.randomUUID().toString().substring(0, 16));
                String pathPrefix = request.getSession().getServletContext().getRealPath("/resourcces/model");
                iModelService.trainModelAndSaveModel(model,pathPrefix);
            }else {
                Model model1 = iModelService.getModelById(modelID);
                model1.setModelName(model.getModelName());
                model1.setModelDesc(model.getModelDesc());
                iModelService.saveOrUpdataModel(model1);
            }
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "程序出错");
        }
        return map;
    }
}
