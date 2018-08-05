package com.ethan.entity;

import javax.persistence.*;

/**
 * Created by Ehtan on 2017/3/29.
 */
@Entity
@Table(name = "t_TEXTCLASS", schema = "db_textclassify")
public class TextClass {

    @Id
    @Column(name = "TEXTCLASS_ID", nullable = false, length = 20)
    private String textClassID;

    @Column(name = "TEXTCLASS_NAME")
    private String textClassName;

    /**
     * 文本分类时需要数值作为类别
     */
    @Column(name = "TEXTCLASS_NUMBER")
    private Float textClassNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TRAINDATA_ID")
    private TrainData trainData;

    public void setTextClassID(String textClassID) {this.textClassID = textClassID;}

    public void setTextClassName(String textClassName) {this.textClassName = textClassName;}

    public void setTextClassNumber(Float textClassNumber) {this.textClassNumber = textClassNumber;}

    public void setTrainData(TrainData trainData) {this.trainData = trainData;}

    public Float getTextClassNumber() {return textClassNumber;}

    public String getTextClassID() {return textClassID;}

    public String getTextClassName() {return textClassName;}

    public TrainData getTrainData() {return trainData;}
}
