package com.ethan.service;

import com.ethan.entity.TextClass;
import com.ethan.entity.TrainData;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ethan on 2017/3/29.
 */
public interface ITrainDataService {
    void saveOrUpdataTrainData(TrainData trainData);
    void saveOrUpdataTextClass(TextClass textClass);

    void deleteTrainDataById(String id);
    void deleteTextClassById(String id);
    void deleteTrainDataByIdWithTextClass(String id);

    TrainData getTrainDataById(String id);

    List<TrainData> getAllTrainDataList();

    /**
     * 获取所有已经处理过的数据
     * @return
     */
    List<TrainData> getAllHandleTrainDataList();

    /**
     * 根据训练数据的ID获取其中的类别list
     * @param id
     * @return
     */
    List<TextClass> getTextClassListByTrainDataId(String id);

    /**
     * 开始初始化数据
     * @param trainDataId
     */
    void handleTrainData(String trainDataId,String saveFilePathPrefix) throws IOException;

}
