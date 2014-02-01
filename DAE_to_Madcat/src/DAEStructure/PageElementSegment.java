package DAEStructure;

import java.util.ArrayList;

public class PageElementSegment {
	private int id;
	
	private String boundary;
	
	private ArrayList<PageElementZone> zones;
	private PageElementPropertyValue transcription;
	private PageElementPropertyValue traduction;
	
	public PageElementSegment(int id, String boundary,
			ArrayList<PageElementZone> zones,
			PageElementPropertyValue transcription,
			PageElementPropertyValue traduction) {
		super();
		this.id = id;
		this.boundary = boundary;
		this.zones = zones;
		this.transcription = transcription;
		this.traduction = traduction;
	}
	
	public PageElementSegment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	public ArrayList<PageElementZone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<PageElementZone> zones) {
		this.zones = zones;
	}

	public PageElementPropertyValue getTranscription() {
		return transcription;
	}

	public void setTranscription(PageElementPropertyValue transcription) {
		this.transcription = transcription;
	}

	public PageElementPropertyValue getTraduction() {
		return traduction;
	}

	public void setTraduction(PageElementPropertyValue traduction) {
		this.traduction = traduction;
	}
	
	
}
