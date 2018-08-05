package com.ethan.service.impl;

import com.ethan.dao.IModelDao;
import com.ethan.dao.ITextClassDao;
import com.ethan.entity.Model;
import com.ethan.entity.TextClass;
import com.ethan.entity.TrainData;
import com.ethan.service.IModelService;
import com.ethan.util.PassageClassifyToWordUtil;
import com.ethan.util.SparkModelCreateUtil;
import com.ethan.util.SparkTextClassifyUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ethan on 2017/03/29.
 */

@Service(value = "iModelService")
public class ModelServiceImpl implements IModelService {

    @Resource
    IModelDao iModelDao;
    @Resource
    ITextClassDao iTextClassDao;

    @Override
    @Transactional
    public void saveOrUpdataModel(Model model) {
        iModelDao.updateEntity(model);
    }

    @Override
    @Transactional
    public void trainModelAndSaveModel(Model model,String pathPrefix) {
        String modelSavePath = pathPrefix +"/"+ model.getModelID();
        model.setModelSavePath(modelSavePath);
        String accuracyRate = null;//准确率
        String algorithm = model.getAlgorithm();
        TrainData trainData = model.getTrainData();
        String trainDataPath =trainData.getHandledDataPath();
        switch (algorithm){
            case "逻辑回归":
                accuracyRate = SparkModelCreateUtil.LogisticRegression(trainDataPath,modelSavePath);
                break;
            case "决策树":
                accuracyRate = SparkModelCreateUtil.Decision(trainDataPath,modelSavePath);
                break;
            case "神经网络":
                Integer textClassNumber = trainData.getClassNumber();
                accuracyRate = SparkModelCreateUtil.Multilayer(trainDataPath,modelSavePath,textClassNumber);
                break;
            case "贝叶斯":
                accuracyRate = SparkModelCreateUtil.Naive(trainDataPath,modelSavePath);
                break;
        }
        String[] strings = accuracyRate.split("哈哈");
        model.setAccuracyRate(strings[0]);
        model.setPrecisionRate(strings[1]);
        model.setRecallRate(strings[2]);
        model.setF1(strings[3]);
        iModelDao.updateEntity(model);
    }

    @Override
    @Transactional
    public void deleteModelById(String id) {
        iModelDao.deleteEntityById(id);
    }

    @Override
    public Model getModelById(String id) {
        return iModelDao.querryById(id);
    }

    @Override
    public String classifyTextByModelId(String text, String modelId) throws IOException {
        Model model = this.getModelById(modelId);
        String textClassFloat = SparkTextClassifyUtil.classifyText(model.getModelSavePath(),
                PassageClassifyToWordUtil.passageToWord(text));
        String textClassNumber = Integer.parseInt(textClassFloat.substring(0,textClassFloat.indexOf('.')))+"";


        Map<String,Object> params = new HashMap<>();
        params.put("trainData.trainDataID",model.getTrainData().getTrainDataID());
        params.put("textClassNumber",Float.parseFloat(textClassNumber));
        List<TextClass> textClassList = iTextClassDao.queryByKeyWords(params);
        String textClassName = textClassList.get(0).getTextClassName();

        return textClassName;
    }


    @Override
    @Transactional
    public List<Model> getAllModelList() {
        return iModelDao.queryList();
    }
}
