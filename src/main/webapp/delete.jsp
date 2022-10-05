<%@page import="Connection.DBConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	
	DBConnection conn = new DBConnection();
	conn.getConn();
	conn.delete(id);
	
	RequestDispatcher ds = request.getRequestDispatcher("WifiServlet?command=getHistory");
	ds.forward(request, response);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>