package Dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WifiList {
	double distance;
	String manageNumber;
	String borough;
	String wifiName;
	String sAddress;
	String dAddress;
	String installLocation;
	String installType;
	String installAgency;
	String serviceType;
	String netType;
	int year;
	String door;
	String connEnvironment;
	double lat;
	double lnt;
	LocalDateTime localDateTime;
	
}
