package C209_GA2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.regex.Pattern;


import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * @author Joel
 *
 */
public class JoggingSpotViewWindow extends Application {
	private Text header = new Text();
	private Label header1 = new Label();
	private Text headerPark = new Text();
	private Text headerParkC = new Text();
	private Text headerStadium = new Text();
	private CheckBox park = new CheckBox("Park");
	private CheckBox parkConnector = new CheckBox("Park Connector");
	private CheckBox stadium = new CheckBox("Stadium");
	private ScrollPane scrollPane;
	private BorderPane pane ;
	private HBox topHPane = new HBox();
	private VBox topVPane = new VBox();
	private ResultSet rs;
	private ArrayList<JoggingSpot> jogArr = new ArrayList<JoggingSpot>();
	private Text unavailability = new Text();
	
	private TableView tablePark = new TableView();
	private TableView tableParkC = new TableView();
	private TableView tableStadium = new TableView();
	
	private TableColumn columnIdPark = new TableColumn("ID");
	private TableColumn columnNamePark = new TableColumn("Name");
	private TableColumn columnIdParkC = new TableColumn("ID");
	private TableColumn columnNameParkC = new TableColumn("Name");
	private TableColumn columnIdStadium = new TableColumn("ID");
	private TableColumn columnNameStadium = new TableColumn("Name");
	
	private TableColumn columnSeaView = new TableColumn("Has Sea View");
	private TableColumn columnDistance = new TableColumn("Distance");
	private TableColumn columnClosingTime = new TableColumn("Closing Time");
	
	
		public void start(Stage primaryStage) {
			String connectionString = "jdbc:mysql://localhost:3310/joggingspot";
			String userid = "root";
			String password = "";
			DBUtil.init(connectionString, userid, password);
			String sql ="SELECT * FROM jogging_spots_new";
			rs = DBUtil.getTable(sql);
			addToArray();
			     
			park.setPrefWidth(100);
			parkConnector.setPrefWidth(100);
			stadium.setPrefWidth(100);
			
			parkConnector.setPrefWidth(125);
			
			Stop[] stops = new Stop[] { new Stop(0, Color.AQUA), new Stop(1, Color.AQUAMARINE)};  
			LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops); 
			
			
			tablePark.setEditable(true);
			tablePark.setPrefHeight(300);
			tablePark.setPrefWidth(685);
			tableParkC.setEditable(true);
			tableParkC.setPrefHeight(300);
			tableParkC.setPrefWidth(685);
			tableStadium.setEditable(true);
			tableStadium.setPrefHeight(300);
			tableStadium.setPrefWidth(685);
			
			columnIdPark.setPrefWidth(10);
			columnIdPark.setMinWidth(100);
			columnIdPark.setCellValueFactory(new PropertyValueFactory<JoggingSpot,String>("id"));
			
			columnNamePark.setPrefWidth(40);
			columnNamePark.setMinWidth(385);
			columnNamePark.setCellValueFactory(new PropertyValueFactory<JoggingSpot,String>("name"));
			
			columnIdParkC.setPrefWidth(10);
			columnIdParkC.setMinWidth(100);
			columnIdParkC.setCellValueFactory(new PropertyValueFactory<JoggingSpot,String>("id"));
			
			columnNameParkC.setPrefWidth(40);
			columnNameParkC.setMinWidth(385);
			columnNameParkC.setCellValueFactory(new PropertyValueFactory<JoggingSpot,String>("name"));
			
			columnIdStadium.setPrefWidth(10);
			columnIdStadium.setMinWidth(100);
			columnIdStadium.setCellValueFactory(new PropertyValueFactory<JoggingSpot,String>("id"));
			
			columnNameStadium.setPrefWidth(40);
			columnNameStadium.setMinWidth(385);
			columnNameStadium.setCellValueFactory(new PropertyValueFactory<JoggingSpot,String>("Name"));
			
			columnDistance.setPrefWidth(5);
			columnDistance.setMinWidth(200);
			columnDistance.setCellValueFactory(new PropertyValueFactory<ParkConnector,String>("id"));
			
			columnSeaView.setPrefWidth(10);
			columnSeaView.setMinWidth(200);
			columnSeaView.setCellValueFactory(new PropertyValueFactory<Park,String>("hasSeaView"));
			
			columnClosingTime.setPrefWidth(10);
			columnClosingTime.setMinWidth(200);
			columnClosingTime.setCellValueFactory(new PropertyValueFactory<Stadium,String>("closingTime"));
			
			
			header.setText("WELCOME");
			header.setFill(linearGradient);
			header.setFont(Font.font("Times New Roman",FontWeight.SEMI_BOLD,50));
			header1.setText("Select A Jogging Spot To Display");
			headerPark.setText("PARK");
			headerPark.setFont(Font.font("Verdana", 15));
			headerParkC.setText("PARK CONNECTOR");
			headerParkC.setFont(Font.font("Verdana", 15));
			headerStadium.setText("Stadium");
			headerStadium.setFont(Font.font("Verdana", 15));
			
			topHPane.getChildren().addAll(park,parkConnector,stadium);
			topHPane.setSpacing(100);
			topHPane.setPadding(new Insets(10,10,10,10));
			topVPane.setPadding(new Insets(10,10,10,10));
			topHPane.setAlignment(Pos.CENTER);
			topVPane.getChildren().addAll(header,header1,topHPane,headerPark,tablePark,headerParkC,tableParkC,headerStadium,unavailability,tableStadium);
			topVPane.setAlignment(Pos.CENTER);
			
			scrollPane = new ScrollPane(topVPane);
			scrollPane.setPrefWidth(720);
			scrollPane.setPadding(new Insets(10,10,10,10));
			topVPane.setStyle("-fx-background-color : #e6ffff;");
			scrollPane.setStyle("-fx-background-color : #e6ffff;");
			pane = new BorderPane(scrollPane);

			tablePark.getColumns().addAll(columnIdPark,columnNamePark,columnSeaView);
			tableParkC.getColumns().addAll(columnIdParkC,columnNameParkC,columnDistance);
			tableStadium.getColumns().addAll(columnIdStadium,columnNameStadium,columnClosingTime);
			
			
			Scene mainScene = new Scene(pane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("View Jogging Spot's");
			primaryStage.setHeight(720);
			primaryStage.setWidth(750);
			primaryStage.getIcons().add(new Image("file:/C:/Users/21006649/eclipse-workspace/GradedAssignmentC209Part2/src/icon.png"));
			primaryStage.show();
			
			EventHandler<ActionEvent> handlePark = (ActionEvent e) -> onCheckBoxPark();
			park.setOnAction(handlePark);
			EventHandler<ActionEvent> handlePC = (ActionEvent e) -> onCheckBoxParkConnector();
			parkConnector.setOnAction(handlePC);
			EventHandler<ActionEvent> handleStadium = (ActionEvent e) -> onCheckBoxStadium();
			stadium.setOnAction(handleStadium);
		}
		private void onCheckBoxPark() {
			if(park.isSelected()) {
				for(JoggingSpot js : jogArr) {
					if(js instanceof Park) {
						Park p = (Park)js;
						tablePark.getItems().add(p);
					}
				}
			}
			else {
				tablePark.getItems().clear();
			}
			
		}
		private void onCheckBoxParkConnector() {
			if(parkConnector.isSelected()) {
				for(JoggingSpot js : jogArr) {
					if(js instanceof ParkConnector) {
						ParkConnector pc = (ParkConnector) js;
						tableParkC.getItems().add(pc);
					}
				}
			}
			else {
				tableParkC.getItems().clear();
			}
		}
		private void onCheckBoxStadium() {
			String un = "";
			unavailability.setFont(Font.font ("Verdana", 13));
			unavailability.setFill(Color.RED);
			if(stadium.isSelected()) {
				for(JoggingSpot js : jogArr) {
					if(js instanceof Stadium) {
						Stadium s = (Stadium)js;
						tableStadium.getItems().add(s);
						un = s.announceUnavailability();
					}
				}
				
			}
			else {
				tableStadium.getItems().clear();
			}
			unavailability.setText(un);
		}
		private void addToArray() {
			jogArr.clear();
			try {
				while (rs.next()) {
					String id = rs.getString("ID");
					String name = rs.getString("Name");
					String category = rs.getString("category_ID");
					if (category.equalsIgnoreCase("C001")) {
						boolean hasSeaView = rs.getBoolean("HasSeaview");
						jogArr.add(new Park(id,name,hasSeaView));
					}
					else if (category.equalsIgnoreCase("C002")) {
						double distance = rs.getDouble("DistanceKm");
						jogArr.add(new ParkConnector(id,name,distance));
					}
					else if (category.equalsIgnoreCase("C003")){
						LocalTime closingTime = rs.getTime("ClosingTime").toLocalTime();
						jogArr.add(new Stadium(id,name,closingTime));
					}
				}	
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}

	}


