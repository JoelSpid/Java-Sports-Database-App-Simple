package C209_GA2;
/**
 * @author Joel Samuel
 *
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @author 21006649
 *
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JoggingSpotAddWindow extends Application {
	private BorderPane pane = new BorderPane();
	private HBox idPane = new HBox();
	private Label id = new Label("ID: ");
	private TextField fieldId = new TextField();
	private HBox namePane = new HBox();
	private Label name = new Label("Name: ");
	private TextField fieldName = new TextField();
	private HBox distancePane = new HBox();
	private Label distance = new Label("Distance: ");
	private TextField fieldDistance = new TextField();
	private HBox closingTimePane = new HBox();
	private Label closingTime = new Label("Closing Time: ");
	private TextField fieldClosingTime = new TextField();
	
	private Label category = new Label("Category: ");
	private RadioButton rbPark = new RadioButton("Park");
	private RadioButton rbParkConnector = new RadioButton("Park Connector");
	private RadioButton rbStadium = new RadioButton("Stadium");
	
	private Label seaView = new Label("Has Sea View?");
	private RadioButton hasSeaView = new RadioButton("Yes");
	private RadioButton noSeaView = new RadioButton("No");
	
	private Button submitButton = new Button("Submit");
	private ToggleGroup categoryGroup = new ToggleGroup();
	private ToggleGroup seaViewGroup = new ToggleGroup();
	private VBox mainVPane = new VBox();
	private HBox categoryHPane = new HBox();
	private HBox seaViewPane = new HBox();
	
	private Label statusLabel = new Label("\n");
	RadioButton rb;
	
	private ArrayList<JoggingSpot> jogArr = new ArrayList<JoggingSpot>();
	
	private ResultSet rs;
	
	private static final String JOG_ID = "J\\d\\d\\d\\d";
	public void start(Stage primaryStage) {
		
		String connectionString = "jdbc:mysql://localhost:3310/joggingspot";
		String userid = "root";
		String password = "";
		DBUtil.init(connectionString, userid, password);		
		String sql = "SELECT * FROM jogging_spots_new";
		rs = DBUtil.getTable(sql);
		addToArray();
		
		rbPark.setToggleGroup(categoryGroup);
		rbPark.setPrefWidth(50);
		rbPark.setUserData("P");
		rbParkConnector.setToggleGroup(categoryGroup);
		rbParkConnector.setPrefWidth(110);
		rbParkConnector.setUserData("PC");
		rbStadium.setToggleGroup(categoryGroup);
		rbStadium.setUserData("S");
		
		idPane.setAlignment(Pos.CENTER_LEFT);
		namePane.setAlignment(Pos.CENTER_LEFT);
		distancePane.setAlignment(Pos.CENTER_LEFT);
		closingTimePane.setAlignment(Pos.CENTER_LEFT);
		categoryHPane.setAlignment(Pos.CENTER_LEFT);
		seaViewPane.setAlignment(Pos.CENTER_LEFT);
		
		idPane.setPadding(new Insets(10,10,10,10));
		namePane.setPadding(new Insets(10,10,10,10));
		distancePane.setPadding(new Insets(10,10,10,10));
		closingTimePane.setPadding(new Insets(10,10,10,10));
		categoryHPane.setPadding(new Insets(10,10,10,10));
		seaViewPane.setPadding(new Insets(10,10,10,10));

		
		hasSeaView.setToggleGroup(seaViewGroup);
		noSeaView.setToggleGroup(seaViewGroup);
		
		fieldId.setPrefColumnCount(10);
		fieldName.setPrefColumnCount(10);
		fieldClosingTime.setPrefColumnCount(10);
		fieldDistance.setPrefColumnCount(10);
		

		idPane.setSpacing(10);
		
		distancePane.setVisible(false);
		closingTimePane.setVisible(false);
		seaViewPane.setVisible(false);
		
		idPane.getChildren().addAll(id,fieldId);
		namePane.getChildren().addAll(name,fieldName);
		distancePane.getChildren().addAll(distance,fieldDistance);
		closingTimePane.getChildren().addAll(closingTime,fieldClosingTime);
		categoryHPane.getChildren().addAll(category,rbPark,rbParkConnector,rbStadium);
		seaViewPane.getChildren().addAll(seaView,hasSeaView,noSeaView);
		mainVPane.getChildren().addAll(idPane,namePane,categoryHPane,seaViewPane,distancePane,closingTimePane,submitButton,statusLabel);
		mainVPane.setAlignment(Pos.CENTER);
		
		pane.setStyle("-fx-background-color : #e6ffff;");
		mainVPane.setStyle("-fx-background-color : #e6ffff;");
		mainVPane.setPadding(new Insets(10,10,10,10));
		pane.setTop(mainVPane);
		
		Scene mainScene = new Scene(pane);
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("View Jogging Spot's");
		primaryStage.setHeight(400);
		primaryStage.setWidth(500);
		primaryStage.getIcons().add(new Image("file:/C:/Users/21006649/eclipse-workspace/GradedAssignmentC209Part2/src/icon.png"));
		primaryStage.show();
		
		EventHandler<ActionEvent> handleCategory = (ActionEvent e) -> doDisplayOption(e);
		rbPark.setOnAction(handleCategory);
		rbParkConnector.setOnAction(handleCategory);
		rbStadium.setOnAction(handleCategory);
		
		EventHandler<ActionEvent> handleSubmit = (ActionEvent e) -> doAddJoggingSpot(e);
		submitButton.setOnAction(handleSubmit);
	}
	private void doDisplayOption(ActionEvent e) {
		rb = (RadioButton)e.getSource();
		String category = rb.getUserData().toString();
		if(category.equals("PC")) {
			distancePane.setVisible(true);
			closingTimePane.setVisible(false);
			seaViewPane.setVisible(false);
		}else if(category.equals("P")) {
			seaViewPane.setVisible(true);
			distancePane.setVisible(false);
			closingTimePane.setVisible(false);
		}else if(category.equals("S")) {
			closingTimePane.setVisible(true);
			distancePane.setVisible(false);
			seaViewPane.setVisible(false);
		}
	}
	private void doAddJoggingSpot(ActionEvent e) {
		//get values to be inserted into database from the user
		String id = fieldId.getText();
		String name = fieldName.getText();
		
		boolean isSeaView = false;
		String sql = "";
		boolean exist = false;
		for(JoggingSpot j: jogArr) {
			if(j.getId().equalsIgnoreCase(id)) {
				exist = true;
			}
		}
		if(!id.isEmpty() && !name.isEmpty()) {
			if(!exist) {
				if(id.matches(JOG_ID)) {
					if(rb.getUserData().toString().contains("PC")) {
						double distance = Double.parseDouble(fieldDistance.getText());
						sql = "INSERT INTO jogging_spots_new(ID, Name, category_ID, DistanceKm) " 
								+ "VALUES ('" + id + "', '" + name + "', 'C002' ," + distance + ")";
					}
					else if(rb.getUserData().toString().contains("P")){
						if(hasSeaView.isSelected()) {
							isSeaView = true;
						}
						sql = "INSERT INTO jogging_spots_new(ID, Name, category_ID, HasSeaview) " 
								+ "VALUES ('" + id + "', '" + name + "', 'C001' ," + isSeaView + ")";
					}
					else if(rb.getUserData().toString().contains("S")){
						String closingTime = fieldClosingTime.getText();
						sql = "INSERT INTO jogging_spots_new(ID, Name, category_ID, ClosingTime) " 
								+ "VALUES ('" + id + "', '" + name + "','C003' ," + closingTime + ")";
					}
					int rowsAffected = DBUtil.execSQL(sql);

					if (rowsAffected == 1) {
						statusLabel.setText("Jogging Spot Added!");
						
					} else {
						statusLabel.setText("Failed to insert Jogging Spot, Please Try Again");
					}
				}
				else {
					statusLabel.setText("Please Follow the correct ID format eg:JXXXX where X represents a whole number");
				}
			}
			else {
				statusLabel.setText("A Jogging Spot With the Item ID "+id+" Already exists");
			}
		}
		else {
			statusLabel.setText("Please do not leave any of the fields empty");
		}
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
