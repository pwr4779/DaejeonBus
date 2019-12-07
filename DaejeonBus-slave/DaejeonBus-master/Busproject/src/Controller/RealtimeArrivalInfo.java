package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	
	public void handleMouseClickEx(MouseEvent event) {
		  String ext = ExtimeTable.getSelectionModel().getSelectedItem();
		  if(ext == null) {
			  
			  System.out.println("List Clicked! but any Item I can't See sir? Plz reselct if you want to setting alarm those route");
			  return;
		  }
		  String[] extArr = ext.split(" ");
		  String target = null;
		  String targetStation = null;
		  String route = null;
		  String routeGoing = null;
		 target = extArr[8].replaceAll("분" , "");
		 targetStation = extArr[0];
		 route = extArr[1];
		 routeGoing = extArr[2] + extArr[3];
			try{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Setting Alarm");
				alert.setHeaderText("You want to Setting Alarm?");
				alert.setContentText(route + " " + routeGoing + " On : " + targetStation);
				
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
					int min = Integer.valueOf(target);
					AlarmSet al = new AlarmSet(min, targetStation, route);
					al.alarmSettingAndNotification();
				} else {}
				
				
			 } catch(Exception e) {
			       e.printStackTrace();
			}
	}
	
	private void BusStationArrivalPrint(String BusStationID) {
		ExtimeTable.setItems(FXCollections.observableArrayList());
		ExtimeList=mApiParsing.extimeInfoParsing(BusStationID);
		Iterator<ExtimeInfo> Iterator = ExtimeList.iterator();
		String result = null;
		while (Iterator.hasNext()) {
			ExtimeInfo element = (ExtimeInfo)Iterator.next();
			if(element.getROUTE_TP() != null) {
				switch (element.getROUTE_TP()) {
				case "1":
					result = "급행"+element.getROUTE_NO();
					break;
				case "5":
					result = "마을"+element.getROUTE_NO();
					
					break;
				case "6":
					result = "첨단"+element.getROUTE_NO();	
					break;
				default:
					result = element.getROUTE_NO();
					break;
				}
			}
			if(element.getDESTINATION()!=null) {
				result= result+" ["+element.getDESTINATION()+" 방향]";
			}
			
			if(element.getSTATUS_POS()!= null) {
				result= result+" [잔여정류장(# "+element.getSTATUS_POS()+" )]";
			}
			if(element.getEXTIME_MIN()!= null) {
				result= result+" *예상시간*- "+element.getEXTIME_MIN();
			}
			if(element.getMSG_TP()!=null) {
				switch (element.getMSG_TP()) {
				case "1":
					result = result+"도착";
					break;
				case "2":
					result = result+"분 후 도착";
					break;
				case "3":
					result = result+"진입중";
					break;
				case "7":
					result = result+"차고운행대기중";	
					break;
				default:
					result = result+"분";	
					break;
				}
			} 	
			ExtimeTable.getItems().add("[" + busStationTable.get(BusStationID).getStationName() + "(" + BusStationID + ")" + "] " + result);
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
				.zoom(14);
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
	
}
