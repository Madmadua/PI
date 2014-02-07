import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import parser.MadcatHandler;

import BDDAccess.BDDAccess;
import Constants.DataTypeProperty;
import DAEStructure.Dataset;
import DAEStructure.PageElementPropertyValue;
import DAEStructure.PageElementSegment;
import DAEStructure.PageElementToken;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;


public class InsertTest {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SQLException, ParserConfigurationException, SAXException, IOException {
		
		SAXParserFactory fabrique = SAXParserFactory.newInstance();
		SAXParser parseur = fabrique.newSAXParser();

		File fichier = new File("reference_files/in.madcat.xml");
		DefaultHandler gestionnaire = new MadcatHandler();
		parseur.parse(fichier, gestionnaire);
	
	}

}
