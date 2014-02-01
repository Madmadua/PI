package DAEStructure;
public class PageElementPropertyValue {
	
	private int id;
	private String valueType;
	private String value;
	
	public PageElementPropertyValue(int id, String valueType, String value) {
		super();
		this.id = id;
		this.valueType = valueType;
		this.value = value;
	}
	
	public PageElementPropertyValue(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
