package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
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

public class FindNearestStop implements Initializable, MapComponentInitializedListener {
	
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
	
    @FXML
    private void toTextFieldAction(ActionEvent event) {
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
        GoogleMap map = mapView.createMap(options);
        
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
 		   LatLong latLong = event.getLatLong();
 		   System.out.println("Latitude: " + latLong.getLatitude());
 		   System.out.println("Longitude: " + latLong.getLongitude());
        });
    }

	
	
	
	
}