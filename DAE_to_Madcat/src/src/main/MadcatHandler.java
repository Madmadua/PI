package main;

import java.io.File;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import daeBeans.Boundary;
import daeBeans.Dataset;
import daeBeans.PageElement;
import daeBeans.PageImage;
import daeBeans.Property;
import daeBeans.RectangleBoundary;
import daeBeans.Type;
import daeBeans.Value;

public class MadcatHandler extends DefaultHandler{
	private File daeOutput;
	private Dataset doc;
	private Dataset content;
	private PageImage image;
	private Type type;
	private String contributor;
	private PageElement zone;
	private PageElement tokenImage;
	private Boundary boundary;
	private RectangleBoundary rectangle;
	private ArrayList<String> boundaryValues;
	
	
	public MadcatHandler(File daeOutput){
		super();
		this.daeOutput = daeOutput;
		this.doc = new Dataset();
		
	}
	
	public void startElement(String uri, String localName,
			String qName, Attributes attributes) throws SAXException{
				if(qName.equals("doc")){
					String id = attributes.getValue("id");
					String src = attributes.getValue("src");
					String typeName= attributes.getValue("type");
					
					doc.setId(id);
					doc.setPath(src);
					type = new Type();
					type.setId("type");
					type.setName(typeName);
				}
				
				if(qName.equals("writer")){
					contributor = attributes.getValue("id");	
				}
				if(qName.equals("page")){
					image = new PageImage();
					
					String id = attributes.getValue("id");
					int dpi = Integer.parseInt(attributes.getValue("dpi"));
					String colordepth = attributes.getValue("colordepth");
					int width = Integer.parseInt(attributes.getValue("width"));
					int height = Integer.parseInt(attributes.getValue("height"));
					
					Type colordepthType = new Type();
					colordepthType.setId("colordepth");
					colordepthType.setName(colordepth);
					
					ArrayList<Type> typeList = new ArrayList<Type>();
					typeList.add(type);
					typeList.add(colordepthType);
					
					
					image.setId(id);
					image.setHdpi(dpi);
					image.setHeight(height);
					image.setWidth(width);
					
					}
				if(qName.equals("zone")){
					String id = attributes.getValue("id");
					String type = attributes.getValue("type");
					
					zone = new PageElement();
					zone.setType(type);
					zone.setId(id);
				}
				if(qName.equals("polygon")){
					boundary = new Boundary();
					boundaryValues = new ArrayList<String>();
					
				}
				if(qName.equals("point")){
					String x = attributes.getValue("x");
					String y = attributes.getValue("y");
					
					boundaryValues.add("(" + x + ";" +y + ")");
				}
				if(qName.equals("token-image")){
					String id = attributes.getValue("id");
					
					tokenImage = new PageElement();
					tokenImage.setId(id);
				}
				if(qName.equals("content")){
					String docId = doc.getId();
					String contentId = docId + "_Content";
					doc.setAssociateId(contentId);
					
					content = new Dataset();
					content.setAssociateId(docId);
					
				}
				
				
	}
	
	
}
