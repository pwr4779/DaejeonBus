package BusStation;

public class BusStation {
	String StationName; 
	String BUS_NODE_ID; 
	String Latitude; 
	String Longitude; 
	String RouteList; 
	
	public String getStationName() {
		return StationName;
	}

	public void setStationName(String stationName) {
		StationName = stationName;
	}

	public String getBUS_NODE_ID() {
		return BUS_NODE_ID;
	}

	public void setBUS_NODE_ID(String bUS_NODE_ID) {
		BUS_NODE_ID = bUS_NODE_ID;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getRouteList() {
		return RouteList;
	}

	public void setRouteList(String routeList) {
		RouteList = routeList;
	}

	public BusStation(String BUS_NODE_ID, String stationName, String Latitude, String Longitude) { 
		this.BUS_NODE_ID =  BUS_NODE_ID;
		this.StationName = stationName;
		this.Latitude = Latitude;
		this.Longitude = Longitude;
		
	}
	
}



