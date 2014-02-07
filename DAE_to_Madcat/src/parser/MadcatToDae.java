package parser;

import java.sql.SQLException;

import org.xml.sax.Attributes;

import BDDAccess.BDDAccess;
import Constants.DataTypeProperty;
import DAEStructure.Dataset;
import DAEStructure.PageElementPropertyValue;
import DAEStructure.PageImage;

public class MadcatToDae {
	private BDDAccess bdd;
	private Dataset dataset;
	private PageImage image;
	
	
	public MadcatToDae(){
		bdd = new BDDAccess();
	}
	
	public void insertDoc(Attributes attributes){
		String id = attributes.getValue("id");
		String src = attributes.getValue("src");
		String nbPages = attributes.getValue("nbpages");
		String type = attributes.getValue("type");
		
		dataset = new Dataset();
		dataset.setName(id);
		dataset.setPurpose(type);
		
		PageElementPropertyValue pepv = new PageElementPropertyValue();
		pepv.setName("Dataset " + id + " : number of pages");
		pepv.setValue(nbPages);
		pepv.setValueTypeId(DataTypeProperty.NBPAGES);
		
		
		try {
			if(dataset.insert(bdd)){
				pepv.insertWithDataset(bdd, dataset.getId());
			}
		} catch (SQLException e) {
			System.err.println("Fail to insert dataset " + id); 
			e.printStackTrace();
		}
		
	}
	
	public void insertWriter(Attributes attributes){
		String id = attributes.getValue("id");
		
		try {
			bdd.insertContributor(dataset.getId(), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertPage(Attributes attributes){
		String id = attributes.getValue("id");
		int dpi = Integer.parseInt(attributes.getValue("dpi"));
		int colordepth = Integer.parseInt(attributes.getValue("colordepth"));
		int width = Integer.parseInt(attributes.getValue("width"));
		int height = Integer.parseInt(attributes.getValue("height"));
		
		image = new PageImage();
		image.setName(id);
		image.setHdpi(dpi);
		image.setVdpi(dpi);
		image.setWidth(width);
		image.setHeight(height);
		/* Ne peut pas être mis dans DAE pour le moment
		image.setColordepth(colordepth); 
		*/
		
		try {
			image.insert(bdd, dataset);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
