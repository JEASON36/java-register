<!-- 用户界面代码 -->
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" 
import="java.util.*, book.model.UserInfo"%>
<html>
<head>
<link rel="Stylesheet" type="text/css" href="layout.css" />
<title>用户信息列表</title>
</head>
<body id="word">
<h2>用户信息列表</h2>
<!-- -->
 <!-- <form name="f" method="post" action="delecte.action"> -->
 <form action="delete.action" method = "post">     
<table id="form">
<tr><th>序号<th>用户名<th>密码<th>学号<th>班级<th>专业<th>学分<th>功能</tr>
<% ArrayList<UserInfo>
list=(ArrayList<UserInfo>)request.getAttribute("list");
int i=1;
for(UserInfo ui:list){
%>
<tr> <td><%=i%>
<td><input name="username" type="text" value="<%=ui.getUsername()%>">
<td><input name="password" type="text" value="<%=ui.getPassword()%>">
<td><input name="courseid" type="text" value="<%=ui.getCourseid()%>">
<td><input name="coursename" type="text" value="<%=ui.getCoursename()%>">
<td><input name="coursesort" type="text" value="<%=ui.getCoursesort()%>">
<td><input name="credit" type="text" value="<%=ui.getCredit()%>">
<td>删除<input type="submit" value="<%=ui.getUsername()%>" name="username1">
</tr>
<% i++; } %>
</table>
</form>
<a href="loginSuccess.html" id="word">返回主页</a>
</body>
