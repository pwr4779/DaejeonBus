package application;

import java.util.ArrayList;
import java.util.List;

public class Route {
	private String RouteID; //노선ID
	private String RouteName; // 노선명칭
	private String RouteType;//노선타입 1: 급행, 2:간선, 3:지선, 4:외곽, 5:마을, 6:첨단
	private String Startstop;//노선기점
	private String Laststop;//노선정점
	private String turnstop;// 반환정점

	private List<String> BusstationList = new ArrayList<String>();// 노선의 전체경유정류장ID 리스트
	private List<String> forwardBusstationList = new ArrayList<String>();
	private List<String> reverseBusstationList = new ArrayList<String>();

	public Route(String RouteID, String RouteName, String RouteType, String Startstop, String Laststop,
			String turnstop) {
		this.RouteID = RouteID;
		this.RouteName = RouteName;
		this.RouteType = RouteType;
		this.Startstop = Startstop;
		this.Laststop = Laststop;
		this.turnstop = turnstop;
	}
	
	public String getStartstop() {
		return Startstop;
	}

	public void setStartstop(String startstop) {
		Startstop = startstop;
	}

	public String getLaststop() {
		return Laststop;
	}

	public void setLaststop(String laststop) {
		Laststop = laststop;
	}

	public String getRouteID() {
		return RouteID;
	}
	public void setRouteID(String routeID) {
		RouteID = routeID;
	}
	public String getRouteName() {
		return RouteName;
	}
	public void setRouteName(String routeName) {
		RouteName = routeName;
	}
	public String getRouteType() {
		return RouteType;
	}
	public void setRouteType(String routeType) {
		RouteType = routeType;
	}
	
	public String getTurnstop() {
		return turnstop;
	}

	public void setTurnstop(String turnstop) {
		this.turnstop = turnstop;
	}
	
	public void addBusStationList(String mBusStation) {
		BusstationList.add(mBusStation);
	}
	
	public List<String> getForwardBusstationList() {
		return forwardBusstationList;
	}

	public List<String> getBusstationList() {
		return BusstationList;
	}

	public void setBusstationList(List<String> busstationList) {
		BusstationList = busstationList;
	}
	
	
	public void setForwardBusstationList(List<String> forwardBusstationList) {
		this.forwardBusstationList = forwardBusstationList;
	}

	public List<String> getReverseBusstationList() {
		return reverseBusstationList;
	}

	public void setReverseBusstationList(List<String> reverseBusstationList) {
		this.reverseBusstationList = reverseBusstationList;
	}
	
}
