package DAEStructure;

import java.util.ArrayList;

public class PageImage {
	
	private int id;
	private int vdpi;
	private int hdpi;
	private int skew;
	private String path;
	private int width;
	private int height;
	private int colordepth;
	
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
	
	
	
}
