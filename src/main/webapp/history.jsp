<%@page import="java.util.ArrayList"%>
<%@page import="Dao.WifiHistory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<WifiHistory> lists = (ArrayList<WifiHistory>)request.getAttribute("list");

%>
<!DOCTYPE html>
<html>
<head>
<style>
#customers {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

#customers tr:nth-child(odd){background-color: #f2f2f2;}

#customers tr:hover {background-color: #ddd;}

#customers th {
  padding-top: 12px;
  padding-bottom: 12px;
  background-color: #04AA6D;
  color: white;
}
</style>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
<h1>와이파이 정보 구하기</h1>

<a href='home.jsp'>홈</a> | <a href="WifiServlet?command=getHistory">위치 히스토리 목록</a> | <a href="WifiServlet?command=getWifi"> Open API 와이파이 정보 가져오기 </a><br>
<table  id="customers">
	<tr>
		<th>ID</th>
		<th>X좌표</th>
		<th>Y좌표</th>
		<th>조회일자</th>
		<th>비고</th>
	</tr>
	
    <%
		for(WifiHistory history : lists) {
	%>
	<tr>
		<td><%= history.getId() %></td>
		<td><%= history.getX() %></td>
		<td><%= history.getY() %></td>
		<td><%= history.getDatetime() %></td>
		<td style="text-align:center"><button type=button title="삭제" onClick="location.href='delete.jsp?id=<%= history.getId() %>'">삭제</button> </td>
	</tr>
    <%
		}
	%>
</table>

</body>
</html>