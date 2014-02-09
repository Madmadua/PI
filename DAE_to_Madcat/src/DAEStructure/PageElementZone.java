package DAEStructure;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;


public class PageElementZone {
	
	private int id;
	
	private String boundary;
	private String name;
	private String type;
	
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
	
	public boolean insert(BDDAccess bdd, PageElementSegment segment) throws SQLException{
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

			query = "INSERT INTO DAE.ASSOCIATE_PAGE_ELEMENT (PAGE_ELEMENT_ID,ASSOCIATING_PAGE_ELEMENT_ID) VALUES (?,?)";
			collumns = new ArrayList<Object>();

			collumns.add(segment.getId());
			collumns.add(this.id);

			bdd.insert(query, collumns);
			return true;
		}
		id = result.getInt(1);
		System.err.println("Page Element " + name + " already exists");
		return false;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
