<%--
  Created by IntelliJ IDEA.
  User: Ethan
  Date: 2017/4/3
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
                    <a href="#" id="addTrainData" class="btn btn-info btn" onclick="trainData.openEditOrNewtrainDataPage('添加数据集')">添加数据集</a>
                </div>
                <!-- 列表 -->
                <div class="row">
                    <div class="col-md-12">
                        <ul class="nav nav-tabs" id="myTab">
                            <li class="active"><a href="#home">数据集列表</a></li>
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
                                                    <th>数据集名称</th>
                                                    <th>数据集描述</th>
                                                    <th>路径</th>
                                                    <th>状态</th>
                                                    <th>操作</th>
                                                </tr>
                                            </thead>
                                            <tbody id="trainDataListTbody">
                                                <tr>
                                                    <td align="center" colspan=5>数据加载中...</td>
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
<script src="<%=basePath%>/assets/js/trainData/trainData.js"></script>
</body>
</html>
