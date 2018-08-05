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
        <label for="trainDataName" class="col-xs-3 control-label right-text">数据名称</label>
        <div class="col-xs-9">
            <input type="hidden" id="trainDataId" value="${trainData.trainDataID}"/>
            <input type="text" class="form-control" id="trainDataName" placeholder="请输入数据名称" value="${trainData.trainDataName}">
        </div>
    </div>
    <div class="form-group">
        <label for="sourceFilePath" class="col-xs-3 control-label right-text">数据路径</label>
        <div class="col-xs-9">
            <input type="text"  class="form-control" id="sourceFilePath" placeholder="请输入数据路径" value="${trainData.sourceFilePath}" ${trainData==null?"":"disabled"}>
        </div>
    </div>
    <div class="form-group">
        <label for="trainDataDesc" class="col-xs-3 control-label right-text">数据描述</label>
        <div class="col-xs-9">
            <textarea class="form-control" id="trainDataDesc" placeholder="请输入数据描述">${trainData.trainDataDesc}</textarea>
        </div>
    </div>
</form>
</body>
</html>
