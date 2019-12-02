package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;

import BusStation.BusStation;
import BusStation.BusStationTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindNearestStop implements Initializable, MapComponentInitializedListener {
	BusStationTable mBusStationTable = BusStationTable.getInstance();
    Map<String, BusStation> busStationTable = mBusStationTable.getBusStationTable();
    List<BusStation> surroundList = new ArrayList<>();
	@FXML
	private TextField RoutetxtField;
	
	@FXML
	private TextField errormsg;
	
	@FXML
	ListView<String> routelist;
	
	@FXML
	ListView<String> searchlist;
	
	@FXML
	private Button HomeButton;
	
	@FXML
	private Button RouteButton;
	
	@FXML
	private Button BusStationButton;
	
	@FXML
	private Button RoutesearchBtn;
	
	@FXML
	private Button uptypeBtn;
	
	@FXML
	private Button downtypeBtn;
	
	@FXML 
	GoogleMapView mapView; 
	
	 GoogleMap map;
	
    @FXML
    private void toTextFieldAction(ActionEvent event) {
    }
    
	private void handleBtnHome(ActionEvent event) {
		try{
		    Parent Home = FXMLLoader.load(getClass().getResource("..\\UI/HomeGUI.fxml"));
		    Scene scene = new Scene(Home);
		    scene.getStylesheets().add(getClass().getResource("..\\UI/application.css").toExternalForm());
		    Stage primaryStage = (Stage)HomeButton.getScene().getWindow();
		    primaryStage.setScene(scene);	    
		 } catch(Exception e){

		       e.printStackTrace();

		}
	}
	
	
	

    public void initialize(URL url, ResourceBundle rb) {
    	
    	HomeButton.setOnAction(e->handleBtnHome(e));
        mapView.addMapInializedListener(this);
    }
	
	
	@Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();
        options.center(new LatLong(36.35111, 127.38500))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        map = mapView.createMap(options);
        
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
 		   LatLong latLong = event.getLatLong();
 		   System.out.println("Latitude: " + latLong.getLatitude());
 		   System.out.println("Longitude: " + latLong.getLongitude());
 			double latitude =  latLong.getLatitude();
 			double longtitude = latLong.getLongitude();
 			//findNearestStation(latitude,longtitude);
 			options.center(new LatLong(latitude, longtitude)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false)
 					.panControl(false).rotateControl(false).scaleControl(false).streetViewControl(false).zoomControl(false)
 					.zoom(17);
 			map = mapView.createMap(options);
 			stationMarking(latitude,longtitude);
        });
    }
	
	private void stationMarking(double mlat, double mlong) {
		System.out.println("making start");
		LatLong Location = new LatLong(mlat, mlong);
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(Location);
		Marker Marker = new Marker(markerOptions);
		map.addMarker(Marker);
		InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
		String info ="현재위치";
		infoWindowOptions.content(info);
		InfoWindow InfoWindow = new InfoWindow(infoWindowOptions);
		InfoWindow.open(map, Marker);
		BusStation BusStationvalue = null;
		// for(String key : busStationTable.keySet()){
		// BusStationvalue = busStationTable.get(key);
		for(String key : busStationTable.keySet()){
			 BusStationvalue = busStationTable.get(key);
			double latitude = Double.parseDouble(BusStationvalue.getLatitude());
			double longtitude = Double.parseDouble(BusStationvalue.getLongitude());
			if (getDistance(latitude, longtitude, mlat, mlong) < 500) {
				LatLong NearestStationLocation = new LatLong(latitude, longtitude);
				markerOptions = new MarkerOptions();
				markerOptions.position(NearestStationLocation);
				Marker = new Marker(markerOptions);
				map.addMarker(Marker);
				infoWindowOptions = new InfoWindowOptions();
				info = BusStationvalue.getStationName() + "(" + BusStationvalue.getBUS_NODE_ID() + ")";
				infoWindowOptions.content(info);
				InfoWindow = new InfoWindow(infoWindowOptions);
				InfoWindow.open(map, Marker);

			}

		}
		
	}
	
	public double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = Math.abs(dist * 60 * 1.1515*1609.344);

        return dist;
	}
	
	 // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
	
}