package C209_GA2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


import java.time.LocalDateTime;
import java.time.LocalTime;
/**
 * @author Joel
 *
 */
public class spotsAppMain {
	private Connection conn;
	private Statement statement;
	private ResultSet rs;	
	private ArrayList<JoggingSpot> jogArr = new ArrayList<JoggingSpot>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		spotsAppMain sap = new spotsAppMain();
		sap.start();
	}
	public void start() {
		try {
//			String output = String.format("%-10s %-40s %-15s %-20s %-15s\n", "ID","NAME","CATEGORY","HAS SEA VIEW","Distance(KM)","CLOSING TIME");
			String connectionString = "jdbc:mysql://localhost:3310/joggingspot";
			String userid = "root";
			String password = "";
			DBUtil.init(connectionString, userid, password);
			String sql ="SELECT * FROM jogging_spots_new";
			rs = DBUtil.getTable(sql);
			int choice = 0;
			
			while(choice != 4) {
				rs = DBUtil.getTable(sql);
				addToArray();
				menu();
				
				choice = Helper.readInt("Enter choice > ");
				if (choice == 1) {
					viewAll();
				}
				else if (choice == 2) {
					viewByCategory();
				}
				else if (choice ==3) {
					addJoggingSpot();
				}
				else if (choice == 4) {
					System.out.println("Goodbye! ");
					rs.close();
					statement.close();
					conn.close();
				}
				else {
					System.out.println("Invalid Choice");
				}
			}
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
	}
	private void menu() {
		Helper.line(120, "=");
		System.out.println("SINGAPORE JOGGING SPOTS APP");
		Helper.line(120, "=");
		System.out.println("1. View All Jogging spot");
		System.out.println("2. View Jogging Spots by Category");
		System.out.println("3. Add New Joggin Spot");
		System.out.println("4. Quit");
	}
	private void viewAll() {
		Helper.line(120, "=");
		System.out.println("VIEW ALL JOGGING SPOT");
		Helper.line(120, "=");
		String output = String.format("%-10s %-40s %-15s %-20s %-15s %s\n", "ID","NAME","CATEGORY","HAS SEA VIEW","Distance(KM)","CLOSING TIME");
		for(JoggingSpot js : jogArr) {
			if(js instanceof Park) {
				Park p = (Park)js;
				output += String.format("%-10s %-40s %-15s %-15b\n",p.getId(), p.getName(),"Park",p.isHasSeaView());
			}
			else if(js instanceof ParkConnector) {
				ParkConnector pc = (ParkConnector) js;
				output += String.format("%-10s %-40s %-36s %-20.2f\n",pc.getId(), pc.getName(),"Park Connector",pc.getDistanceKm());
			}
			else if(js instanceof Stadium) {
				Stadium s = (Stadium)js;
				output += String.format("%-10s %-40s %-52s %-15s\n",s.getId(), s.getName(),"Stadium",s.getClosingTime().toString());
			}
		}
		System.out.println(output);
	}
	private void viewByCategory() {
		Helper.line(120, "=");
		System.out.println("VIEW BY CATEGORY");
		Helper.line(120, "=");
		System.out.println("1. Park");
		System.out.println("2. Park Connector");
		System.out.println("3. Stadium");
		int choice = Helper.readInt("Enter choice > ");
		String output = "";
		String un = "";
		int i = 0;
		for(JoggingSpot js : jogArr) {
			if(choice == 1 && js instanceof Park) {
				Park p = (Park)js;
				if(i == 0) {
					output = String.format("%-10s %-40s %-15s %-20s\n", "ID","NAME","CATEGORY","HAS SEA VIEW");
					System.out.println();
				}
				output += String.format("%-10s %-40s %-15s %-15b\n",p.getId(), p.getName(),"Park",p.isHasSeaView());
				i++;
			}
			else if (choice == 2 && js instanceof ParkConnector) {
				ParkConnector pc = (ParkConnector) js;
				if(i == 0) {
					output = String.format("%-10s %-40s %-15s %-15s\n", "ID","NAME","CATEGORY","Distance(KM)");
				}
				output += String.format("%-10s %-40s %-15s %-15.2f\n",pc.getId(), pc.getName(),"Park Connector",pc.getDistanceKm());
				i++;
			}
			else if (choice == 3 && js instanceof Stadium) {
				Stadium s = (Stadium)js;
				if(i==0) {
					output = String.format("%-10s %-40s %-15s %s\n", "ID","NAME","CATEGORY","CLOSING TIME");
				}
				output += String.format("%-10s %-40s %-15s %s\n",s.getId(), s.getName(),"Stadium",s.getClosingTime().toString());
				un = s.announceUnavailability();
				i++;
			}
		}
		i=0;
		if(!output.equals("")) {
			System.out.println(output);
			System.out.println(un);
		}
		else {
			System.out.println("Invalid Jogging Spot!");
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
	private void addJoggingSpot() {
		System.out.println("ADDING JOGGING SPOT");
		Helper.line(120, "-");
		//get values to be inserted into database from the user
		String id = Helper.readString("Enter ID > ");
		String name = Helper.readString("Enter Jogging Spot Name > ");
		String category = Helper.readString("Enter Category (Park/Park Connector/Stadium) >");
		boolean isSeaView = false;
		boolean exist = false;
		for(JoggingSpot j: jogArr) {
			if(j.getId().equalsIgnoreCase(id)) {
				exist = true;
			}
		}
		if(!exist) {
			String sql = null;
			if(category.equalsIgnoreCase("park connector")) {
				double distance = Helper.readDouble("Enter distance(Km) > ");
				sql = "INSERT INTO jogging_spots_new(ID, Name, category_ID, DistanceKm) " 
						+ "VALUES ('" + id + "', '" + name + "', 'C002' ," + distance + ")";
			}
			else if(category.equalsIgnoreCase("park")){
				char hasSeaView = Helper.readChar("Has Sea View (y/n) > ");
				if(hasSeaView == 'y') {
					isSeaView = true;
				}
				sql = "INSERT INTO jogging_spots_new(ID, Name, category_ID, HasSeaview) " 
						+ "VALUES ('" + id + "', '" + name + "', 'C001' ," + 1 + ")";
			}
			
			else if(category.equalsIgnoreCase("stadium")){
				String closingTime = Helper.readString("Enter closing time (HHMMSS) > ");
				sql = "INSERT INTO jogging_spots_new(ID, Name, category_ID, ClosingTime) " 
						+ "VALUES ('" + id + "', '" + name + "','C003' ," + closingTime + ")";
			}
			
			
			int rowsAffected = DBUtil.execSQL(sql);

			if (rowsAffected == 1) {
				System.out.println("Jogging Spot added!");
				
			} else {
				System.out.println("Insert failed!");
			}
		}
		else {
			System.out.println("A Jogging Spot With the Item ID "+id+" Already exists");
		}
	}
}
