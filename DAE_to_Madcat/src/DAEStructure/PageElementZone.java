package DAEStructure;
import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;
import Constants.DataTypeProperty;


public class PageElementZone {
	
	private int id;
	
	private String boundary;
	private String name;
	private String type;
	private int topLeftX;
	private int topLeftY;
	private int width;
	private int height;
	private ArrayList<Point> points;
	
	private ArrayList<PageElementToken> mots;
	private PageElementPropertyValue transcription;
	private PageElementPropertyValue traduction;
	
	public PageElementZone(int id, String boundary,
			ArrayList<PageElementToken> mots,
			PageElementPropertyValue transcription,
			PageElementPropertyValue traduction) {
		super();
		this.id = id;
		this.boundary = boundary;
		this.mots = mots;
		this.transcription = transcription;
		this.traduction = traduction;
	}

	public PageElementZone(){
		super();
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<PageElementToken> getMots() {
		return mots;
	}

	public void setMots(ArrayList<PageElementToken> mots) {
		this.mots = mots;
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

	public String getBoundary() {
		return boundary;
	}

	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public boolean insert(BDDAccess bdd, PageElementSegment segment,PageImage image) throws SQLException{
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

			collumns.add(segment.getId());
			collumns.add(this.id);
			

			bdd.insert(query, collumns);
			
			query = "INSERT INTO DAE.CONTAINS_PAGE_ELEMENT (PAGE_ELEMENT_ID,PAGE_IMAGE_ID) VALUES (?,?)";
			collumns = new ArrayList<Object>();

			collumns.add(this.id);
			collumns.add(image.getId());

			bdd.insert(query, collumns);
			
			query = "INSERT INTO ASSOCIATE_DATATYPE_DATA_ITEM (DATA_ITEM_ID,DATATYPE_ID) VALUES (?,?)";
			
			collumns = new ArrayList<Object>();
			
			collumns.add(this.id);
			collumns.add(DataTypeProperty.ZONE);
			
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

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	

}
