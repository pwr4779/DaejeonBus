package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import javafx.collections.FXCollections;
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

public class RealtimeArrivalInfo implements  Initializable, MapComponentInitializedListener {
	private String StationName;
	private List<BusStation> Stationlist = new ArrayList<BusStation>();
	private String StationNumber;
    BusStationTable mBusStationTable = BusStationTable.getInstance();
    Map<String, BusStation> busStationTable = mBusStationTable.getBusStationTable();
    
	@FXML
	private TextField BusStationtxtField;
	
	@FXML
	private TextField errormsg;
	
	@FXML
	ListView<String> searchlist;
	
	@FXML
	private Button HomeButton;
	
	@FXML
	private Button RouteButton;
	
	@FXML
	private Button BusStationButton;
	
	@FXML
	private Button BusStationsearchBtn;
	
	@FXML
	private Button SurroundStationButton;
	
	@FXML
	private Button uptypeBtn;
	
	@FXML
	private Button downtypeBtn;
	
	
	@FXML 
	GoogleMapView mapView;  
	
	private GoogleMap map;
	
	
	private void handleBusStationsearchBtn(ActionEvent e) {
		// TODO Auto-generated method stub
		searchlist.setItems(FXCollections.observableArrayList());
		StationName = BusStationtxtField.getText().trim();
		findStationLocation(StationName);
		BusStation Station = Stationlist.get(0);
		MapOptions options = new MapOptions();
		double latitude = Double.parseDouble(Station.getLatitude());
		double longtitude = Double.parseDouble(Station.getLongitude());
		options.center(new LatLong(latitude, longtitude)).mapType(MapTypeIdEnum.ROADMAP).overviewMapControl(false)
				.panControl(false).rotateControl(false).scaleControl(false).streetViewControl(false).zoomControl(false)
				.zoom(12);
		map = mapView.createMap(options);
		stationMarking();

	}
	
	
	
	private void stationMarking() {
		System.out.println("making start");
		for (int i = 0, n = Stationlist.size(); i < n; i++) {
			BusStation Station = Stationlist.get(i);
			double latitude = Double.parseDouble(Station.getLatitude());
			double longtitude = Double.parseDouble(Station.getLongitude());
			LatLong Location = new LatLong(latitude, longtitude);
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.position(Location);
			Marker Marker = new Marker(markerOptions);
			
			map.addMarker(Marker);
			InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
			String info =Station.getStationName() +"("+ Station.getBUS_NODE_ID()+")"; //+"Location("+latitude+","+longtitude+")"
			infoWindowOptions.content(info);
			InfoWindow InfoWindow = new InfoWindow(infoWindowOptions);
			InfoWindow.open(map, Marker);
		}
	}
	
	public void findStationLocation(String uStationName) {

		BusStation BusStationvalue = null;
		for (String key : busStationTable.keySet()) {
			BusStationvalue = busStationTable.get(key);
			// System.out.println(BusStationvalue.getStationName());
			String element = BusStationvalue.getStationName();
			if (element.indexOf(uStationName) > -1) {
				Stationlist.add(BusStationvalue);
				String result = BusStationvalue.getStationName() + "(" + BusStationvalue.getBUS_NODE_ID() + ")";
				searchlist.getItems().add(result);
			}
		}
	}

	@Override
	public void mapInitialized() {
		MapOptions options = new MapOptions();
		options.center(new LatLong(36.35111, 127.38500)).zoomControl(true).zoom(12).overviewMapControl(false)
				.mapType(MapTypeIdEnum.ROADMAP);
		map = mapView.createMap(options);
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		HomeButton.setOnAction(e -> handleBtnHome(e));
		HomeButton.setOnAction(e -> handleBtnHome(e));
		RouteButton.setOnAction(e->handleBtnRouteSearch(e));
		BusStationsearchBtn.setOnAction(e -> handleBusStationsearchBtn(e));
		SurroundStationButton.setOnAction(e->handleBtnSurroundSearch(e));
		mapView.addMapInializedListener(this);
	}
	
	
	
	private void handleBtnHome(ActionEvent event) {
		try{
		    Parent Home = FXMLLoader.load(getClass().getResource("HomeGUI.fxml"));
		    Scene scene = new Scene(Home);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    Stage primaryStage = (Stage)HomeButton.getScene().getWindow(); 
			primaryStage.setScene(scene);
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	private void handleBtnRouteSearch(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			Parent RouteSearch = FXMLLoader.load(getClass().getResource("RouteLocationGUI.fxml"));

			Scene scene = new Scene(RouteSearch);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage primaryStage = (Stage) RouteButton.getScene().getWindow(); // Route ������ ��������
			primaryStage.setScene(scene);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	private void handleBtnSurroundSearch(ActionEvent event) {
		try{
		    Parent RouteSearch = FXMLLoader.load(getClass().getResource("SurroundStationGUI.fxml"));

		    Scene scene = new Scene(RouteSearch);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    Stage primaryStage = (Stage)RouteButton.getScene().getWindow(); // Route ������ ��������
		    primaryStage.setScene(scene);
		    
		 } catch(Exception e){

		       e.printStackTrace();

		}
	}
}
