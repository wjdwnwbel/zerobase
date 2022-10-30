package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Dao.WifiList;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WifiConnection {
	String key = "7870434268686a6a3131395145435768";
	int sPage = 1;
	int ePage = 1000;
	String urls = "http://openapi.seoul.go.kr:8088/"+ key + "/json/TbPublicWifiInfo/"
					+ Integer.toString(sPage)+"/"+ Integer.toString(ePage)+"/";

	public void setUrls(int sPage, int ePage) {
		this.urls = "http://openapi.seoul.go.kr:8088/"+ key + "/json/TbPublicWifiInfo/"
				+ Integer.toString(sPage)+"/"+ Integer.toString(ePage)+"/";;
	}


	public JSONObject getJson() {
		JSONObject data  = null;
		try {
			URL url = new URL(urls);
			System.out.println(url.openStream());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
			
			BufferedReader rd;
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <=300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			StringBuilder sbs = new StringBuilder();
			String line;
			while((line = rd.readLine()) != null) {
				sbs.append(line);
			}
			
			rd.close();
			conn.disconnect();
			JSONParser parser = new JSONParser();
			JSONObject jsonObj = (JSONObject) parser.parse(sbs.toString());
			JSONObject jsonObj2 = (JSONObject) jsonObj.get("TbPublicWifiInfo");
			data = jsonObj2;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public int getCount(JSONObject object) {
		String count = object.get("list_total_count").toString();
		return Integer.parseInt(count);
		
	}
	
	public void saveWifi() {
		JSONObject obj = getJson();
		int max = getCount(obj)/1000 +1;

		
		for(int i = 1; i <= max ; i++) {
			setUrls(sPage, ePage);
			obj = getJson();
			getWifi(obj);
			System.out.println("sssssss " + i);
			sPage += 1000;
			ePage += 1000;
			
		}
		
	}
	
	
	public void getWifi(JSONObject object) {
		JSONArray jsonArr = (JSONArray) object.get("row");
		DBConnection dbConnection = new DBConnection();
		dbConnection.getConn();
		
		for(int i = 0; i < jsonArr.size(); i++) {
			JSONObject obj = (JSONObject) jsonArr.get(i);
			
			double distance = 0;
			String manageNumber = obj.get("X_SWIFI_MGR_NO").toString();
			String borough = obj.get("X_SWIFI_WRDOFC").toString();
			String wifiName = obj.get("X_SWIFI_MAIN_NM").toString();
			String sAddress = obj.get("X_SWIFI_ADRES1").toString();
			String dAddress = obj.get("X_SWIFI_ADRES2").toString();
			String installLocation = obj.get("X_SWIFI_INSTL_FLOOR").toString();
			String installType = obj.get("X_SWIFI_INSTL_TY").toString();
			String installAgency = obj.get("X_SWIFI_INSTL_MBY").toString();
			String serviceType = obj.get("X_SWIFI_SVC_SE").toString();
			String netType = obj.get("X_SWIFI_CMCWR").toString();
			int year = obj.get("X_SWIFI_CNSTC_YEAR").equals("") ? 0 : Integer.valueOf(obj.get("X_SWIFI_CNSTC_YEAR").toString());
			// 비어있는경우 0으로 처리
			String door = obj.get("X_SWIFI_INOUT_DOOR").toString();
			String connEnvironment = obj.get("X_SWIFI_REMARS3").toString();
			double lat = Double.valueOf(obj.get("LNT").toString());
			double lnt = Double.valueOf(obj.get("LAT").toString());
			String workYear = obj.get("WORK_DTTM").toString();
			LocalDateTime time =  LocalDateTime.parse(workYear, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
			
			WifiList list = new WifiList(distance, manageNumber, borough, wifiName, sAddress, dAddress, installLocation, installType, installAgency, serviceType, netType, year, door, connEnvironment, lat, lnt, time);
		
			dbConnection.saveJson(list);
		}
	}
	

	
}
