package metricFileMaker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BDDAccess.BDDAccess;

public class MetricFileMaker {

	/**
	 * @param args
	 */
	
	private Statement statement;
	private String datasetName;
	private String ID;
	
	private ResultSet resultIDTranslations;
	private ResultSet resultIDTranscriptions;

	private ResultSet resultTranslations;
	private ResultSet resultTranscriptions;
	
	private ArrayList<Integer> idsTranslations;
	private ArrayList<Integer> idsTranscriptions;

	private ArrayList<String> translations;
	private ArrayList<String> transcriptions;
	
	public MetricFileMaker(String ID)
	{
		this.ID = ID;
		idsTranslations = new ArrayList<Integer>();
		idsTranscriptions= new ArrayList<Integer>();
		
		translations = new ArrayList<String>();
		transcriptions = new ArrayList<String>();
	}
	
	public void makeRequests()
	{
		BDDAccess bdd = new BDDAccess();
		try {
			statement = bdd.getConn().createStatement();
			ResultSet set = statement.executeQuery("select NAME from DATASET_UNDERLYING where ID="+ID);
			set.next();
			datasetName=set.getString(1);
			resultIDTranslations = statement.executeQuery("select ID from data_item_underlying where description like 'Dataset "+datasetName+"%translation'");
			while(resultIDTranslations.next())
			{	
				idsTranslations.add(resultIDTranslations.getInt(1));
			}
			
			resultIDTranscriptions = statement.executeQuery("select ID from data_item_underlying where description like 'Dataset "+datasetName+"%transcription'");
			while(resultIDTranscriptions.next())
			{	
				idsTranscriptions.add(resultIDTranscriptions.getInt(1));
			}
			for(int i=0;i<idsTranslations.size();++i)
			{
				resultTranslations = statement.executeQuery("select value from page_element_p_val_underlying where id="+idsTranslations.get(i));
				resultTranslations.next();
				translations.add(clobToString(resultTranslations.getClob(1)));
			}
			for(int i=0;i<idsTranscriptions.size();++i)
			{
				resultTranscriptions = statement.executeQuery("select value from page_element_p_val_underlying where id="+idsTranscriptions.get(i));
				resultTranscriptions.next();
				transcriptions.add(clobToString(resultTranscriptions.getClob(1)));
			}
			System.out.println(translations.toString());
			System.out.println(transcriptions.toString());
			bdd.getConn().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	public void makeHypSGML(String dir)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(dir+"/hyp.sgml", "UTF-8");
			writer.println("<tstset setid=\"MADCAT09\" srclang=\"Arabic\" trglang=\"English\">");
			writer.println("<doc docid=\""+datasetName+"\" sysid=\"MADCAT\" genre=\"newswire\">");
			for(int i=0;i<translations.size();++i)
			{
				writer.println("<seg id=\""+i+"\">"+translations.get(i)+"</seg>");
			}
			writer.println("</doc>");
			writer.println("</tstset>");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeRefSGML(String dir)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(dir+"/ref.sgml", "UTF-8");
			writer.println("<refset setid=\"MADCAT09\" srclang=\"Arabic\" trglang=\"English\">");
			writer.println("<doc docid=\""+datasetName+"\" sysid=\"MADCAT\" genre=\"newswire\">");
			for(int i=0;i<translations.size();++i)
			{
				writer.println("<seg id=\""+i+"\">"+translations.get(i)+"</seg>");
			}
			writer.println("</doc>");
			writer.println("</refset>");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeHypTRN(String dir)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(dir+"/hyp.trn", "UTF-8");
			for(int i=0;i<translations.size();++i)
			{
				writer.println(translations.get(i)+"("+datasetName+'_'+(i+1)+')');
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeRefTRN(String dir)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(dir+"/ref.trn", "UTF-8");
			for(int i=0;i<translations.size();++i)
			{
				writer.println(translations.get(i)+"("+datasetName+'_'+(i+1)+')');
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeSRC(String dir)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(dir+"/hyp.src", "UTF-8");
			writer.println("<srcset setid=\"MADCAT09\" srclang=\"Arabic\" trglang=\"English\">");
			writer.println("<doc docid=\""+datasetName+"\" sysid=\"MADCAT\" genre=\"newswire\">");
			for(int i=0;i<translations.size();++i)
			{
				writer.println("<seg id=\""+i+"\">"+transcriptions.get(i)+"</seg>");
			}
			writer.println("</doc>");
			writer.println("</srcset>");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String clobToString(Clob data) {
	    StringBuilder sb = new StringBuilder();
	    try {
	        Reader reader = data.getCharacterStream();
	        BufferedReader br = new BufferedReader(reader);

	        String line;
	        while(null != (line = br.readLine())) {
	            sb.append(line);
	        }
	        br.close();
	    } catch (SQLException e) {
	        // handle this exception
	    } catch (IOException e) {
	        // handle this exception
	    }
	    return sb.toString();
	}

}
