package BusStation;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlarmSet {
	private int ExTime;
	private String stationName;
	private String route;
	public AlarmSet(int ExTime, String stationName, String route) {
		this.ExTime = ExTime;
		this.stationName = stationName;
		this.route = route;
	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("RING DING DONG");
					alert.setHeaderText("INCOMING - [" + stationName + "]" + " Route : " + route);
					alert.setContentText("WAKE UP THE BUS IS COMING!");
					alert.show();
				}
			});
		}
	};

	public void alarmSettingAndNotification() {
		Timer timer = new Timer();
		timer.schedule(task, this.ExTime * 1000*60);
	//	timer.schedule(task, this.ExTime * 1000 * 60);
	}


}
