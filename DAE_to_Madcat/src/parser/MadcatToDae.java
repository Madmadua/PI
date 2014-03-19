package parser;

import java.awt.Point;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;

import BDDAccess.BDDAccess;
import Constants.DataTypeProperty;
import DAEStructure.Dataset;
import DAEStructure.PageElementPropertyValue;
import DAEStructure.PageElementSegment;
import DAEStructure.PageElementToken;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;

public class MadcatToDae {
	private BDDAccess bdd;
	private Dataset dataset;
	private PageImage image;
	private HashMap zones;
	private PageElementZone zone;
	private boolean inToken = false;
	private String boundary = "";
	private HashMap tokensImages;
	private PageElementToken token = new PageElementToken();
	private ArrayList<Point> tokenPoints;
	private ArrayList<Point> zonePoints;
	private ArrayList<Point> segmentPoints;
	private PageElementSegment segment = new PageElementSegment();
	private static String path = "/dae/database/openhart/image/";
	private String zoneName;
	
	private String src;
	private String ref;
	
	public MadcatToDae(){
		bdd = new BDDAccess();
		zones = new HashMap();
		tokensImages = new HashMap();
		zonePoints = new ArrayList<Point>();
		segmentPoints = new ArrayList<Point>();
	}
	
	public void insertDoc(Attributes attributes){
		String id = attributes.getValue("id");
		src = path + attributes.getValue("src");
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
			System.out.println(dataset.getId());
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
		//int colordepth = Integer.parseInt(attributes.getValue("colordepth"));
		int width = Integer.parseInt(attributes.getValue("width"));
		int height = Integer.parseInt(attributes.getValue("height"));
		
		image = new PageImage();
		image.setName("Dataset " + dataset.getName() + " " + id);
		image.setHdpi(dpi);
		image.setVdpi(dpi);
		image.setWidth(width);
		image.setHeight(height);
		image.setPath(src);
		
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
	
	public void prepareZone(Attributes attributes){
		zoneName = attributes.getValue("id");
		String type = attributes.getValue("type");
		
		zone = new PageElementZone();
		zone.setName("Dataset " + dataset.getName() + " " + zoneName);
		zone.setType(type);
		
	}
	
	public void addPoint(Attributes attributes){
		int x = Integer.parseInt(attributes.getValue("x"));
		int y = Integer.parseInt(attributes.getValue("y"));
		
		if(!inToken){
			boundary += "(" + x + "," + y + ");";
			Point point = new Point();
			point.setLocation(x,y);
			zonePoints.add(point);
		}
		if(inToken){
			Point point = new Point();
			point.setLocation(x, y);
			tokenPoints.add(point);
			
		}
	}
	
	public void endZone(){
		
		// remove last ";" in boundary
		int index = boundary.lastIndexOf(';');
		boundary = boundary.substring(0, index);
		
		zone.setBoundary(boundary);
		zone.setPoints(zonePoints);
		zones.put(zoneName, zone);
		zonePoints = new ArrayList<Point>();
		
		
	}
	
	public void prepareTokenImage(Attributes attributes){
		inToken = true;
		String id = attributes.getValue("id");
		tokenPoints = new ArrayList<Point>();
		
		
		token = new PageElementToken();
		token.setName("Dataset " + dataset.getName() + " " + id);
	}
	
	public void endTokenImage(){
		Rectangle rect = buildRectangle(tokenPoints);
		
		token.setHeight(rect.height);
		token.setWidth(rect.width);
		token.setTopLeftX(rect.x);
		token.setTopLeftY(rect.y);
		
		token.setParent(zone);
		
		tokensImages.put(token.getName(), token);
		
		inToken = false;
		
	}
	
	public void insertSegment(Attributes attributes){
		String id = attributes.getValue("id");
		
		segment = new PageElementSegment();
		segmentPoints = new ArrayList<Point>();
		segment.setName("Dataset " + dataset.getName() + " " + id);
		
		try {
			segment.insert(bdd,image);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertZone(String name){
		zone = (PageElementZone) zones.get(name);
		PageElementPropertyValue type = new PageElementPropertyValue();
		
		
		type.setName(zone.getName() + " type");
		type.setValue(zone.getType());
		type.setValueTypeId(DataTypeProperty.TYPE);
		
		PageElementPropertyValue boundaryPV = new PageElementPropertyValue();
		boundaryPV.setName(zone.getName() + " boundary");
		boundaryPV.setValue(zone.getBoundary());
		boundaryPV.setValueTypeId(DataTypeProperty.BOUNDARY);
		
		ArrayList<Point> points = zone.getPoints();
		Rectangle rect = buildRectangle(points);
		segmentPoints.addAll(points);
		
		zone.setWidth(rect.width);
		zone.setHeight(rect.height);
		zone.setTopLeftX(rect.x);
		zone.setTopLeftY(rect.y);
			
			
		try {
			zone.insert(bdd, segment,image);
			type.insertWithPageElement(bdd, zone.getId());
			boundaryPV.insertWithPageElement(bdd, zone.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertTranscription(String phrase){
		PageElementPropertyValue transcription = new PageElementPropertyValue();
		transcription.setName(segment.getName() + " transcription");
		transcription.setValue(phrase);
		transcription.setValueTypeId(DataTypeProperty.TRANSCRIPTION); 
		try {
			transcription.insertWithPageElement(bdd, segment.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertTranslation(String phrase){
		PageElementPropertyValue translation = new PageElementPropertyValue();
		translation.setName(segment.getName() + " translation");
		translation.setValue(phrase);
		translation.setValueTypeId(DataTypeProperty.TRANSLATION);
		try {
			translation.insertWithPageElement(bdd, segment.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void prepareToken(Attributes attributes){
		ref = attributes.getValue("ref_id");
		token = (PageElementToken) tokensImages.get("Dataset " + dataset.getName() + " " + ref);
		
	}
	public void endToken(String source){
		PageElementPropertyValue sourcePV = new PageElementPropertyValue();
		zone = token.getParent();
		
		sourcePV.setName(dataset.getName() + ref);
		sourcePV.setValue(source);
		sourcePV.setValueTypeId(DataTypeProperty.SOURCE);
		
		try {
			zone.insert(bdd, segment,image);
			token.insert(bdd, zone,image);
			sourcePV.insertWithPageElement(bdd, token.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void close() throws SQLException{
		bdd.close();
	}
	
	public Rectangle buildRectangle(ArrayList<Point> points){
		Rectangle rect = new Rectangle();
		
		Point topLeft = points.get(0);
		Point botRight = points.get(0);
		
		for(int i=1;i<points.size();i++){
			Point p = points.get(i);
			if(p.x<topLeft.x){
				Point np = new Point(p.x,topLeft.y);
				topLeft = np;
			}
			if(p.y<topLeft.y){
				Point np = new Point(topLeft.x,p.y);
			}
			if(p.x>botRight.x){
				Point np = new Point(p.x,botRight.y);
				botRight = np;
			}
			if(p.y>botRight.y){
				Point np = new Point(botRight.x,p.y);
				botRight = np;
			}
			
		}
		int height = botRight.y - topLeft.y;
		int width = botRight.x - topLeft.x;
		
		rect.setLocation(topLeft.x, topLeft.y);
		rect.setSize(width, height);
		
		return rect;
	}
	
	public void updateSegment(){
		Rectangle rect = buildRectangle(segmentPoints);
		
		segment.setTopLeftX(rect.x);
		segment.setTopLeftY(rect.y);
		segment.setHeight(rect.height);
		segment.setWidth(rect.width);
		
		try {
			segment.update(bdd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
