package C209_GA2;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 * Joel_Samuel_Vijay Amirtharaj 21006649, 30 Jul 2022 5:13:52 pm
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
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

/**
 * @author 21006649
 *
 */
public class JogginSpotMainWindow extends Application{

		public static final int MAX_WIDTH = 200;
		public static final int MAX_HEIGHT = 300;
		private BorderPane mainPane = new BorderPane();		
		private Text header = new Text();
		private Text subHeader = new Text();
		private VBox VPane = new VBox();
		
		private Button buttonView = new Button("View Jogging Spots");
		private Button buttonAdd = new Button("Add a Jogging Spot");
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			launch(args);
		}
		public void start(Stage primaryStage) {
			buttonView.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
			buttonAdd.setMaxSize(MAX_WIDTH, MAX_HEIGHT);

			
			buttonView.setFont(Font.font("Serif",FontWeight.LIGHT,15));
			buttonAdd.setFont(Font.font("Serif",FontWeight.LIGHT,15));
			
			Stop[] stops = new Stop[] { new Stop(0, Color.AQUA), new Stop(1, Color.AQUAMARINE)};  
			LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops); 
			
			header.setText("WELCOME");
			header.setFill(linearGradient);
			header.setFont(Font.font("Times New Roman",FontWeight.SEMI_BOLD,35));
			
			subHeader.setText("Click on a button to get started");
			subHeader.setFill(Color.BLACK);
			subHeader.setFont(Font.font("Verdana",FontWeight.MEDIUM,15));
			
			primaryStage.getIcons().add(new Image("file:/C:/Users/21006649/eclipse-workspace/GradedAssignmentC209Part2/src/icon.png"));

			
			VPane.setStyle("-fx-background-color : #e6ffff;");
			mainPane.setStyle("-fx-background-color : #e6ffff;");
			
			VPane.getChildren().addAll(header,subHeader,buttonAdd,buttonView);
			VPane.setAlignment(Pos.CENTER);
			VPane.setSpacing(10);
			VPane.setPadding(new Insets(10,10,10,10));
			mainPane.setCenter(VPane);
			
			Scene mainScene = new Scene(mainPane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Jogging Spot App");
			primaryStage.setHeight(250);
			primaryStage.setWidth(300);
			primaryStage.show();
			
			EventHandler<ActionEvent> handleAdd = (ActionEvent e) -> (new JoggingSpotAddWindow()).start(new Stage());
			buttonAdd.setOnAction(handleAdd);
			EventHandler<ActionEvent> handleView = (ActionEvent e) -> (new JoggingSpotViewWindow()).start(new Stage());
			buttonView.setOnAction(handleView);
	}
}
