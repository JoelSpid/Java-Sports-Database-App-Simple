package C209_GA2;
/**
 * @author Joel
 *
 */
public class ParkConnector extends JoggingSpot {
	private double distanceKm;
	ParkConnector(String id, String n, double dkm){
		super(id,n);
		this.distanceKm = dkm;
	}
	public double getDistanceKm() {
		return distanceKm;
	}
	
	
}
