package DAEStructure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;

public class PageImage {
	
	private int id;
	private String name;
	private int vdpi;
	private int hdpi;
	private int skew;
	private String path;
	private int width;
	private int height;
	private int colordepth;
	
	private ArrayList<PageElementSegment> segments;

	public PageImage(int id, int vdpi, int hdpi, int skew, String path,
			int width, int height, ArrayList<PageElementSegment> segments) {
		super();
		this.id = id;
		this.vdpi = vdpi;
		this.hdpi = hdpi;
		this.skew = skew;
		this.path = path;
		this.width = width;
		this.height = height;
		
		this.segments = segments;
	}
	
	public PageImage(){
		super();
	}
	
	public int getColordepth() {
		return colordepth;
	}

	public void setColordepth(int colordepth) {
		this.colordepth = colordepth;
	}

	public ArrayList<PageElementSegment> getSegments() {
		return segments;
	}

	public void setSegments(ArrayList<PageElementSegment> segments) {
		this.segments = segments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVdpi() {
		return vdpi;
	}

	public void setVdpi(int vdpi) {
		this.vdpi = vdpi;
	}

	public int getHdpi() {
		return hdpi;
	}

	public void setHdpi(int hdpi) {
		this.hdpi = hdpi;
	}

	public int getSkew() {
		return skew;
	}

	public void setSkew(int skew) {
		this.skew = skew;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean insert(BDDAccess bdd,Dataset dataset) throws SQLException{
		String query = "SELECT DAE.DATA_ITEM_UNDERLYING.ID FROM DAE.DATA_ITEM_UNDERLYING WHERE DAE.DATA_ITEM_UNDERLYING.DESCRIPTION = '" + name + "'";
		ResultSet result = bdd.executeQuery(query);
		
		if(!result.next()){
			this.id = bdd.insertDataItem(name, "page_image");
			bdd.insertImageDataItem(id);
			bdd.insertPhysicalImageDataItem(id);

			query = "INSERT INTO DAE.PAGE_IMAGE_UNDERLYING (ID,VDPI,HDPI,PATH,WIDTH,HEIGHT) VALUES (?,?,?,?,?,?)";
			ArrayList<Object> collumns = new ArrayList<Object>();

			collumns.add(this.id);
			collumns.add(this.vdpi);
			collumns.add(this.hdpi);
			collumns.add(this.path);
			collumns.add(this.width);
			collumns.add(this.height);

			bdd.insert(query, collumns);

			query = "INSERT INTO DAE.INCLUDES_PAGE_IMAGE (DATASET_ID,PAGE_IMAGE_ID) VALUES (?,?)";
			collumns = new ArrayList<Object>();

			collumns.add(dataset.getId());
			collumns.add(this.id);

			bdd.insert(query, collumns);
			result.close();
			bdd.closeStatement();
			return true;
		}
		id = result.getInt(1);
		System.err.println("Page Image " + name + " already exists");
		result.close();
		bdd.closeStatement();
		return false;
	}

	
	
}
