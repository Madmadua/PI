import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import parser.Parser;
import BDDAcces.BDDAcces;
import DAEStructure.Dataset;
import Exception.MyBDDException;
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
		
		Log log = new Log();
		log.logError("coucou");
		log.logWarn("encore coucou");
		log.logInfo("ca fait beaucoup de coucou");
		
	
		
	}

}
