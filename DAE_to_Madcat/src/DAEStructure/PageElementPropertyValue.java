package DAEStructure;

import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;

public class PageElementPropertyValue {
	
	private int id;
	private String name;
	private String valueType;
	private String value;
	private int valueTypeId;
	
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
	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public int getValueTypeId() {
		return valueTypeId;
	}

	public void setValueTypeId(int valueTypeId) {
		this.valueTypeId = valueTypeId;
	}
	
	public void insertWithPageElement(BDDAccess bdd,int pageElementId) throws SQLException{
		id = bdd.insertDataItem(name, "page_element_property_value");
		
		bdd.insertImageDataItem(id);
		bdd.insertLogicalImageDataItem(id);
		
		
		String query = "INSERT INTO DAE.PAGE_ELEMENT_P_VAL_UNDERLYING (ID,VALUE_TYPE,VALUE) VALUES (?,?,?)";
		ArrayList<Object> collumns = new ArrayList<Object>();

		collumns.add(this.id);
		collumns.add(this.valueTypeId);
		collumns.add(this.value);

		bdd.insert(query, collumns);
		
		query = "INSERT INTO DAE.HAS_VALUE (PAGE_ELEMENT_PROPERTY_VALUE_ID,PAGE_ELEMENT_ID) VALUES (?,?)";
		collumns = new ArrayList<Object>();
		collumns.add(id);
		collumns.add(pageElementId);
		
		bdd.insert(query,collumns);
		
		
		
		
	}
	public void insertWithDataset(BDDAccess bdd,int datasetId) throws SQLException{
		id = bdd.insertDataItem(name, "page_element_property_value");
		
		bdd.insertImageDataItem(id);
		bdd.insertLogicalImageDataItem(id);
		
		
		String query = "INSERT INTO DAE.PAGE_ELEMENT_P_VAL_UNDERLYING (ID,VALUE_TYPE,VALUE) VALUES (?,?,?)";
		ArrayList<Object> collumns = new ArrayList<Object>();

		collumns.add(this.id);
		collumns.add(this.valueTypeId);
		collumns.add(this.value);

		bdd.insert(query, collumns);
		
		query = "INSERT INTO DAE.INCLUDES_PE_PV (PAGE_ELEMENT_PROPERTY_VALUE_ID,DATASET_ID) VALUES (?,?)";
		collumns = new ArrayList<Object>();
		collumns.add(id);
		collumns.add(datasetId);
		
		bdd.insert(query,collumns);
		
	}

}
