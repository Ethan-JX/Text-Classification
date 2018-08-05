<%--
  Created by IntelliJ IDEA.
  User: Ethan
  Date: 2017/4/7
  Time: 0:06
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
<%@include file="../common/header.jsp"%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="btn-contianer">
                    <a href="#" id="addModel" class="btn btn-success btn" onclick="model.openEditOrNewModelPage('训练模型')">训练模型</a>
                </div>
                <!-- 列表 -->
                <div class="row">
                    <div class="col-md-12">
                        <ul class="nav nav-tabs" id="myTab">
                            <li class="active"><a href="#home">模型列表</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="home">
                                <div class="tab-content tabs_container" id="table_container">
                                    <!--Table Wrapper Start-->
                                    <div class="ls-table">
                                        <table class="table table-striped table-hover"
                                               style="table-layout: fixed; font-size: 12px;" id="tableShow">
                                            <thead>
                                                <tr>
                                                    <th>模型名称</th>
                                                    <th>模型描述</th>
                                                    <th>算法</th>
                                                    <th>数据集</th>
                                                    <th>准确率</th>
                                                    <th>精确率</th>
                                                    <th>召回率</th>
                                                    <th>F1</th>
                                                    <th>操作</th>
                                                </tr>
                                            </thead>
                                            <tbody id="modelListTbody">
                                                <tr>
                                                    <td align="center" colspan=9>数据加载中...</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>
    var basePath = "<%=basePath%>";
</script>
<script src="<%=basePath%>/assets/js/model/model.js"></script>
</body>
</html>
