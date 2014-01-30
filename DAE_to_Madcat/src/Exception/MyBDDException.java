package Exception;

public class MyBDDException extends Exception{

	private String description;

	public MyBDDException(String description) {
		super();
		this.description = description;
	}
	
	public MyBDDException(){
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
