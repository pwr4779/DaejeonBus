package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

import ApiParsing.ApiParsing;
import BusStation.AlarmSet;
import BusStation.BusStation;
import BusStation.BusStationTable;
import BusStation.ExtimeInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;

public class RealtimeArrivalInfo implements  Initializable, MapComponentInitializedListener {
	private String StationName;
	private List<BusStation> Stationlist = new ArrayList<BusStation>();
	private List<ExtimeInfo> ExtimeList = new ArrayList<ExtimeInfo>();
	private String StationNumber;
    BusStationTable mBusStationTable = BusStationTable.getInstance();
    Map<String, BusStation> busStationTable = mBusStationTable.getBusStationTable();
    ApiParsing mApiParsing = ApiParsing.getInstance();
    
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
	private ListView<String> ExtimeTable;
	
	
	@FXML 
	GoogleMapView mapView;  
	
	private GoogleMap map;
	
	
	
	@FXML
	public void handleMouseClick(MouseEvent event) {
		  String selectlist = searchlist.getSelectionModel().getSelectedItem();
		  System.out.println("clicked on " + selectlist);
		  String[] select = selectlist.split("[\\[\\]]");
		  System.out.println(select[1]);
		  BusStationArrivalPrint(select[1]);
		 
	}
	
	private void BusStationArrivalPrint(String BusStationID) {
		ExtimeTable.setItems(FXCollections.observableArrayList());;
		ExtimeList=mApiParsing.extimeInfoParsing(BusStationID);
		Iterator<ExtimeInfo> Iterator = ExtimeList.iterator();
		String result;
		while (Iterator.hasNext()) {
			ExtimeInfo element = (ExtimeInfo)Iterator.next();
			switch (element.getROUTE_TP()) {
			case "1":
				result = String.format("%-10s%-20s%-5s%4s","급행"+element.getROUTE_NO(),"잔여정류장수"+"("+element.getSTATUS_POS()+")","최근통과정류장 -"+busStationTable.get(element.getLAST_STOP_ID()).getStationName(),element.getEXTIME_MIN()+"분");
				ExtimeTable.getItems().add(result);
				break;
			case "5":
				result = String.format("%-10s%-20s%-5s%4s","마을"+element.getROUTE_NO(),"잔여정류장수"+"("+element.getSTATUS_POS()+")","최근통과정류장 -"+busStationTable.get(element.getLAST_STOP_ID()).getStationName(),element.getEXTIME_MIN()+"분");
				ExtimeTable.getItems().add(result);
				break;
			case "6":
				result = String.format("%-10s%-20s%-5s%4s","첨단"+element.getROUTE_NO(),"잔여정류장수"+"("+element.getSTATUS_POS()+")","최근통과정류장 -"+busStationTable.get(element.getLAST_STOP_ID()).getStationName(),element.getEXTIME_MIN()+"분");
				ExtimeTable.getItems().add(result);
				break;
			default:
				result = String.format("%-10s%-20s%-5s%4s",element.getROUTE_NO(),"잔여정류장수"+"("+element.getSTATUS_POS()+")","최근통과정류장 -"+busStationTable.get(element.getLAST_STOP_ID()).getStationName(),element.getEXTIME_MIN()+"분");
				ExtimeTable.getItems().add(result);
				break;
			}
		}
	}
	
	
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
			String info =Station.getStationName() +"["+ Station.getBUS_NODE_ID()+"]"; //+"Location("+latitude+","+longtitude+")"
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
				String result = BusStationvalue.getStationName() + "[" + BusStationvalue.getBUS_NODE_ID() + "]";
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
		    Parent Home = FXMLLoader.load(getClass().getResource("..\\UI/HomeGUI.fxml"));
		    Scene scene = new Scene(Home);
		    scene.getStylesheets().add(getClass().getResource("..\\UI/application.css").toExternalForm());
		    Stage primaryStage = (Stage)HomeButton.getScene().getWindow(); 
			primaryStage.setScene(scene);
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	private void handleBtnRouteSearch(ActionEvent event) {
		// TODO Auto-generated method stub
		try {
			Parent RouteSearch = FXMLLoader.load(getClass().getResource("..\\UI/RouteLocationGUI.fxml"));

			Scene scene = new Scene(RouteSearch);
			scene.getStylesheets().add(getClass().getResource("..\\UI/application.css").toExternalForm());
			Stage primaryStage = (Stage) RouteButton.getScene().getWindow();
			primaryStage.setScene(scene);

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	private void handleBtnSurroundSearch(ActionEvent event) {
		try{
		    Parent RouteSearch = FXMLLoader.load(getClass().getResource("..\\UI/SurroundStationGUI.fxml"));

		    Scene scene = new Scene(RouteSearch);
		    scene.getStylesheets().add(getClass().getResource("..\\UI/application.css").toExternalForm());
		    Stage primaryStage = (Stage)RouteButton.getScene().getWindow();
		    primaryStage.setScene(scene);
		    
		 } catch(Exception e){

		       e.printStackTrace();

		}
	}

	public void requestTimeInfoUseAPI(Date date, String stationID, String routeID) {
		// TODO Auto-generated method stub
		AlarmSet alarm = new AlarmSet(date, stationID, routeID);
	}




	
}
