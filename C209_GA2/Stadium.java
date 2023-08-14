package C209_GA2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Joel
 *
 */
public class Stadium extends JoggingSpot implements Unavailability{
	private Connection conn;
	private Statement statement;
	private ResultSet rs;	
	private LocalTime closingTime;
	Stadium(String id, String n, LocalTime ct){
		super(id,n);
		this.closingTime = ct;
	}
	public LocalTime getClosingTime() {
		return closingTime;
	}
	
	public String announceUnavailability() {
		String output = "";
		boolean ifUnavail = false;
		try {
			output = "These stadium will be unavailable on the following days due to the following events: \n";
			String connectionString = "jdbc:mysql://localhost:3310/singapore_joggin_spots_app";
			String userid = "root";
			String password = "";
			
			conn = DriverManager.getConnection(connectionString, userid, password);
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM unavailability_date INNER JOIN jogging_spot ON jogging_spot.ID = unavailability_date.ID WHERE DateUnavailable BETWEEN '" +LocalDate.now()+"'"+ "AND" +"'"+(LocalDate.now().plusDays(14))+"'");
			while (rs.next()) {
				LocalDate du = rs.getDate("DateUnavailable").toLocalDate();
				String event = rs.getString("Event");
				String name = rs.getString("Name");
				if(!du.equals(null)) {
					output+= String.format("%-20s Unavailable on %-10s due to %s\n",name,du.toString(), event);
					ifUnavail = true;
				}
			}
			rs.close();
			statement.close();
			conn.close();
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		if(ifUnavail) {
			return output;
		}
		else{
			return "";
		}
	}
}
