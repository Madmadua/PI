package daeBeans;

import java.util.ArrayList;

public class Boundary {
	private String type;
	private ArrayList<String> values;
	private String description;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<String> getValue() {
		return values;
	}
	public void setValue(ArrayList<String> values) {
		this.values = values;
	}
	
}
