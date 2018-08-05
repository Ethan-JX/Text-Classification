<%--
  Created by IntelliJ IDEA.
  User: Ethan
  Date: 2017/3/28
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<div class="title">文本分类系统</div>
<div class="mynav container-fluid">
    <div class="nav_list row">
        <div class="col-md-offset-2 col-md-2 col-sm-4 col-xs-4"><a href="<%=path%>/trainData">数据集管理</a></div>
        <div class="col-md-4 col-sm-4 col-xs-4"><a href="<%=path%>/model">模型管理</a></div>
        <div class="col-md-2 col-sm-4 col-xs-4"><a href="<%=path%>/textClassify">文本分类</a></div>
    </div>
</div>