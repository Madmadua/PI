package daeBeans;

import java.util.ArrayList;
import java.util.Hashtable;

public class Dataset {
	private String id;
	private ArrayList<PageImage> images;
	private String path;
	private String associateId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public ArrayList<PageImage> getImages() {
		return images;
	}
	public void setImages(ArrayList<PageImage> images) {
		this.images = images;
	}
	public String getAssociateId() {
		return associateId;
	}
	public void setAssociateId(String contentId) {
		this.associateId = contentId;
	}
		
	
}
