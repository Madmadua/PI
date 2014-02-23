package GenerationMadcat;




import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import BDDAccess.BDDAccess;
import DAEStructure.Dataset;
import DAEStructure.PageElementSegment;
import DAEStructure.PageElementToken;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;
import Exception.MyBDDException;
import Exception.MyGeneratorException;

public class MadcatGenerator {
	
	private Dataset dataset;
	
	static Element racine = new Element("madcat");
	
	static org.jdom2.Document document = new Document(racine);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int error = 0;
		if(args == null){
			System.out.println("MadcatGenerator [datasetId] [DIR|DTT|REF] [path|afficher]");
			error = 1;
		}
		if(error == 0 && args[0].equals("help")){
			System.out.println("MadcatGenerator [datasetId] [DIR|DTT|REF] [path|afficher]");
			error = 1;
		}
		if(args.length != 3){
			System.out.println("Mauvais nombre d'arguments, essayez help");
		}else{
			BDDAccess access = new BDDAccess();
			Dataset dataset;
			try {
				dataset = access.getDataset(Integer.valueOf(args[0]));
				MadcatGenerator mg = new MadcatGenerator(dataset);
				if(args[1].equals("DIR")){
						mg.generateInputDIR();
					}else{
						if(args[1].equals("DTT")){
							mg.generateInputDTT();
						}else{
							if(args[1].equals("REF")){
								mg.generateInputRef();
							}else{
								System.out.println("Il faut specifier DIR|DTT|REF");
								error = 1;
							}
						}
					}
					if(args[2].equals("afficher") && error == 0){
						affiche();
					}else{
						if(error == 0){
							System.out.println(args[2]);
							enregistre(args[2]);
						}
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public MadcatGenerator(Dataset dataset) {
		super();
		this.dataset = dataset;
	}
	
	public void generateInputDIR() throws MyGeneratorException{
		generate(true, false, false);
	}
	
	public void generateInputDTT() throws MyGeneratorException{
		generate(false, true, false);
	}
	
	public void generateInputRef() throws MyGeneratorException{
		generate(false, false, true);
	}
	
	private void generate(boolean isDIR, boolean isDTT, boolean isRef) throws MyGeneratorException{
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
						polygon.addContent(polyPoint);
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
							tPoint2.setAttribute(tPoint2Y);
							tPoint2.setAttribute(tPoint2X);
							tokenPolygon.addContent(tPoint2);
							
							Element tPoint3 = new Element("point");
							Attribute tPoint3Y = new Attribute("y", String.valueOf(token.getTopLeftY()));
							Attribute tPoint3X = new Attribute("x", String.valueOf(token.getTopLeftX()+token.getWidth()));
							tPoint3.setAttribute(tPoint3Y);
							tPoint3.setAttribute(tPoint3X);
							tokenPolygon.addContent(tPoint3);
							
							Element tPoint4 = new Element("point");
							Attribute tPoint4Y = new Attribute("y", String.valueOf(token.getTopLeftY()));
							Attribute tPoint4X = new Attribute("x", String.valueOf(token.getTopLeftX()));
							tPoint4.setAttribute(tPoint4Y);
							tPoint4.setAttribute(tPoint4X);
							tokenPolygon.addContent(tPoint4);
						}
					}
				}
			}
		}
		
		Element content = new Element("content");
		doc.addContent(content);
		
		Element section = new Element("section");
		Attribute secType = new Attribute("type", "tokenization");
		Attribute secId = new Attribute("id", "sec0001");
		section.setAttribute(secType);
		section.setAttribute(secId);
		content.addContent(section);
		
		for(PageImage imageC : dataset.getImages()){
			for(PageElementSegment segmentC : imageC.getSegments()){
				Element seg = new Element("segment");
				Attribute segId = new Attribute("id",String.valueOf(segmentC.getId()));
				seg.setAttribute(segId);
				section.addContent(seg);
				
				for(PageElementZone zoneC : segmentC.getZones()){
					Element zone_ref = new Element("zone_ref");
					zone_ref.addContent(String.valueOf(zoneC.getId()));
					seg.addContent(zone_ref);
				}
				
				if(isRef){
					for(PageElementZone zoneC : segmentC.getZones()){
						for(PageElementToken token : zoneC.getMots()){
							Element tokenE = new Element("token");
							Attribute status = new Attribute("status","");
							Attribute ref_id = new Attribute("ref_id", String.valueOf(token.getId()));
							tokenE.setAttribute(status);
							tokenE.setAttribute(ref_id);
							seg.addContent(tokenE);
							
							Element source = new Element("source");
							source.addContent(token.getTranscription().getValue());
							tokenE.addContent(source);
						}
					}
				}
				
				if(isRef || isDTT){
					Element transcription = new Element("transcription");
					transcription.addContent(segmentC.getTranscription().getValue());
					seg.addContent(transcription);
				}
				
				if(isRef){
					Element translation = new Element("translation");
					translation.addContent(segmentC.getTraduction().getValue());
					seg.addContent(translation);
				}
				
			}
		}
	}
	
	public static void affiche(){
		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		}catch(java.io.IOException e){
			e.printStackTrace();
		}
	}
	
	public static void enregistre(String fichier){
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
			point = point.replace(")(", ",");
			point = point.replace(")", "");
			point = point.replace("(", "");
			String[] pointSplit = point.split(",");
			
			if(pointSplit.length == 4){
				points.add(new Point(Integer.valueOf(pointSplit[2]),Integer.valueOf(pointSplit[3])));
			}
			
			if(pointSplit.length != 2 && pointSplit.length != 4){
				throw new MyGeneratorException("[genererPoints] Erreur boundary mal formé : " + boundary);
			}else{
				points.add(new Point(Integer.valueOf(pointSplit[0]),Integer.valueOf(pointSplit[1])));
			}
		}
		return points;
	}
	
	
	

}
