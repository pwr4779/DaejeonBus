package Route;


import java.util.HashMap;
import java.util.Map;

public class RouteTable {
	private static Map<String, Route> routeTable = new HashMap<>();

	private RouteTable(){}

	private static class _Holder {
		
		private static final RouteTable instance = new RouteTable();
	}

	public static RouteTable getInstance() {
		return _Holder.instance;
	}

	public Map<String, Route> getRouteTable() {
		return routeTable;
	}

}

