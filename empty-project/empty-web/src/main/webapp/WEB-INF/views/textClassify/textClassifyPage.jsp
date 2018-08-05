<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ethan
  Date: 2017/4/10
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="../common/import.jsp"%>
    <link rel="stylesheet" href="<%=basePath%>/assets/css/textClassify/textClassifyPage.css" />
</head>
<body>
<%@include file="../common/header.jsp"%>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">

            <form class="form-horizontal text_container">
                <div class="form-group">
                    <label for="text" class="col-sm-2 control-label">分类文本</label>
                    <div class="col-sm-10">
                        <textarea class="form-control" rows="8" placeholder="请输入分类文本" id="text"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label for="model" class="col-sm-2 control-label">模型</label>
                    <div class="col-sm-10">
                        <select class="form-control" id="model" >
                            <s:forEach items="${modelList}" var="model">
                                <option value="${model.modelID}">${model.modelName}</option>
                            </s:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <a onclick="classifyText()" class="btn btn-danger">开始分类</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    var basePath = "<%=basePath%>";
</script>
<script src="<%=basePath%>/assets/js/textClassify/classify.js"></script>
</body>
</html>
