package Servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Connection.DBConnection;
import Dao.WifiList;


@WebServlet("/DBServlet")
public class DBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
		
		if(command.equals("insertHistory")) {
			double lat = Double.valueOf(request.getParameter("lat"));
			double lnt = Double.valueOf(request.getParameter("lnt"));
			
			DBConnection conn = new DBConnection();
			conn.getConn();
			conn.insert(lat, lnt);
			
			ArrayList<WifiList> list = conn.getList();	// 업데이트용 list
			
			conn.update(list, lat, lnt);
			ArrayList<WifiList> lists = conn.selectwifi();
			request.setAttribute("list", lists);
			request.setAttribute("lat", lat);
			request.setAttribute("lnt", lnt);
			String url = "home.jsp";
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
