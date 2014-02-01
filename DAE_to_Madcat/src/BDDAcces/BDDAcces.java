package BDDAcces;

import java.sql.*;
import java.util.ArrayList;

import DAEStructure.Dataset;
import DAEStructure.DatasetSection;
import DAEStructure.PageImage;
import Exception.MyBDDException;


public class BDDAcces {
	
	private String url = "jdbc:oracle:thin:@localhost:1521:Demo";
	private String user = "dae";
	private String passwd = "dae";
	private Connection conn = null;
	
	public BDDAcces() {
		super();
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		}catch(Exception e){
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}
		
		try{
			conn = DriverManager.getConnection(url, user, passwd);
		}catch(Exception e){
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		
		if (conn != null) {
			System.out.println("Connexion effective !");
		} else {
			System.out.println("Failed to make connection!");
			return;
		}
	}
	

	public Dataset getDataset(int id, int id_containt) throws MyBDDException {
		// Fonction pour recuperer toutes les info de la base de donné pour construire l'arbre
		// 1 creer dataset
		// 2 requette sql pour recuperer dataset
		// 3 rien
		// 4 requette sql pour recuperer les Page_Image à ajouter à dataset
		// 5 requette sql pour recuperer les Page_Element_zone assocée à dataset_containt
		// 6 boucle pour chaque Page_Element_Zone
		/// 6.1 requette sql pour recuperer les Page_Element_Property_Value de la zone
		/// 6.2 requette sql pour recuperer les Page_Element_Token associe a la zone
		/// 6.3 boucle sur Page_Element_Token
		//// 6.3.1 requette sql pour recuperer les Page_Element_Property_Value associe au token
		
		// 1 creer dataset et dataset containt
		Dataset dataset = new Dataset(); 
		dataset.setContent(new DatasetSection());
		
		// 2 requette sql pour recuperer dataset
		try{
			Statement state;
			state = conn.createStatement();
	        ResultSet result = state.executeQuery("SELECT * FROM DATASET_UNDERLYING d WHERE d.ID = " + String.valueOf(id));
	        ResultSetMetaData resultMeta = result.getMetaData();
	         
	        if(resultMeta.getColumnCount() != 3){
	        	throw new MyBDDException("Erreur BDDAcces/getDataset/2");
	        }
	        
	        if(result.next()){
	        	dataset.setId(id);
	        	dataset.setName(result.getObject(2).toString());
	        	dataset.setPurpose(result.getObject(3).toString());
	        }else{
	        	throw new MyBDDException("Erreur BDDAcces/getDataset/2 pas de dataset avec l'id " + id);
	        }
	        
	        if(result.next()){
	        	throw new MyBDDException("Erreur BDDAcces/getDataset/2 plusieurs dataset avec l'id " + id);
	        }
	    
	        result.close();
	        state.close();
	        
		}catch (Exception e) {
			if(e instanceof MyBDDException){
				throw new MyBDDException(((MyBDDException) e).getDescription());
			}else{
				e.printStackTrace();
			}
		}
		
		// 3 requette sql pour recuperer dataset_containt
		
				
		// 4 requette sql pour recuperer les Page_Image à ajouter à dataset
		try{
			Statement state;
			state = conn.createStatement();
		    ResultSet result = state.executeQuery(
		   		"SELECT PAGE_IMAGE_UNDERLYING.ID, " +
		   		"PAGE_IMAGE_UNDERLYING.VDPI, " +
		   		"PAGE_IMAGE_UNDERLYING.HDPI, " +
		   		"PAGE_IMAGE_UNDERLYING.SKEW, " +
		   		"PAGE_IMAGE_UNDERLYING.PATH, " +
		   		"PAGE_IMAGE_UNDERLYING.WIDTH, " +
		   		"PAGE_IMAGE_UNDERLYING.HEIGHT " +
		   		"FROM INCLUDES_PAGE_IMAGE_UNDERLYING, PAGE_IMAGE_UNDERLYING " +
		   		"WHERE INCLUDES_PAGE_IMAGE_UNDERLYING.DATASET_ID = " + String.valueOf(id) +
		   		" AND INCLUDES_PAGE_IMAGE_UNDERLYING.PAGE_IMAGE_ID = PAGE_IMAGE_UNDERLYING.ID"
		   		);
			        
		    ResultSetMetaData resultMeta = result.getMetaData();
			        
			ArrayList<PageImage> images = new ArrayList<PageImage>();
			        
			while(result.next()){
				    	
			   	if(resultMeta.getColumnCount() != 7){
			   		throw new MyBDDException("Erreur BDDAcces/getDataset/4 nombre de collones.");
	        	}
			        	
			   	int pageId = Integer.valueOf(result.getObject(1).toString());
			    int vdpi = 0;
			    int hdpi = 0;
			   	int skew = 0;
			  	String path = result.getObject(5).toString();
			   	int width = Integer.valueOf(result.getObject(6).toString());
		      	int height = Integer.valueOf(result.getObject(7).toString());
			        	
		      	if(result.getObject(2).toString() != "null"){
			        		
		       	}
			        	
		      	image = new
			        	
			        	
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++){
				        		
		        	if(result.getObject(i) == null){
				    	System.out.print("\t null \t |");
				    }else{
				    	System.out.print("\t" + result.getObject(i).toString() + "\t |");
			    	}
			    }
				        	
			}
			         
			        

			        

			    
			        result.close();
			        state.close();
			        
				}catch (Exception e) {
					if(e instanceof MyBDDException){
						throw new MyBDDException(((MyBDDException) e).getDescription());
					}else{
						e.printStackTrace();
					}
				}
		
		return dataset;
		
	}
	
	public ResultSet executeQuery(String query) throws SQLException{
		Statement state;
		state = conn.createStatement();
        ResultSet result = state.executeQuery(query);
        
		
	return result;
		
		
	}


	public void close() {
		
	}
	
	

}
