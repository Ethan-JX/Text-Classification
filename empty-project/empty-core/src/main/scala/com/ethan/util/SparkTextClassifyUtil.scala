package com.ethan.util

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.SparkSession

/**
  * Created by Ethan on 2017/4/10.
  */
object SparkTextClassifyUtil {

  val sqlCtx = SparkModelCreateUtil.sqlCtx;


  def classifyText(modelPath:String,text:String):String = {
    val training = sqlCtx.createDataFrame(Seq(
      (1.0 ,text)
    )).toDF( "label", "message")

    val model = PipelineModel.load(modelPath)
    val result = model.transform(training)
      .select("message", "prediction").collect()
    val prediction = result(0).get(1).toString
    prediction
  }
}
