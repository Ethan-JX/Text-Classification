package com.ethan.service.impl;

import com.ethan.dao.ITextClassDao;
import com.ethan.dao.ITrainDataDao;
import com.ethan.entity.TextClass;
import com.ethan.entity.TrainData;
import com.ethan.service.ITrainDataService;
import com.ethan.util.FileUtil;
import com.ethan.util.PassageClassifyToWordUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Ethan on 2017/03/29.
 */

@Service(value = "iTrainDataService")
public class TrainDataServiceImpl implements ITrainDataService{

    @Resource
    ITrainDataDao iTrainDataDao;
    @Resource
    ITextClassDao iTextClassDao;

    @Override
    @Transactional
    public void saveOrUpdataTrainData(TrainData trainData) {
        iTrainDataDao.updateEntity(trainData);
    }

    @Override
    @Transactional
    public void saveOrUpdataTextClass(TextClass textClass) {
        iTextClassDao.updateEntity(textClass);
    }

    @Override
    @Transactional
    public void deleteTrainDataById(String id) {
        iTrainDataDao.deleteEntityById(id);
    }

    @Override
    @Transactional
    public void deleteTextClassById(String id) {
        iTextClassDao.deleteEntityById(id);
    }

    @Override
    @Transactional
    public void deleteTrainDataByIdWithTextClass(String id) {
        List<TextClass> textClassList = this.getTextClassListByTrainDataId(id);
        for(TextClass t:textClassList){
            iTextClassDao.deleteEntity(t);
        }
        iTrainDataDao.deleteEntityById(id);
    }

    @Override
    public TrainData getTrainDataById(String id) {
        return iTrainDataDao.querryById(id);
    }

    @Override
    @Transactional
    public List<TrainData> getAllTrainDataList() {
        return iTrainDataDao.queryList();
    }

    @Override
    public List<TrainData> getAllHandleTrainDataList() {
        Map<String,Object> params = new HashMap<>();
        params.put("trainDataStatus",1);
        return iTrainDataDao.queryByKeyWords(params);
    }

    @Override
    @Transactional
    public List<TextClass> getTextClassListByTrainDataId(String id) {
        Map<String,Object> params = new HashMap<>();
        params.put("trainData.trainDataID", id);
        return iTextClassDao.queryByKeyWords(params);
    }

    @Override
    @Transactional
    public void handleTrainData(String trainDataId,String saveFilePathPrefix) throws IOException {
        TrainData trainData = iTrainDataDao.querryById(trainDataId);
        if(trainData.getTrainDataStatus()==0) {
            String sourceFilePath = trainData.getSourceFilePath();
            File sourceFile = new File(sourceFilePath);
            String handledDataPath = saveFilePathPrefix + "/" + trainDataId;
            FileUtil.createFile(handledDataPath);
            File[] classTypeFileList = sourceFile.listFiles();
            int classTypeNumber = classTypeFileList.length;
            trainData.setClassNumber(classTypeNumber);
            trainData.setHandledDataPath(handledDataPath);
            trainData.setTrainDataStatus(1);
            iTrainDataDao.updateEntity(trainData);
            System.out.println("类别总数:" + classTypeNumber);
            for (int i = 0; i < classTypeNumber; i++) {
                handleOneClassFile(classTypeFileList[i], i, trainData, handledDataPath);
            }
        }
    }

    private void handleOneClassFile(File file,int index,TrainData trainData,String targetFilePath) throws IOException{
        TextClass textClass = new TextClass();
        String textClassName = file.getName();
        String textClassId = UUID.randomUUID().toString().substring(0, 16);
        textClass.setTextClassID(textClassId);
        textClass.setTextClassName(textClassName);
        textClass.setTextClassNumber(Float.parseFloat(index+""));
        textClass.setTrainData(trainData);
        this.saveOrUpdataTextClass(textClass);

        FileWriter out = new FileWriter(targetFilePath,true);
        String fileContent = null;
        String wordString = null;
        for (File f:file.listFiles()){
            fileContent = FileUtil.readAllContentByFile(f);
            wordString = PassageClassifyToWordUtil.passageToWord(fileContent);
            out.write(""+index);
            out.write(",");
            out.write(wordString);
            out.write("\n");
        }
        out.close();
    }


//    public static void main(String[] args) throws IOException {
//        new TrainDataServiceImpl().handleOneClassFile(new File("C:\\Users\\Ethan\\Desktop\\文本分类语料库\\交通214"),0,null,"C:\\Users\\Ethan\\Desktop\\handledData");
//
//        new TrainDataServiceImpl().handleTrainData("");
//
//    }
}
