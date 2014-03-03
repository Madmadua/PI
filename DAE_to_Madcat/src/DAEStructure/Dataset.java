package DAEStructure;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;


public class Dataset {

	private int id;
	private String name;
	private String purpose;

	private ArrayList<PageImage> images;
	private PageElementPropertyValue writer;


	public Dataset(int id, String name, String purpose, ArrayList<PageImage> images) {
		super();
		this.id = id;
		this.name = name;
		this.purpose = purpose;
		this.images = images;
	}

	public Dataset(){
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

	public ArrayList<PageImage> getImages() {
		return images;
	}

	public void setImages(ArrayList<PageImage> images) {
		this.images = images;
	}

	public PageElementPropertyValue getWriter() {
		return writer;
	}

	public void setWriter(PageElementPropertyValue writer) {
		this.writer = writer;
	}

	public boolean insert(BDDAccess bdd) throws SQLException{
		
		String query = "SELECT DAE.DATA_ITEM_UNDERLYING.ID FROM DAE.DATA_ITEM_UNDERLYING WHERE DAE.DATA_ITEM_UNDERLYING.DESCRIPTION = '" + name + "'";
		ResultSet result = bdd.executeQuery(query);
		
		if(!result.next()){
			id = bdd.insertDataItem(this.name, "dataset");

			bdd.insertImageDataItem(id);
			bdd.insertLogicalImageDataItem(id);

			query = "INSERT INTO DAE.DATASET_UNDERLYING (ID,NAME) VALUES (?,?)";
			ArrayList<Object> collumns = new ArrayList<Object>();

			collumns.add(this.id);
			collumns.add(this.name);

			bdd.insert(query, collumns);
			result.close();
			bdd.closeStatement();
			return true;
		}
		id = result.getInt(1);
		System.err.println("Dataset " + name + " already exists");
		result.close();
		bdd.closeStatement();
		return false;
	}

}
