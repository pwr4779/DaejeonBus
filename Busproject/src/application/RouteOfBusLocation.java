package application;

public class RouteOfBusLocation {
	private String GPS_LATI;
	private String GPS_LONG;
	
	public RouteOfBusLocation(String GPS_LATI, String GPS_LONG) {
		this.GPS_LATI = GPS_LATI;
		this.GPS_LONG = GPS_LONG;
	}
	
	public String getGPS_LATI() {
		return GPS_LATI;
	}
	public void setGPS_LATI(String gPS_LATI) {
		GPS_LATI = gPS_LATI;
	}
	
	public String getGPS_LONG() {
		return GPS_LONG;
	}
	public void setGPS_LONG(String gPS_LONG) {
		GPS_LONG = gPS_LONG;
	}
}
