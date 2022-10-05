package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import Dao.WifiHistory;
import Dao.WifiList;

public class DBConnection {
	private static String name = "root";
	private static String password = "1234";
	private static String url = "jdbc:mysql://localhost:3306/wifidb?allowMultiQueries=true&serverTimezone=UTC";
	
	Connection conn = null;
	
	
	public void getConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, name, password);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public ArrayList<WifiHistory> getHistory() {
		ArrayList<WifiHistory> list = new ArrayList<>();
		String query = "SELECT * from history order by id desc";
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				WifiHistory history = new WifiHistory();
				history.setId(rs.getInt("id"));
				history.setX(rs.getDouble("x"));
				history.setY(rs.getDouble("y"));
				history.setDatetime(rs.getTimestamp("time").toString());
				list.add(history);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void saveJson(WifiList list) {
		String query = "INSERT INTO wifi VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDouble(1, list.getDistance());
			ps.setString(2, list.getManageNumber());
			ps.setString(3, list.getBorough());
			ps.setString(4, list.getWifiName());
			ps.setString(5, list.getSAddress());
			ps.setString(6, list.getDAddress());
			ps.setString(7, list.getInstallLocation());
			ps.setString(8, list.getInstallType());
			ps.setString(9, list.getInstallAgency());
			ps.setString(10, list.getServiceType());
			ps.setString(11, list.getNetType());
			ps.setInt(12, list.getYear());
			ps.setString(13, list.getDoor());
			ps.setString(14, list.getConnEnvironment());
			ps.setDouble(15, list.getLat());
			ps.setDouble(16, list.getLnt());
			ps.setTimestamp(17, Timestamp.valueOf(list.getLocalDateTime()));
			
			ps.executeUpdate();
			
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void delete(String id) {
		String query = "delete from history where id="+id + "; alter table history auto_increment=1;"
				+ "set @count = 0; UPDATE history SET id = @count:=@count+1;";
		// 삭제하고나서 id 값 다시 설정
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			boolean suss = ps.execute();
			
			if(!suss) {
				throw new Exception("데이터를 삭제할 수 없습니다.");
			}
			
			
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}

