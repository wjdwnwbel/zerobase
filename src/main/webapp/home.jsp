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
LAT: <input type=text id="lat">, LNT: <input type=text id='lnt'> 
<button id = "placebtn" onclick='getUserLocation()'>내 위치 가져오기</button>
<button type=button onclick="getInfo('lat','lnt')">근처 WIFI 정보 보기</button>
</div>

<jsp:include page="/WEB-INF/WifiList.jsp"></jsp:include>

<script type="text/javascript" src="js/getplace.js"></script>
</body>
</html>