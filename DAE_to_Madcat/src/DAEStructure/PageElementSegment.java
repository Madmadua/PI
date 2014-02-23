package DAEStructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;

public class PageElementSegment {
	private int id;
	
	private String boundary;
	private String name;
	private int topLeftX;
	private int topLeftY;
	private int width;
	private int height;
	
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

			query = "INSERT INTO DAE.PAGE_ELEMENT_UNDERLYING (ID,TOPLEFTX,TOPLEFTY,WIDTH,HEIGHT) VALUES (?,?,?,?,?)";
			ArrayList<Object> collumns = new ArrayList<Object>();

			collumns.add(this.id);
			collumns.add(this.topLeftX);
			collumns.add(this.topLeftY);
			collumns.add(this.width);
			collumns.add(this.height);
			
			//collumns.add(this.boundary);

			bdd.insert(query, collumns);

			query = "INSERT INTO DAE.CONTAINS_PAGE_ELEMENT (PAGE_ELEMENT_ID,PAGE_IMAGE_ID) VALUES (?,?)";
			collumns = new ArrayList<Object>();

			collumns.add(this.id);
			collumns.add(image.getId());

			bdd.insert(query, collumns);
			result.close();
			bdd.closeStatement();
			return true;
		}
		id = result.getInt(1);
		System.err.println("Page Element " + name + " already exists");
		result.close();
		bdd.closeStatement();
		return false;
	}

	public int getTopLeftX() {
		return topLeftX;
	}

	public void setTopLeftX(int topLeftX) {
		this.topLeftX = topLeftX;
	}

	public int getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(int topLeftY) {
		this.topLeftY = topLeftY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
}
