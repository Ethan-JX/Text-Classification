package com.ethan.action;

import com.ethan.entity.TextClass;
import com.ethan.entity.TrainData;
import com.ethan.service.ITrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ethan on 2017/utf-8File/29.
 */
@Controller
@RequestMapping(value = "/trainData")
public class TrainDataController {

    @Autowired
    private ITrainDataService iTrainDataService;

    @RequestMapping()
    public String toTrainDataPage(){
        return "/trainData/trainDataList";
    }


    @RequestMapping("/loadTrainDataList")
    @ResponseBody
    public Map<String, Object> loadTrainDataList(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try{
            List<TrainData> list = iTrainDataService.getAllTrainDataList();
            map.put("trainDataList",list);
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "获取表列表失败");
        }
        return map;
    }

    @RequestMapping("/deleteTrainData")
    @ResponseBody
    public Map<String, Object> deleteTrainData(String trainDataId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try{
            iTrainDataService.deleteTrainDataByIdWithTextClass(trainDataId);
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "删除失败");
        }
        return map;
    }

    @RequestMapping("/toAddOrEditTrainDataPage")
    public ModelAndView toAddTrainDataPage(String trainDataId){
        ModelAndView modelAndView = new ModelAndView("/trainData/toAddOrEditTrainDataPage");
        if(trainDataId != null){
            TrainData trainData = iTrainDataService.getTrainDataById(trainDataId);
            modelAndView.addObject("trainData",trainData);
        }
        return modelAndView;
    }

    @RequestMapping("/editOrNewtrainData")
    @ResponseBody
    public Map<String, Object> editOrNewtrainData(TrainData trainData){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try{
            String trainDataID = trainData.getTrainDataID();
            if(trainDataID==null||"".equals(trainDataID)) {
                trainData.setTrainDataID(UUID.randomUUID().toString().substring(0, 16));
                trainData.setTrainDataStatus(0);
                iTrainDataService.saveOrUpdataTrainData(trainData);
            }else {
                TrainData trainData1 = iTrainDataService.getTrainDataById(trainDataID);
                trainData1.setTrainDataName(trainData.getTrainDataName());
                trainData1.setTrainDataDesc(trainData.getTrainDataDesc());
                iTrainDataService.saveOrUpdataTrainData(trainData1);
            }
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "保存失败");
        }
        return map;
    }

    @RequestMapping("/initTrainData")
    @ResponseBody
    public Map<String, Object> initTrainData(String trainDataId,HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 1);
        try {
            String path = request.getSession().getServletContext().getRealPath("/resourcces/traindata_file");
            iTrainDataService.handleTrainData(trainDataId, path);
        }catch (Exception e){
            map.put("code", -1);
            map.put("msg", "预处理失败");
        }
        return map;
    }

    @RequestMapping("/toShowTextClassPage")
    public ModelAndView toShowTextClassPage(String trainDataId){
        ModelAndView modelAndView = new ModelAndView("/trainData/showTextClassPage");
        List<TextClass> textClassList = iTrainDataService.getTextClassListByTrainDataId(trainDataId);
        modelAndView.addObject("textClassList",textClassList);
        return modelAndView;
    }

}
