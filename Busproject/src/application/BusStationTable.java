package application;

import java.util.HashMap;
import java.util.Map;

public class BusStationTable {
	private static Map<String, BusStation> stationTable = new HashMap<>();
	
	private BusStationTable() {}
	
	private static class _Holder {
		private static final BusStationTable instance = new BusStationTable();
	}

	public static BusStationTable getInstance() {
		return _Holder.instance;
	}

	public Map<String, BusStation> getBusStationTable() {
		return stationTable;
	}	
}