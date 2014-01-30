package DAEStructure;
import java.util.ArrayList;


public class DatasetContent {
	
	private int id;
	private String name;
	private String purpose;
	
	private ArrayList<PageElementZone> zones;

	public DatasetContent(int id, String name, String purpose,
			ArrayList<PageElementZone> zones) {
		super();
		this.id = id;
		this.name = name;
		this.purpose = purpose;
		this.zones = zones;
	}
	
	public DatasetContent(){
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public ArrayList<PageElementZone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<PageElementZone> zones) {
		this.zones = zones;
	}
	
	

}