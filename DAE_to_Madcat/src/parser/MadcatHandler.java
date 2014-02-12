package parser;




import java.sql.SQLException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MadcatHandler extends DefaultHandler{
	private StringBuffer buffer;
	private String segmentId;
	private MadcatToDae dae;
	
	private String path;
	
	public MadcatHandler(){
		super();
		dae = new MadcatToDae();
		
		
	}
	
	public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
				
				if(qName.equals("doc")){
					dae.insertDoc(attributes);
					
				}
				if(qName.equals("writer")){
					dae.insertWriter(attributes);
				}
				if(qName.equals("page")){
					dae.insertPage(attributes);
				}
				if(qName.equals("zone")){
					dae.prepareZone(attributes);
				}
				if(qName.equals("point")){
					dae.addPoint(attributes);
				}
				if(qName.equals("token-image")){
					dae.prepareTokenImage(attributes);
				}
				if(qName.equals("segment")){
					dae.insertSegment(attributes);
				}
				if(qName.equals("zone_ref")){
					buffer = new StringBuffer();
				}
				if(qName.equals("transcription")){
					buffer = new StringBuffer();
				}
				if(qName.equals("translation")){
					buffer = new StringBuffer();
				}
				if(qName.equals("token")){
					dae.prepareToken(attributes);
					buffer = new StringBuffer();
				}
				
				
	}
	
	public void endElement(String uri, String localName,
			String qName) throws SAXException{
				if(qName.equals("zone")){
					dae.endZone();
				}
				if(qName.equals("token-image")){
					dae.endTokenImage();
				}
				if(qName.equals("zone_ref")){
					dae.insertZone(buffer.toString());
					buffer = null;
				}
				if(qName.equals("transcription")){
					dae.insertTranscription(buffer.toString());
					buffer = null;
				}
				if(qName.equals("translation")){
					dae.insertTranslation(buffer.toString());
					buffer = null;
				}
				if(qName.equals("token")){
					dae.endToken(buffer.toString());
					buffer = null;
				}
				
	}
	public void characters(char[] ch,int start, int length)
			throws SAXException{
		String lecture = new String(ch,start,length);
		if(buffer != null) buffer.append(lecture);       
	}
	
	public void endDocument() throws SAXException {
		try {
			dae.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
