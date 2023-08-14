package C209_GA2;

/**
 * @author Joel
 *
 */
public class Park extends JoggingSpot {
	private boolean hasSeaView;
	Park(String id , String n, boolean hsv){
		super(id,n);
		hasSeaView = hsv;
	}
	
	public boolean isHasSeaView() {
		return hasSeaView;
	}
	
	
}
