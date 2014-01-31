package parser;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import BDDAcces.BDDAcces;
import DAEStructure.Dataset;
import DAEStructure.PageElementPropertyValue;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;



public class MadcatHandler extends DefaultHandler{
	private StringBuffer buffer;
	private String segmentId;
	
	
	public MadcatHandler(File daeOutput){
		super();
		
	}
	
	public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
				
				if(qName.equals("segment")){
					segmentId = attributes.getValue("id");
				}
				if(qName.equals("transcription")){
					BDDAcces bdd = new BDDAcces();
					String query = "" +
							"";
					try {
						bdd.executeQuery(query);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
	}
	
	public void characters(char[] ch,int start, int length)
			throws SAXException{
		String lecture = new String(ch,start,length);
		if(buffer != null) buffer.append(lecture);       
	}
	
	
}
