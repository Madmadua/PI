package DAEStructure;
public class PageElementPropertyValue {
	
	private int id;
	private int valueType;
	private String value;
	
	public PageElementPropertyValue(int id, int valueType, String value) {
		super();
		this.id = id;
		this.valueType = valueType;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
