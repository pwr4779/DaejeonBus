package application;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HomeController implements Initializable {
	ApiParsing mApiParsing =  ApiParsing.getInstance();
	
	@FXML
	private Button RouteButton;
	
	@FXML
	private Button BusStationButton;
	
	@FXML
	private Button SurroundStationButton;
	
	private boolean Startflag = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if(Startflag) {
			mApiParsing.initData();
			Startflag = false;
		}
		RouteButton.setOnAction(e->handleBtnRouteSearch(e));
		BusStationButton.setOnAction(e->handleBtnBusSearch(e));
		SurroundStationButton.setOnAction(e->handleBtnSurroundSearch(e));
		/*Image image = new Image(getClass().getResourceAsStream("GPS.png"));
	
		SurroundStationButton.setGraphic(new ImageView(image));*/
	}


	private void handleBtnRouteSearch(ActionEvent event) {
		try{
		    Parent RouteSearch = FXMLLoader.load(getClass().getResource("RouteLocationGUI.fxml"));

		    Scene scene = new Scene(RouteSearch);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    Stage primaryStage = (Stage)RouteButton.getScene().getWindow(); 
		    primaryStage.setScene(scene);
		    
		 } catch(Exception e){

		       e.printStackTrace();

		}
	}
	
	private void handleBtnBusSearch(ActionEvent event) {
		try{
		    Parent RouteSearch = FXMLLoader.load(getClass().getResource("BusStationArrivalGUI.fxml"));

		    Scene scene = new Scene(RouteSearch);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    Stage primaryStage = (Stage)BusStationButton.getScene().getWindow(); 
		    primaryStage.setScene(scene);
		    
		 } catch(Exception e){

		       e.printStackTrace();

		}
	}
	
	private void handleBtnSurroundSearch(ActionEvent event) {
		try{
		    Parent RouteSearch = FXMLLoader.load(getClass().getResource("SurroundStationGUI.fxml"));

		    Scene scene = new Scene(RouteSearch);
		    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    Stage primaryStage = (Stage)RouteButton.getScene().getWindow(); 
		    primaryStage.setScene(scene);
		    
		 } catch(Exception e){

		       e.printStackTrace();

		}
	}
	
}
