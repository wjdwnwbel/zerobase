package Servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import Connection.DBConnection;
import Connection.WifiConnection;
import Dao.WifiHistory;


@WebServlet("/WifiServlet")
public class WifiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
		
		if(command.equals("getWifi")) {
			WifiConnection wifiConnection = new WifiConnection();
			JSONObject wifiJson = wifiConnection.getJson();
			int count = wifiConnection.getCount(wifiJson);
			wifiConnection.saveWifi();
			request.setAttribute("count", count);
			String url = "load-wifi.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
			
		}
		
		if(command.equals("getHistory")) {
			DBConnection dbConnection = new DBConnection();
			dbConnection.getConn();
			ArrayList<WifiHistory> list = dbConnection.getHistory();
			request.setAttribute("list", list);
			String url = "history.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
