package DAEStructure;
import java.util.ArrayList;


public class DatasetSection {
	
	private int id;
	private String name;
	private String purpose;

	public DatasetSection(int id, String name, String purpose) {
		super();
		this.id = id;
		this.name = name;
		this.purpose = purpose;
	}
	
	public DatasetSection(){
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
	
	

}
