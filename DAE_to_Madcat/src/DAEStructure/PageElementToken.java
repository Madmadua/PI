package DAEStructure;

public class PageElementToken {
	
	private int id;
	private int number_of_pixels;
	private int topLeftX;
	private int topLeftY;
	private int width;
	private int height;
	
	private PageElementPropertyValue transcription;
	private PageElementPropertyValue traduction;
	
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
	
	

}
