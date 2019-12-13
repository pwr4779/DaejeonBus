package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import ApiParsing.ApiParsing;
import BusStation.BusStation;
import BusStation.BusStationTable;
import Route.Route;
import Route.RouteTable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RealtimeRouteLocation implements Initializable  {
	private String uRouteName;
	private String uRouteID;
	private String ud_type = "0";
	RouteTable mRouteTable = RouteTable.getInstance();
	BusStationTable mBusStationTable = BusStationTable.getInstance();
	Map<String, Route> routeTable = mRouteTable.getRouteTable();
	Map<String, BusStation> busStationTable = mBusStationTable.getBusStationTable();
	ApiParsing mApiParsing = ApiParsing.getInstance();
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
	private Button SurroundStationButton;
	
	@FXML
	private Button uptypeBtn;
	
	@FXML
	private Button downtypeBtn;
	
	private boolean RouteViewSettingFlag = true;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(RouteViewSettingFlag) {
			RouteTableViewSetting();
			RouteViewSettingFlag = false;
		}
		
		HomeButton.setOnAction(e->handleBtnHome(e));
		BusStationButton.setOnAction(e->handleBtnBusStation(e));
		RoutesearchBtn.setOnAction(e->handleRoutesearchBtn(e));
		SurroundStationButton.setOnAction(e->handleBtnSurroundSearch(e));
		uptypeBtn.setOnAction(e->handleuduptypeBtn(e));
		downtypeBtn.setOnAction(e->handleuddowntypeBtn(e));
	}

	private void handleuddowntypeBtn(ActionEvent event) {
		// TODO Auto-generated method stub
		this.ud_type = "1";
	}

	private void handleuduptypeBtn(ActionEvent event) {
		// TODO Auto-generated method stub
		this.ud_type = "0";
	}

	private void handleRoutesearchBtn(ActionEvent event) {
		// TODO Auto-generated method stub
		uRouteName =  RoutetxtField.getText().trim();
		RealtimeRouteLocationPrint(uRouteName,ud_type);
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
	
	private void handleBtnBusStation(ActionEvent event) {
		try{
		    Parent BusStation = FXMLLoader.load(getClass().getResource("..\\UI/BusStationArrivalGUI.fxml"));
		    Scene scene = new Scene(BusStation);
		    scene.getStylesheets().add(getClass().getResource("..\\UI/application.css").toExternalForm());
		    Stage primaryStage = (Stage)BusStationButton.getScene().getWindow();
		    primaryStage.setScene(scene);	    
		 } catch(Exception e){

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
	
	public void RouteTableViewSetting() {
		// TODO Auto-generated method stub
		System.out.println("busStationTable TableSetting");
		routelist.setItems(FXCollections.observableArrayList());
		for (String key : routeTable.keySet()) {
			Route routevalue = routeTable.get(key);
			// System.out.println(routevalue.RouteID);
			String RouteID = routevalue.getRouteID();
			String RouteName = routevalue.getRouteName();
			String RouteType = routevalue.getRouteType();
			String StartstopID = routevalue.getStartstop();
			String TurnstopID = routevalue.getTurnstop();
			String StartStopName = ((BusStation)busStationTable.get(StartstopID)).getStationName();
			String LastStopName = ((BusStation)busStationTable.get(TurnstopID)).getStationName();
			String item;
			switch (RouteType) {
			case "1":
				item = "급행" + RouteName + " " + StartStopName + " <-> " + LastStopName;
				routelist.getItems().add(item);
				break;
			case "5":
				item = "마을" + RouteName + " " + StartStopName + " <-> " + LastStopName;
				routelist.getItems().add(item);
				break;
			case "6":
				item = "첨단" + RouteName + " " + StartStopName + " <-> " + LastStopName;
				routelist.getItems().add(item);
				break;
			default:
				item = RouteName + " " + StartStopName + " <-> " + LastStopName;
				routelist.getItems().add(item);
				break;
			}
		}
	}
	
	public void RealtimeRouteLocationPrint(String Name, String type) {
		if(Name.contains("급행")) Name = Name.replace("급행", "");
		if(Name.contains("마을")) Name = Name.replace("마음", "");
		if(Name.contains("첨단")) Name = Name.replace("첨단", "");
		
		Route routevalue = null;
		for (String key : routeTable.keySet()) {
			routevalue = routeTable.get(key);
			if (routevalue.getRouteName().equals(Name)) {
				System.out.println("찾음");
				break;	
			}	
		}
		
		if(routevalue == null) {
			errormsg.setText("찾는 노선이 없음.");
			return;
		}
		
		List<String> uBusLocation = mApiParsing.RealtimeRouteLocationParsing(routevalue, routevalue.getRouteID(), type);
		//CaldistanceLocation(routevalue, uBusLocation);
		searchlist.setItems(FXCollections.observableArrayList());
		List<String>  BusstationList = routevalue.getBusstationList();
		List<String> searchdirectionList = new ArrayList<String>();
		
		if(type.equals("1")) {
		     String turnstop =routevalue.getTurnstop();
			 for(int i =BusstationList.indexOf(turnstop); i<BusstationList.size(); i++) {
					searchdirectionList.add(BusstationList.get(i));
			}
		}
		else{
			for(int i =0; i<BusstationList.size(); i++) {
			if(routevalue.getTurnstop().equals(BusstationList.get(i)))
				break;
			searchdirectionList.add(BusstationList.get(i));
			}
		}
		
		for(String stationelement:searchdirectionList) {
			BusStation BusStationvalue = null;
			for (String key : busStationTable.keySet()) {
				BusStationvalue = busStationTable.get(key);
				if ((BusStationvalue.getBUS_NODE_ID()).equals(stationelement.trim())) {
					break;
				}
			}
			
			boolean buslocationflag = false;
			System.out.println(BusStationvalue.getStationName());
			for(String uBusLocationelement:uBusLocation) {
				if(uBusLocationelement.equals(stationelement)) {
					buslocationflag = true;
				}
			}
			if(buslocationflag) {
				String result = String.format("%-7s%s","[bus]",BusStationvalue.getStationName());
				searchlist.getItems().add(result);
				/*
				 * searchlist.setCellFactory(ramda -> new ListCell<String>() { ImageView imgV =
				 * new ImageView(); public void updateItem(String name, boolean empty) {
				 * super.updateItem(name, empty); if (empty) { setText(null); setGraphic(null);
				 * } else { if (name.equals(result)) imgV.setImage(image); setText(name);
				 * setGraphic(imgV); } } });
				 */
			}else {
				String result = String.format("%-10s%s","", BusStationvalue.getStationName());
				searchlist.getItems().add(result);
			}
		}
	}


}
