package DAEStructure;
import java.util.ArrayList;


public class PageElementZone {
	
	private int id;
	
	private String boundary;
	
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
	
	

}
