package madcatBeans;

import java.util.ArrayList;

public class Page {
	private String id;
	private int dpi;
	private int colordepth;
	private int width;
	private int heigh;
	
	private ArrayList<Zone> zones;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDpi() {
		return dpi;
	}

	public void setDpi(int dpi) {
		this.dpi = dpi;
	}

	public int getColordepth() {
		return colordepth;
	}

	public void setColordepth(int colordepth) {
		this.colordepth = colordepth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigh() {
		return heigh;
	}

	public void setHeigh(int heigh) {
		this.heigh = heigh;
	}

	public ArrayList<Zone> getZones() {
		return zones;
	}

	public void setZones(ArrayList<Zone> zones) {
		this.zones = zones;
	}
}
