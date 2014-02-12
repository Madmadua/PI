package DAEStructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;

public class PageElementToken {
	
	private int id;
	private String name;
	private int number_of_pixels;
	private int topLeftX;
	private int topLeftY;
	private int width;
	private int height;
	
	private PageElementPropertyValue transcription;
	private PageElementPropertyValue traduction;
	private PageElementPropertyValue source;
	
	private PageElementZone parent;
	
	public PageElementToken(int id, int number_of_pixels, int topLeftX,
			int topLeftY, int width, int height,
			PageElementPropertyValue transcription,
			PageElementPropertyValue traduction) {
		super();
		this.id = id;
		this.number_of_pixels = number_of_pixels;
		this.topLeftX = topLeftX;
		this.topLeftY = topLeftY;
		this.width = width;
		this.height = height;
		this.transcription = transcription;
		this.traduction = traduction;
	}
	
	public PageElementToken(){
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber_of_pixels() {
		return number_of_pixels;
	}

	public void setNumber_of_pixels(int number_of_pixels) {
		this.number_of_pixels = number_of_pixels;
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
	
	public PageElementPropertyValue getSource() {
		return source;
	}

	public void setSource(PageElementPropertyValue source) {
		this.source = source;
	}
	
	public PageElementZone getParent() {
		return parent;
	}

	public void setParent(PageElementZone parent) {
		this.parent = parent;
	}

	
	public boolean insert(BDDAccess bdd, PageElementZone zone) throws SQLException{
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

			query = "INSERT INTO DAE.ASSOCIATE_PAGE_ELEMENT (PAGE_ELEMENT_ID,ASSOCIATING_PAGE_ELEMENT_ID) VALUES (?,?)";
			collumns = new ArrayList<Object>();

			collumns.add(zone.getId());
			collumns.add(this.id);

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
