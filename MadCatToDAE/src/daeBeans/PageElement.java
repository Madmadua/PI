package daeBeans;

import java.util.ArrayList;
import java.util.Hashtable;

public class PageElement {
	private String id;
	private String description;
	private String type;
	private RectangleBoundary rectangle;
	private ArrayList<Value> valueList;
	private ArrayList<String> contributors;
	private ArrayList<Boundary> boundaryList;
	private ArrayList<PageElement> pageElementList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public RectangleBoundary getRectangle() {
		return rectangle;
	}
	public void setRectangle(RectangleBoundary rectangle) {
		this.rectangle = rectangle;
	}
	
	public ArrayList<Value> getValueList() {
		return valueList;
	}
	public void setValueList(ArrayList<Value> valueList) {
		this.valueList = valueList;
	}
	public ArrayList<String> getContributors() {
		return contributors;
	}
	public void setContributors(ArrayList<String> contributors) {
		this.contributors = contributors;
	}
	public ArrayList<Boundary> getBoundaryList() {
		return boundaryList;
	}
	public void setBoundaryList(ArrayList<Boundary> boundaryList) {
		this.boundaryList = boundaryList;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<PageElement> getSubElement() {
		return pageElementList;
	}
	public void setSubElement(ArrayList<PageElement> subElement) {
		this.pageElementList = subElement;
	}
	
	
	
	
}
