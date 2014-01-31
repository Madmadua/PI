package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Parser {
	private File madcatInput;
	private File daeOutput;
	
	public Parser(String madcatInputPath, String daeOutputPath){
		madcatInput = new File(madcatInputPath);
		daeOutput = new File(daeOutputPath);
		
	}
	
	public void parse() throws ParserConfigurationException, SAXException, IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		DefaultHandler dh = new MadcatHandler(madcatInput);
		parser.parse(madcatInput, dh);
		
	}
}
