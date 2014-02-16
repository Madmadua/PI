package DAEStructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;

public class PageElementSegment {
	private int id;
	
	private String boundary;
	private String name;
	
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean insert(BDDAccess bdd, PageImage image) throws SQLException{
		String query = "SELECT DAE.DATA_ITEM_UNDERLYING.ID FROM DAE.DATA_ITEM_UNDERLYING WHERE DAE.DATA_ITEM_UNDERLYING.DESCRIPTION = '" + name + "'";
		ResultSet result = bdd.executeQuery(query);
		
		if(!result.next()){
			this.id = bdd.insertDataItem(name, "page_element");
			bdd.insertImageDataItem(id);
			bdd.insertPhysicalImageDataItem(id);

			query = "INSERT INTO DAE.PAGE_ELEMENT_UNDERLYING (ID) VALUES (?)";
			ArrayList<Object> collumns = new ArrayList<Object>();

			collumns.add(this.id);
			//collumns.add(this.boundary);

			bdd.insert(query, collumns);

			query = "INSERT INTO DAE.CONTAINS_PAGE_ELEMENT (PAGE_ELEMENT_ID,PAGE_IMAGE_ID) VALUES (?,?)";
			collumns = new ArrayList<Object>();

			collumns.add(this.id);
			collumns.add(image.getId());

			bdd.insert(query, collumns);
			bdd.closeStatement();
			return true;
		}
		id = result.getInt(1);
		System.err.println("Page Element " + name + " already exists");
		bdd.closeStatement();
		return false;
	}

	
}
