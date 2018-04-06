<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: oo
  Date: 2018/4/5
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${base}/consumer_edit" method="post">
    id:<input type="text" name="consumerId" value="${consumer.consumerId}" readonly="readonly"></br>
    客户名称：<input type="text" name="consumerName" value="${consumer.consumerName}"></br>
    联系人：<input type="text" name="consumerContact" value="${consumer.consumerContact}"></br>
    联系电话：<input type="text" name="consumerTelephone" value="${consumer.consumerTelephone}"></br>
    电子邮件：<input type="text" name="consumerEmail" value="${consumer.consumerEmail}"></br>
    备注：<input type="text" name="consumerRemark" value="${consumer.consumerRemark}"></br>
    <input type="submit"  value="提交">
    <span>      </span>
    <input type="reset"  value="清空">
</form>
</body>
</html>
