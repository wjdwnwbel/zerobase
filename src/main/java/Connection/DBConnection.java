package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	public void insert(double lat, double lnt) {
		String query = "insert into history(x,y,time) values ("+lat+","+lnt+",now())"+"; alter table history auto_increment=1;"
				+ "set @count = 0; UPDATE history SET id = @count:=@count+1;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.execute();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	// 거리 계산
	
	public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		if (unit == "kilometer") {
			dist = dist * 1.609344;
		} else if(unit == "meter"){
			dist = dist * 1609.344;
		} 

		return (dist);
	}
	
	public double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	public double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	
	public ArrayList<WifiList> getList() {
		ArrayList<WifiList> list = new ArrayList<>();
		String query = "SELECT * from wifi";
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				WifiList wifi = new WifiList();
				wifi.setManageNumber(rs.getString("manageNumber"));
				wifi.setLat(rs.getDouble("lat"));
				wifi.setLnt(rs.getDouble("lnt"));
				list.add(wifi);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public void update(ArrayList<WifiList> list, double paramlat, double paramlnt) {
		
		for(WifiList wifi : list) {
			double result = distance(wifi.getLat(), wifi.getLnt(), paramlat, paramlnt, "kilometer");
			result = Double.valueOf(String.format("%.4f", result));
			System.out.println(wifi.getManageNumber());
			String query = "UPDATE wifi SET distance ="+result+"where manageNumber = '" + wifi.getManageNumber()+"'";
			
			try {
				PreparedStatement ps = conn.prepareStatement(query);
				
				ps.executeUpdate();
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		System.out.println("갱신 완료");
		
	}
	
	public ArrayList<WifiList> selectwifi() {
		String query = "SELECT * from wifi order by distance LIMIT 20";
		ArrayList<WifiList> list = new ArrayList<>();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				WifiList wifi = new WifiList();
				wifi.setDistance(rs.getDouble(1));
				wifi.setManageNumber(rs.getString(2));
				wifi.setBorough(rs.getString(3));
				wifi.setWifiName(rs.getString(4));
				wifi.setSAddress(rs.getString(5));
				wifi.setDAddress(rs.getString(6));
				wifi.setInstallLocation(rs.getString(7));
				wifi.setInstallType(rs.getString(8));
				wifi.setInstallAgency(rs.getString(9));
				wifi.setServiceType(rs.getString(10));
				wifi.setNetType(rs.getString(11));
				wifi.setYear(rs.getInt(12));
				wifi.setDoor(rs.getString(13));
				wifi.setConnEnvironment(rs.getString(14));
				wifi.setLat(rs.getDouble(15));
				wifi.setLnt(rs.getDouble(16));
				wifi.setLocalDateTime(rs.getTimestamp(17).toLocalDateTime());
				list.add(wifi);
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}

