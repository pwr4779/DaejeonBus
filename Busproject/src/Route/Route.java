package Route;

import java.util.ArrayList;
import java.util.List;

public class Route {
	private String RouteID; 
	private String RouteName; 
	private String RouteType;
	private String Startstop;
	private String Laststop;
	private String turnstop;

	private List<String> BusstationList = new ArrayList<String>();
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
