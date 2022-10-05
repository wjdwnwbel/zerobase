package Dao;

public class WifiHistory {
	int id;
	double x;
	double y;
	String Datetime;
	
	public WifiHistory() {}
	
	public WifiHistory(int id, double x, double y, String datetime) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		Datetime = datetime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getDatetime() {
		return Datetime;
	}
	public void setDatetime(String datetime) {
		Datetime = datetime;
	}
	
	
}
