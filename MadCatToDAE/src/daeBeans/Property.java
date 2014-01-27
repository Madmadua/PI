package daeBeans;

import java.util.ArrayList;

public class Property {
	private String id;
	private String name;
	private String description;
	private ArrayList<Type> typeList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Type> getTypeList() {
		return typeList;
	}
	public void setTypeList(ArrayList<Type> typeList) {
		this.typeList = typeList;
	}
	
}
