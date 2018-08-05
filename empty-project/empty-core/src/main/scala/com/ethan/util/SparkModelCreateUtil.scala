package com.ethan.util

import org.apache.spark.ml.{Pipeline, PipelineStage}
import org.apache.spark.ml.classification._
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 2017/3/22.
  */
object SparkModelCreateUtil {

  System.setProperty("hadoop.home.dir", "D:\\hadoop-2.6.5")
  val conf = new SparkConf().setMaster("local[1]").setAppName("Classification")
  val sc = new SparkContext(conf)
  val sqlCtx = SparkSession.builder().getOrCreate();

  /**
    * 抽取的公共方法
    * @param classify
    * @param trainDataFilePath
    * @return
    */
private def beginTrainModel(classify : PipelineStage,trainDataFilePath:String,modelSavePath:String) :String = {
  val parsedRDD = sc.textFile(trainDataFilePath).map(_.split(","))
    .map(eachRow => {
      (eachRow(0).toFloat, eachRow(1))
    })
  val msgDF = sqlCtx.createDataFrame(parsedRDD).toDF("label", "message")

  val tokenizer = new Tokenizer()
    .setInputCol("message")
    .setOutputCol("words")

  val hashingTF = new HashingTF()
    .setNumFeatures(1000)
    .setInputCol(tokenizer.getOutputCol)
    .setOutputCol("features")
  val Array(trainingData, testData) = msgDF.randomSplit(Array(0.8, 0.2))

  val pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, classify))
  val model = pipeline.fit(trainingData)

  //     Now we can optionally save the fitted pipeline to disk
  //    model.write.overwrite().save("/tmp/spark-logistic-regression-model")
  model.write.overwrite().save(modelSavePath)

  val predictionResultDF = model.transform(testData)

  predictionResultDF.printSchema
  predictionResultDF.select("message", "label", "prediction").show

  val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")

  val predictionAccuracy = evaluator.evaluate(predictionResultDF)
  println("--->        Testing Accuracy is %2.4f".format(predictionAccuracy * 100) + "%")
  val weightedPrecision=evaluator.setMetricName("weightedPrecision").evaluate(predictionResultDF);
  val weightedRecall=evaluator.setMetricName("weightedRecall").evaluate(predictionResultDF);
  val f1=evaluator.setMetricName("f1").evaluate(predictionResultDF);
  println((predictionAccuracy * 100) + "哈哈"+(weightedPrecision * 100)+ "哈哈"+(weightedRecall * 100)+ "哈哈"+f1)
  (predictionAccuracy * 100) + "哈哈"+(weightedPrecision * 100)+ "哈哈"+(weightedRecall * 100)+ "哈哈"+f1
}


/**
  * 逻辑回归
  * @param trainDataFilePath
  * @return
  */
def LogisticRegression(trainDataFilePath:String,modelSavePath:String): String ={

  val lr = new LogisticRegression()
    .setMaxIter(10)
    .setRegParam(0.001)
  SparkModelCreateUtil.beginTrainModel(lr,trainDataFilePath,modelSavePath:String);
}


/**
  * 决策树
  *
  * @param trainDataFilePath
  * @return
  */
def Decision(trainDataFilePath:String,modelSavePath:String): String ={
  val dt = new DecisionTreeClassifier()
    .setLabelCol("label")
    .setFeaturesCol("features")
  SparkModelCreateUtil.beginTrainModel(dt,trainDataFilePath,modelSavePath:String);
}


/**
  * 贝叶斯
  * @param trainDataFilePath
  * @return
  */
def Naive(trainDataFilePath:String,modelSavePath:String): String ={
  // Train a NaiveBayes model.
  val nb = new NaiveBayes()
  SparkModelCreateUtil.beginTrainModel(nb,trainDataFilePath,modelSavePath:String);
}


  /**
    * 神经网络
    * @param trainDataFilePath
    */
  def Multilayer(trainDataFilePath:String,modelSavePath:String,textClassNumber:Integer): String ={

    val layers = Array[Int](1000,5,textClassNumber)

    // create the trainer and set its parameters
    val mpc = new MultilayerPerceptronClassifier()
      .setLayers(layers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(1000)
      .setFeaturesCol("features")
      .setLabelCol("label")

    SparkModelCreateUtil.beginTrainModel(mpc,trainDataFilePath,modelSavePath:String);
  }



  def main(args: Array[String]): Unit = {
    var path = "E:\\03.项目工作区间\\idea\\empty-project\\empty-web\\target\\empty\\resourcces\\traindata_file/eb3fedb3-1b40-47"
    var modelSavePathPrefix = "C:\\Users\\Ethan\\Desktop\\model"
    SparkModelCreateUtil.LogisticRegression("E:\\03.项目工作区间\\idea\\empty-project\\empty-web\\target\\empty\\resourcces\\traindata_file/eb3fedb3-1b40-47","E:\\03.项目工作区间\\idea\\empty-project\\empty-web\\target\\empty\\resourcces\\model/b70d26e5-1c1a-41")
//    SparkModelCreateUtil.Decision(path,modelSavePathPrefix+"/d")
//    SparkModelCreateUtil.Naive(path,modelSavePathPrefix+"/n")
//    SparkModelCreateUtil.Multilayer(path,modelSavePathPrefix+"/m",10);
  }
}