package Dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class WifiHistory {
	int id;
	double x;
	double y;
	String Datetime;
	

	public WifiHistory(int id, double x, double y, String datetime) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		Datetime = datetime;
	}
}
