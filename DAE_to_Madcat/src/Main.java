import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import parser.Parser;
import BDDAccess.BDDAccess;
import DAEStructure.Dataset;
import DAEStructure.PageElementPropertyValue;
import DAEStructure.PageElementSegment;
import DAEStructure.PageElementToken;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;
import Exception.MyBDDException;
import Exception.MyGeneratorException;
import GenerationMadcat.MadcatGenerator;
import Log.Log;


public class Main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		/*BDDAcces acces = new BDDAcces();
		try {
			Dataset dataset = acces.getDataset(650796, 650796);
		} catch (MyBDDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*Log log = new Log();
		log.logError("coucou");
		log.logWarn("encore coucou");
		log.logInfo("ca fait beaucoup de coucou");*/
		
		PageElementPropertyValue transT = new PageElementPropertyValue(15164735, "transcription", "bla bla bla tok");
		PageElementPropertyValue tradT = new PageElementPropertyValue(15186435, "traduction", "bla bla bla trad tok");
		PageElementToken tok = new PageElementToken(1613516, 5000000, 50, 50, 50, 50, transT, tradT);
		ArrayList<PageElementToken> toks = new ArrayList<PageElementToken>();
		toks.add(tok);
		PageElementZone zoneS11 = new PageElementZone(3846, "(5,3);(8,2);(4,9)", toks, null, null);
		ArrayList<PageElementZone> zones1 = new ArrayList<PageElementZone>();
		zones1.add(zoneS11);
		PageElementPropertyValue transS = new PageElementPropertyValue(1516435, "transcription", "bla bla bla");
		PageElementPropertyValue tradS = new PageElementPropertyValue(1516435, "traduction", "bla bla bla trad");
		PageElementSegment seg1 = new PageElementSegment(0001, "(4,6);(7,2);(4,3);(7,6)", zones1, transS, tradS);
		ArrayList<PageElementSegment> segments = new ArrayList<PageElementSegment>();
		segments.add(seg1);
		PageImage image = new PageImage(84562,150,150,2,"jesuisici", 1080, 512, segments);
		ArrayList<PageImage> images = new ArrayList<PageImage>();
		images.add(image);
		Dataset dataset = new Dataset(58426, "testdataset", "test", images);
		
		MadcatGenerator mg = new MadcatGenerator(dataset);
		try {
			mg.generateInputRef("");
			MadcatGenerator.affiche();
		} catch (MyGeneratorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
