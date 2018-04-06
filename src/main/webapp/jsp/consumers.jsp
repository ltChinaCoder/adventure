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
  <table>
      <tr>
          <th>客户名称</th>
          <th>客户联系人</th>
          <th>客户电话</th>
          <th>客户邮件</th>
          <th>备注</th>
          <th>操作 </th>
      </tr>

          <c:forEach var="consumer" items="${consumerList}">
      <tr>
              <td> ${consumer.consumerName}</td>
              <td> ${consumer.consumerContact}</td>
              <td> ${consumer.consumerTelephone}</td>
              <td> ${consumer.consumerEmail}</td>
              <td> ${consumer.consumerRemark}</td>
          <td>
              <a href="${base}/consumer_show?consumerId=${consumer.consumerId}">修改</a>
              <a href="${base}/consumer_delete?consumerId=${consumer.consumerId}">删除</a>
          </td>
      </tr>
          </c:forEach>

  </table>
</body>
</html>
