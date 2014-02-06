package GenerationMadcat;




import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import DAEStructure.Dataset;
import DAEStructure.PageElementSegment;
import DAEStructure.PageElementToken;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;
import Exception.MyGeneratorException;

public class MadcatGenerator {
	
	private Dataset dataset;
	
	static Element racine = new Element("madcat");
	
	static org.jdom2.Document document = new Document(racine);

	public MadcatGenerator(Dataset dataset) {
		super();
		this.dataset = dataset;
	}
	
	public void generateInputDIR(String path) throws MyGeneratorException{
		generate(path, true, false, false);
	}
	
	public void generateInputDTT(String path) throws MyGeneratorException{
		generate(path, false, true, false);
	}
	
	public void generateInputRef(String path) throws MyGeneratorException{
		generate(path, false, false, true);
	}
	
	private void generate(String path, boolean isDIR, boolean isDTT, boolean isRef) throws MyGeneratorException{
		Attribute version = new Attribute("version", "2008.1");
		racine.setAttribute(version);
		
		Element doc = new Element("doc");
		Attribute type = new Attribute("type", "newswire");
		Attribute nbpages = new Attribute("nbpages", String.valueOf(dataset.getImages().size()));
		Attribute src = new Attribute("src", dataset.getImages().get(0).getPath());
		Attribute id = new Attribute("id", String.valueOf(dataset.getId()));
		doc.setAttribute(type);
		doc.setAttribute(nbpages);
		doc.setAttribute(src);
		doc.setAttribute(id);
		racine.addContent(doc);
		
		Element writer = new Element("writer");
		Attribute wId = new Attribute("id", "qsfgsg"); // a modifier ****************************************
		writer.setAttribute(wId);
		doc.addContent(writer);
		
		Element image = new Element("image");
		doc.addContent(image);
		
		for(PageImage pageImage : dataset.getImages()){
			
			Element page = new Element("page");
			Attribute pId = new Attribute("id", String.valueOf(pageImage.getId()));
			Attribute height = new Attribute("height", String.valueOf(pageImage.getHeight()));
			Attribute width = new Attribute("width", String.valueOf(pageImage.getWidth()));
			Attribute colordepth = new Attribute("colordepth", String.valueOf(pageImage.getColordepth()));
			Attribute dpi = new Attribute("dpi", String.valueOf(pageImage.getHdpi())); //normalement hdpi = vdpi
			page.setAttribute(pId);
			page.setAttribute(height);
			page.setAttribute(width);
			page.setAttribute(colordepth);
			page.setAttribute(dpi);
			image.addContent(page);
			
			for(PageElementSegment segment : pageImage.getSegments()){
				for(PageElementZone zone : segment.getZones()){
					Element eZone = new Element("zone");
					Attribute zoneType = new Attribute("type", "line"); //peut etre à modifier par le suite
					Attribute zoneId = new Attribute("id", String.valueOf(zone.getId()));
					eZone.setAttribute(zoneId);
					eZone.setAttribute(zoneType);
					page.addContent(eZone);
					
					ArrayList<Point> points = this.genererPoints(zone.getBoundary());
					
					Element polygon = new Element("polygon");
					eZone.addContent(polygon);
					for(Point point : points){
						Element polyPoint = new Element("point");
						Attribute y = new Attribute("y", String.valueOf(point.getY()));
						Attribute x = new Attribute("x", String.valueOf(point.getX()));
						polyPoint.setAttribute(y);
						polyPoint.setAttribute(x);
					}
					
					if(isRef){
						for(PageElementToken token : zone.getMots()){
							Element tokenImage = new Element("token-image");
							Attribute tIId = new Attribute("id", String.valueOf(token.getId()));
							tokenImage.setAttribute(tIId);
							eZone.addContent(tokenImage);
							
							Element tokenPolygon = new Element("polygon");
							tokenImage.addContent(tokenPolygon);
							
							Element tPointOne = new Element("point");
							Attribute tPointOneY = new Attribute("y", String.valueOf(token.getTopLeftY()-token.getHeight()));
							Attribute tPointOneX = new Attribute("x", String.valueOf(token.getTopLeftX()));
							tPointOne.setAttribute(tPointOneY);
							tPointOne.setAttribute(tPointOneX);
							tokenPolygon.addContent(tPointOne);
							
							Element tPoint2 = new Element("point");
							Attribute tPoint2Y = new Attribute("y", String.valueOf(token.getTopLeftY()-token.getHeight()));
							Attribute tPoint2X = new Attribute("x", String.valueOf(token.getTopLeftX()+token.getWidth()));
							tPointOne.setAttribute(tPoint2Y);
							tPointOne.setAttribute(tPoint2X);
							tokenPolygon.addContent(tPoint2);
							
							Element tPoint3 = new Element("point");
							Attribute tPoint3Y = new Attribute("y", String.valueOf(token.getTopLeftY()));
							Attribute tPoint3X = new Attribute("x", String.valueOf(token.getTopLeftX()+token.getWidth()));
							tPointOne.setAttribute(tPoint3Y);
							tPointOne.setAttribute(tPoint3X);
							tokenPolygon.addContent(tPoint3);
							
							Element tPoint4 = new Element("point");
							Attribute tPoint4Y = new Attribute("y", String.valueOf(token.getTopLeftY()));
							Attribute tPoint4X = new Attribute("x", String.valueOf(token.getTopLeftX()));
							tPointOne.setAttribute(tPoint4Y);
							tPointOne.setAttribute(tPoint4X);
							tokenPolygon.addContent(tPoint4);
						}
					}
				}
			}
		}
	}
	
	static void affiche(){
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		}catch(java.io.IOException e){
			e.printStackTrace();
		}
	}
	
	static void enregistre(String fichier){
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(fichier));
		}catch(java.io.IOException e){
			e.printStackTrace();
		}
	}

	public ArrayList<Point> genererPoints(String boundary) throws MyGeneratorException {
		ArrayList<Point> points = new ArrayList<Point>();
		//(1,2);(3,5)
		String[] boundarySplit = boundary.split(";");
		for(String point : boundarySplit){
			point = point.replace(")", "");
			point = point.replace("(", "");
			String[] pointSplit = point.split(",");
			
			if(pointSplit.length != 2){
				throw new MyGeneratorException("[genererPoints] Erreur boundary mal formé : " + boundary);
			}else{
				points.add(new Point(Integer.valueOf(pointSplit[0]),Integer.valueOf(pointSplit[1])));
			}
		}
		return null;
	}
	

}
