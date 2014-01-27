package madcatBeans;

public class Token {
	private String id;
	private String ref_id;
	private String status;
	private boolean handprint;
	private String source;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRef_id() {
		return ref_id;
	}
	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isHandprint() {
		return handprint;
	}
	public void setHandprint(boolean handprint) {
		this.handprint = handprint;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
