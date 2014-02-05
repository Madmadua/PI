package DAEStructure;

import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;

public class PageElementPropertyValue {
	
	private int id;
	private String name;
	private int valueTypeId;
	private String value;
	
	public PageElementPropertyValue(int id, int valueTypeId, String value) {
		super();
		this.id = id;
		this.valueTypeId = valueTypeId;
		this.value = value;
	}
	
	public PageElementPropertyValue(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValueTypeId() {
		return valueTypeId;
	}

	public void setValueTypeId(int valueTypeId) {
		this.valueTypeId = valueTypeId;
	}
	
	
	public void insert(BDDAccess bdd,PageElementToken token) throws SQLException{
		int id = bdd.insertDataItem("OpenHart Document", "dataset");
		
		bdd.insertImageDataItem(id);
		bdd.insertLogicalImageDataItem(id);

		this.setId(id);
		
		
		String query = "INSERT INTO DAE.PAGE_ELEMENT_P_VALUE_UNDERLYING (ID,NAME) VALUES (?,?)";
		ArrayList<Object> collumns = new ArrayList<Object>();

		collumns.add(this.id);
		collumns.add(this.name);

		bdd.insert(query, collumns);
		
		// reste association + value_type

	}

	
	
}
