package C209_GA2;
import java.time.LocalDate;

/**
 * @author Joel Samuel
 *
 */
public abstract class JoggingSpot {
	private String id;
	private String name;
	JoggingSpot(String iD, String n){
		id = iD;
		name = n;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
