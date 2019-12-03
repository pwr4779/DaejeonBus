package BusStation;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Controller.RealtimeArrivalInfo;

public class AlarmSet {
	private Date date;
	private String RouteID;
	private String StationID;
	
	public AlarmSet(Date date, String stationID, String routeID) {
		this.date = date;
		this.StationID = stationID;
		this.RouteID = routeID;
	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("The Bus Has Arrived!");
		}
	};

	public void alarmSettingAndNotification() {
		Timer timer = new Timer();
		RealtimeArrivalInfo rta = new RealtimeArrivalInfo();
		rta.requestTimeInfoUseAPI(date, StationID, RouteID);
		timer.schedule(task, 10 * 1000 * 60);
	}


}
