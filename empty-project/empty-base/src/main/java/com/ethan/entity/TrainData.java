package com.ethan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Ethan on 2017/3/29.
 */
@Entity
@Table(name = "t_TRAINDATA", schema = "db_textclassify")
public class TrainData {

    @Id
    @Column(name = "TRAINDATA_ID", nullable = false, length = 20)
    private String trainDataID;

    @Column(name = "TRAINDATA_NAME")
    private String trainDataName;

    @Column(name = "TRAINDATA_DESC")
    private String trainDataDesc;

    @Column(name = "SOURCE_FILE_PATH")
    private String sourceFilePath;

    @Column(name = "HANDLED_DATA_PATH")
    private String handledDataPath;

    @Column(name = "CLASS_NUMBER")
    private Integer classNumber;

    /**
     * 表示数据是否经过预处理
     *     0：无处理
     *     1：已经预处理过
     */
    @Column(name = "TRAINDATA_status")
    private Integer trainDataStatus;

    public void setClassNumber(Integer classNumber) {this.classNumber = classNumber;}

    public void setHandledDataPath(String handledDataPath) {this.handledDataPath = handledDataPath;}

    public void setSourceFilePath(String sourceFilePath) {this.sourceFilePath = sourceFilePath;}

    public void setTrainDataID(String trainDataID) {this.trainDataID = trainDataID;}

    public void setTrainDataName(String trainDataName) {this.trainDataName = trainDataName;}

    public void setTrainDataStatus(Integer trainDataStatus) {this.trainDataStatus = trainDataStatus;}

    public void setTrainDataDesc(String trainDataDesc) {this.trainDataDesc = trainDataDesc;}

    public Integer getClassNumber() {return classNumber;}

    public Integer getTrainDataStatus() {return trainDataStatus;}

    public String getHandledDataPath() {return handledDataPath;}

    public String getSourceFilePath() {return sourceFilePath;}

    public String getTrainDataID() {return trainDataID;}

    public String getTrainDataName() {return trainDataName;}

    public String getTrainDataDesc() {return trainDataDesc;}
}
