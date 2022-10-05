<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
	body {
		text-align: center;
	}
</style>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
</head>
<body>
<h1> <%= request.getAttribute("count") %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1><br>
	<a href='home.jsp'>홈 으로 가기</a>
</body>
</html>