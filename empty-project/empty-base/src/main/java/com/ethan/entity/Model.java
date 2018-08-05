package com.ethan.entity;

import javax.persistence.*;

/**
 * Created by Ethan on 2017/4/7.
 */
@Entity
@Table(name = "t_MODEL", schema = "db_textclassify")
public class Model {
    @Id
    @Column(name = "MODEL_ID", nullable = false, length = 20)
    private String modelID;

    @Column(name = "MODEL_NAME")
    private String modelName;

    @Column(name = "MODEL_DESC")
    private String modelDesc;

    @Column(name = "MODEL_SAVE_PATH")
    private String modelSavePath;


    @Column(name = "Precision_RATE")
    private String precisionRate;

    @Column(name = "ACCURACY_RATE")
    private String accuracyRate;
    //回召率
    @Column(name = "Recall_RATE")
    private String recallRate;
    //F1
    @Column(name = "F1")
    private String F1;

    //算法
    @Column(name = "ALGORITHM")
    private String algorithm;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TRAINDATA_ID")
    private TrainData trainData;

    public void setAccuracyRate(String accuracyRate) {
        this.accuracyRate = accuracyRate;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setModelSavePath(String modelSavePath) {
        this.modelSavePath = modelSavePath;
    }

    public void setTrainData(TrainData trainData) {
        this.trainData = trainData;
    }

    public String getAccuracyRate() {
        return accuracyRate;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public String getModelID() {
        return modelID;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelSavePath() {
        return modelSavePath;
    }

    public TrainData getTrainData() {
        return trainData;
    }

    public void setF1(String f1) {
        F1 = f1;
    }

    public void setPrecisionRate(String precisionRate) {
        this.precisionRate = precisionRate;
    }

    public void setRecallRate(String recallRate) {
        this.recallRate = recallRate;
    }

    public String getF1() {
        return F1;
    }

    public String getPrecisionRate() {
        return precisionRate;
    }

    public String getRecallRate() {
        return recallRate;
    }
}
