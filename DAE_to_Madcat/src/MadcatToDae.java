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



public class MadcatToDae {

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
		
		if(args.length!=1)
		{
			System.err.println("Error: you have to specify an input MADCAT file");
			System.exit(-1);
		}

		File fichier = new File(args[0]);
		DefaultHandler gestionnaire = new MadcatHandler();
		parseur.parse(fichier, gestionnaire);
	
	}

}
