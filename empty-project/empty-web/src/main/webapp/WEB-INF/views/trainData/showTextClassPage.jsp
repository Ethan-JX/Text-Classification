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
<div class="container-fluid">
    <c:forEach items="${textClassList}" var="textClass">
        <span class="label label-primary" style="line-height: 30px;font-size: 16px;">${textClass.textClassName}</span>
    </c:forEach>
</div>
</body>
</html>
