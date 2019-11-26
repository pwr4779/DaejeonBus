package application;


import java.util.HashMap;
import java.util.Map;

public class RouteTable {
	private static Map<String, Route> routeTable = new HashMap<>();

	private RouteTable(){}

	private static class _Holder {
		// instance 최초 1회 할당.
		private static final RouteTable instance = new RouteTable();
	}

	public static RouteTable getInstance() {
		return _Holder.instance;
	}

	public Map<String, Route> getRouteTable() {
		return routeTable;
	}

}

