package DAEStructure;
import java.util.ArrayList;


public class PageElementZone {
	
	private int id;
	
	private String boundary;
	
	private ArrayList<PageElementToken> mots;
	private PageElementPropertyValue transcription;
	private PageElementPropertyValue traduction;
	
	public PageElementZone(int id, int number_of_pixels, int topLeftX,
			int topLeftY, int width, int height,
			ArrayList<PageElementToken> mots,
			PageElementPropertyValue transcription,
			PageElementPropertyValue traduction) {
		super();
		this.id = id;
		
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
	
	

}
