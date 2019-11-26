package application;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
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

public class RealtimeArrivalInfo implements  Initializable, MapComponentInitializedListener, DirectionsServiceCallback {
	
	protected DirectionsService directionsService;
    protected DirectionsPane directionsPane;

	
    protected StringProperty from = new SimpleStringProperty();
    protected StringProperty to = new SimpleStringProperty();
	
    BusStationTable mBusStationTable = BusStationTable.getInstance();
    Map<String, BusStation> busStationTable = mBusStationTable.getBusStationTable();
    
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
	
	private GoogleMap map;
	
	
	private void handleBtnHome(ActionEvent event) {
		try{
		    Parent Home = FXMLLoader.load(getClass().getResource("HomeGUI.fxml"));
		    Scene scene = new Scene(Home);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
			LatLong joeSmithLocation = new LatLong(47.6197, -122.3231);
	        LatLong joshAndersonLocation = new LatLong(47.6297, -122.3431);
	        LatLong bobUnderwoodLocation = new LatLong(47.6397, -122.3031);
	        LatLong tomChoiceLocation = new LatLong(47.6497, -122.3325);
	        LatLong fredWilkieLocation = new LatLong(47.6597, -122.3357);
			
			
	        MapOptions options = new MapOptions();
	        options.center(new LatLong(36.35111, 127.38500))
	                .zoomControl(true)
	                .zoom(12)
	                .overviewMapControl(false)
	                .mapType(MapTypeIdEnum.ROADMAP);
	        map = mapView.createMap(options);
	        
	      //Add markers to the map
	        MarkerOptions markerOptions1 = new MarkerOptions();
	        markerOptions1.position(joeSmithLocation);
	        
	        MarkerOptions markerOptions2 = new MarkerOptions();
	        markerOptions2.position(joshAndersonLocation);
	        
	        MarkerOptions markerOptions3 = new MarkerOptions();
	        markerOptions3.position(bobUnderwoodLocation);
	        
	        MarkerOptions markerOptions4 = new MarkerOptions();
	        markerOptions4.position(tomChoiceLocation);
	        
	        MarkerOptions markerOptions5 = new MarkerOptions();
	        markerOptions5.position(fredWilkieLocation);
	        
	        Marker joeSmithMarker = new Marker(markerOptions1);
	        Marker joshAndersonMarker = new Marker(markerOptions2);
	        Marker bobUnderwoodMarker = new Marker(markerOptions3);
	        Marker tomChoiceMarker= new Marker(markerOptions4);
	        Marker fredWilkieMarker = new Marker(markerOptions5);
	        
	        map.addMarker( joeSmithMarker );
	        map.addMarker( joshAndersonMarker );
	        map.addMarker( bobUnderwoodMarker );
	        map.addMarker( tomChoiceMarker );
	        map.addMarker( fredWilkieMarker );
	        
	        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
	        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
	                                + "Current Location: Safeway<br>"
	                                + "ETA: 45 minutes" );

	        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
	        fredWilkeInfoWindow.open(map, fredWilkieMarker);
	        
	        
	    }
		
		
		public void findStationLocation() {
			BusStation BusStationvalue = null;
			for (String key : busStationTable.keySet()) {
				BusStationvalue = busStationTable.get(key);
				if ((BusStationvalue.getBUS_NODE_ID()).equals(stationelement.trim())) {
					break;
				}
			}
		}
	
}
