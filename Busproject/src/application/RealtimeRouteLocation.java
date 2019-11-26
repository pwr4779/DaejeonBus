package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RealtimeRouteLocation implements Initializable  {
	private String uRouteName;
	private String uRouteID;
	private String ud_type = "0";// ���� 0, ���� 1
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
		errormsg.clear();
		uRouteName =  RoutetxtField.getText().trim();
		RealtimeRouteLocationPrint(uRouteName,ud_type);
	}

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
	
	private void handleBtnBusStation(ActionEvent event) {
		try{
		    Parent BusStation = FXMLLoader.load(getClass().getResource("BusStationArrivalGUI.fxml"));
		    Scene scene = new Scene(BusStation);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    Stage primaryStage = (Stage)BusStationButton.getScene().getWindow();
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
			String RouteID = routevalue.getRouteID(); // �뼱ID
			String RouteName = routevalue.getRouteName(); // �뼱��Ī
			String RouteType = routevalue.getRouteType();// �뼱Ÿ�� 1: ����, 2:����, 3:����, 4:�ܰ�, 5:����, 6:÷��
			String Startstop = routevalue.getStartstop();// �뼱����
			String Turnstop = routevalue.getTurnstop();// �뼱��ȯ����
			String StartStopName = ((BusStation)busStationTable.get(Startstop)).getStationName();
			String LastStopName = ((BusStation)busStationTable.get(Turnstop)).getStationName();
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
		int lastStopIndex = BusstationList.indexOf(routevalue.getLaststop());
		
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
			//System.out.println("ã�� ������"+stationelement);
			BusStation BusStationvalue = null;
			for (String key : busStationTable.keySet()) {
				BusStationvalue = busStationTable.get(key);
				//System.out.println("Ž��"+BusStationvalue.getStationName());
				if ((BusStationvalue.getBUS_NODE_ID()).equals(stationelement.trim())) {
					//System.out.println("ã��"+BusStationvalue.getStationName());
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
				String result = String.format("%-7s%s","[BUS]", BusStationvalue.getStationName());
				searchlist.getItems().add(result);
			}else {
				String result = String.format("%-10s%s","", BusStationvalue.getStationName());
				searchlist.getItems().add(result);
			}
		}
	}

	
	
	/*public void CaldistanceLocation(Route route, List<RouteOfBusLocation> Locationlist) {
		searchlist.setItems(FXCollections.observableArrayList());
		List<String>  BusstationList = route.getBusstationList();
		
		for(String stationelement:BusstationList) {
				BusStation BusStationvalue = null;
				for (String key : busStationTable.keySet()) {
					BusStationvalue = busStationTable.get(key);
					System.out.println("Ž��"+BusStationvalue.getStationName());
					if ((BusStationvalue.getStationName().trim()).equals(stationelement.trim())) {
						System.out.println("ã��"+BusStationvalue.getStationName());
						break;
					}
				}
			System.out.println(BusStationvalue.getStationName());
			double valueLat =Double.parseDouble(BusStationvalue.getLatitude()); 
			double valuelon =Double.parseDouble(BusStationvalue.getLongitude());
			
			for(RouteOfBusLocation element:Locationlist) {
				double lat = Double.parseDouble(element.getGPS_LATI());
				double lon = Double.parseDouble(element.getGPS_LONG());
				
				if(getDistance(valueLat,valuelon,lat,lon)<100) {
					//String result = String.format("%-10s", BusStationvalue.getStationName())+"[BUS]";
					searchlist.getItems().add(BusStationvalue.getStationName()+"[BUS]");
					break;
				}else {
					//String result = String.format("%-10s", BusStationvalue.getStationName());
					searchlist.getItems().add(BusStationvalue.getStationName());
					break;
				}
			}
			
			
		}
		
		return;

	}
	
	//���� �浵�� �� �������� �Ÿ� ��� (���� : m)
	public double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515*1609.344;

        return dist;
	}
	
	 // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }*/

}
