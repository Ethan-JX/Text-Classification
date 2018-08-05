<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ethan
  Date: 2017/4/6
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="../common/import.jsp"%>
    <link href="<%=basePath%>/assets/css/common/table.css" rel="stylesheet"/>
</head>
<body>
<form class="form-horizontal" style="overflow: hidden;margin-top:20px;">
    <div class="form-group">
        <label for="modelName" class="col-xs-3 control-label right-text">模型名称</label>
        <div class="col-xs-9">
            <input type="hidden" id="modelID" value="${model.modelID}"/>
            <input type="text" class="form-control" id="modelName" placeholder="请输入模型名称" value="${model.modelName}">
        </div>
    </div>
    <div class="form-group">
        <label for="trainDataId" class="col-xs-3 control-label right-text">数据集</label>
        <div class="col-xs-9">
            <select class="form-control" id="trainDataId" ${model==null?"":"disabled"}>
                <c:forEach items="${handleTrainDataList}" var="handleTrainData">
                    <option value="${handleTrainData.trainDataID}" ${handleTrainData.trainDataID==model.trainData.trainDataID?"selected":""}>${handleTrainData.trainDataName}</option>
                </c:forEach>
            </select>
            <%--<input type="text"  class="form-control" id="trainDataId" placeholder="请输入数据路径" value="${model.trainData.trainDataName}">--%>
        </div>
    </div>
    <div class="form-group">
        <label for="algorithm" class="col-xs-3 control-label right-text">训练算法</label>
        <div class="col-xs-9">
            <select class="form-control" id="algorithm" ${model==null?"":"disabled"}>
                <option ${model.algorithm=="逻辑回归"?"selected":""}>逻辑回归</option>
                <option ${model.algorithm=="决策树"?"selected":""}>决策树</option>
                <%--<option ${model.algorithm=="神经网络"?"selected":""}>神经网络</option>--%>
                <option ${model.algorithm=="贝叶斯"?"selected":""}>贝叶斯</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="modelDesc" class="col-xs-3 control-label right-text">模型描述</label>
        <div class="col-xs-9">
            <textarea class="form-control" id="modelDesc" placeholder="请输入模型描述">${model.modelDesc}</textarea>
        </div>
    </div>
</form>
</body>
</html>
