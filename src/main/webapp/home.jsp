<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>와이파이 정보 구하기</title>
</head>
<body>
<h1>와이파이 정보 구하기</h1>

<a href='home.jsp'>홈</a> | <a href="WifiServlet?command=getHistory">위치 히스토리 목록</a> | <a href="WifiServlet?command=getWifi"> Open API 와이파이 정보 가져오기 </a><br>

<div>
<%
	if(request.getParameter("lnt") != null && request.getParameter("lat") != null) {
%>
	LAT: <input type=text id='lat' value=<%= request.getParameter("lat") %>>, LNT: <input type=text id='lnt' value=<%=request.getParameter("lnt") %>> 	

<%
	} else {
%>
	LAT: <input type=text id='lat' value =0.0>, LNT: <input type=text id='lnt' value=0.0> 
<%
	}
%>
<button id = "placebtn" onclick='getUserLocation()'>내 위치 가져오기</button>
<button type=button onclick="getInfo()">근처 WIFI 정보 보기</button>
</div>

<jsp:include page="/WEB-INF/WifiList.jsp"></jsp:include>

<script type="text/javascript" src="js/getplace.js"></script>
<script type="text/javascript">
	function getInfo() {
		let lat = document.getElementById('lat').value;
		let lnt = document.getElementById('lnt').value;
		
		
		if( !lat && !lnt) {
			alert("내 위치 가져오기를 먼저 실행해주세요");
		} else {
			location.href = "DBServlet?command=insertHistory&lat="+lat+"&lnt="+lnt;
		}
	}

</script>
</body>
</html>