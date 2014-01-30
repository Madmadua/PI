import java.sql.*;


public class Data_collector {
	public static void main(String[] args){
		
		String url = "jdbc:oracle:thin:@localhost:1521:Demo";
		String user = "dae";
		String passwd = "dae";
		Connection conn = null;
		
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
		
		/*try {
			//Création d'un objet Statement
		    Statement state;
			state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
		    ResultSet result = state.executeQuery("SELECT * FROM ALGORITHM");
		    //On récupère les MetaData
		    ResultSetMetaData resultMeta = result.getMetaData();
		         
		    System.out.println("\n**********************************");
		    //On affiche le nom des colonnes
		    for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		        System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
		         
		    	System.out.println("\n**********************************");
		    
		    while(result.next()){
		        for(int i = 1; i <= resultMeta.getColumnCount()-1; i++){
		        	if(result.getObject(i) == null){
		        		System.out.print("\t null \t |");
		        	}else{
		        		System.out.print("\t" + result.getObject(i).toString() + "\t |");
		        	}
		        }
		        	System.out.println("\n---------------------------------");
		    }
		    result.close();
		    state.close();
		      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
