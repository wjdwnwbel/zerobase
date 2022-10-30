<%@page import="Dao.WifiList"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<WifiList> lists = (ArrayList<WifiList>)request.getAttribute("list");
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
  padding: 10px;
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
<title>Insert title here</title>
</head>
<body>
<br>
	<table id="customers">
		<tr>
    		<th>거리</th>
    		<th>관리번호</th>
    		<th>자치구</th>
    		<th>와이파이명</th>
    		<th>도로명주소</th>
    		<th>상세주소</th>
    		<th>설치위치(층)</th>
    		<th>설치유형</th>
    		<th>설치기관</th>
    		<th>서비스구분</th>
    		<th>망종류</th>
    		<th>설치년도</th>
    		<th>실내외구분</th>
    		<th>WIFI접속환경</th>
    		<th>X좌표</th>
    		<th>Y좌표</th>
    		<th>작업일자</th>
    	</tr>
	
	<%
		if(lists != null) {
			for(WifiList list : lists) {
	%>
	
	<tr>
		<td><%= list.getDistance() %></td>
		<td><%= list.getManageNumber() %></td>
		<td><%= list.getBorough() %></td>
		<td><%= list.getWifiName() %></td>
		<td><%= list.getSAddress() %></td>
		<td><%= list.getDAddress() %></td>
		<td><%= list.getInstallLocation() %></td>
		<td><%= list.getInstallType() %></td>
		<td><%= list.getInstallAgency() %></td>
		<td><%= list.getServiceType() %></td>
		<td><%= list.getNetType() %></td>
		<td><%= list.getYear() %></td>
		<td><%= list.getDoor() %></td>
		<td><%= list.getConnEnvironment() %></td>
		<td><%= list.getLat() %></td>
		<td><%= list.getLnt() %></td>
		<td><%= list.getLocalDateTime() %></td>
	</tr>
	<%
			}
		} else {
	%>
	
	<tr>
		<td colspan="17" style="text-align:center"> 위치 정보를 입력한 후에 조회해 주세요</td>
	</tr>
	
	<%
		}
	%>
	
	</table>
</body>
</html>