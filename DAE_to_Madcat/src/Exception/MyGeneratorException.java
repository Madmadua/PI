package Exception;

public class MyGeneratorException extends Exception{
	
	private String description;

	public MyGeneratorException(String description) {
		super();
		this.description = description;
	}
	
	public MyGeneratorException(){
		super();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
