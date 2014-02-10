package BDDAccess;

import java.sql.*;
import java.util.ArrayList;

import Constants.DataTypeProperty;
import DAEStructure.Dataset;
import DAEStructure.PageElementPropertyValue;
import DAEStructure.PageElementSegment;
import DAEStructure.PageElementToken;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;
import Exception.MyBDDException;
import Log.Log;


public class BDDAccess {

	private String url = "jdbc:oracle:thin:@localhost:1521:Demo";
	private String user = "dae";
	private String passwd = "dae";
	private Connection conn = null;




	public BDDAccess() {
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
		// 3 requette sql pour recuperer les Page_Image à ajouter à dataset
		// 4 boucle sur les Page_Image
		/// 4.1 recuperer les PE segments 
		/// 4.2 boucle sur les PE segments
		//// 4.2.1 recuperer traduction et transcription du PE segment
		//// 4.2.2 recuperer les PE zones
		//// 4.2.3 boucle sur les PE zones
		///// 4.2.3.1 recuperer traduction et transcription du PE zone
		///// 4.2.3.2 recuperer les PE tokens
		///// 4.2.3.3 boucle sur les PE tokens
		////// 4.2.3.3.1 recuperer tradaction et transcription du PE token

		Log log = new Log();

		// 1 creer dataset
		Dataset dataset = new Dataset();


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

				log.logInfo("Dataset importé (ID:" + id + ", name:" + result.getObject(2).toString() + ", purpose:" + result.getObject(2).toString() + ")");
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

		// 3 requette sql pour recuperer les Page_Image à ajouter à dataset
		ArrayList<PageImage> images = new ArrayList<PageImage>();
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

			while(result.next()){

				if(resultMeta.getColumnCount() != 7){
					throw new MyBDDException("Erreur BDDAcces/getDataset/3 nombre de collones.");
				}

				int pageId = Integer.valueOf(result.getObject(1).toString());
				int vdpi = 0;
				int hdpi = 0;
				int skew = 0;
				String path = result.getObject(5).toString();
				int width = Integer.valueOf(result.getObject(6).toString());
				int height = Integer.valueOf(result.getObject(7).toString());
				ArrayList<PageElementSegment> segments = new ArrayList<PageElementSegment>();

				if(result.getObject(2).toString() != "null"){
					vdpi = Integer.valueOf(result.getObject(2).toString());
				}
				if(result.getObject(3).toString() != "null"){
					hdpi = Integer.valueOf(result.getObject(3).toString());
				}
				if(result.getObject(4).toString() != "null"){
					skew = Integer.valueOf(result.getObject(4).toString());
				}

				PageImage image = new PageImage(pageId, vdpi, hdpi, skew, path, width, height, segments);
				log.logInfo("PageImage importée (ID:" + 
						pageId + ", vdpi:" +
						vdpi + ", hdpi:" +
						hdpi + ", skew:" +
						skew + ", path:" +
						path + ", width:" +
						width + ", height:" +
						height + ")");
				images.add(image);
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

		// 4 boucle sur les Page_Image
		for(PageImage image: images){

			/// 4.1 recuperer les PE segments
			try{
				Statement state;
				state = conn.createStatement();
				ResultSet result = state.executeQuery(
						"SELECT PAGE_ELEMENT_UNDERLYING.ID, " +
								"PAGE_ELEMENT_UNDERLYING.BOUNDARY, " +
								"FROM CONTAINS_PAGE_ELEMENT, PAGE_ELEMENT_UNDERLYING " +
								"WHERE CONTAINS_PAGE_ELEMENT.PAGE_IMAGE_ID = " + String.valueOf(image.getId()) +
								" AND CONTAINS_PAGE_ELEMENT.PAGE_ELEMENT_ID = PAGE_ELEMENT_UNDERLYING.ID"
						);

				ResultSetMetaData resultMeta = result.getMetaData();

				while(result.next()){

					if(resultMeta.getColumnCount() != 2){
						throw new MyBDDException("Erreur BDDAcces/getDataset/4.1 nombre de collones.");
					}

					int elementId = Integer.valueOf(result.getObject(1).toString());
					String boundary = result.getObject(2).toString();
					ArrayList<PageElementZone> zones = new ArrayList<PageElementZone>();
					PageElementPropertyValue transcription = null;
					PageElementPropertyValue traduction = null;

					PageElementSegment segment = new PageElementSegment(elementId, boundary, zones, transcription, traduction);
					log.logInfo("PageElementSegment associé à PageImage:" + image.getId() + " importé (ID:" +
							elementId + ", boudary:" +
							boundary + ")");
					image.getSegments().add(segment);
				}

			}catch (Exception e) {
				if(e instanceof MyBDDException){
					throw new MyBDDException(((MyBDDException) e).getDescription());
				}else{
					e.printStackTrace();
				}
			}

			/// 4.2 boucle sur les PE segments
			for(PageElementSegment segment: image.getSegments()){

				//// 4.2.1 recuperer traduction et transcription du PE segment
				try{
					Statement state;
					state = conn.createStatement();
					ResultSet result = state.executeQuery(
							"SELECT PAGE_ELEMENT_P_VAL_UNDERLYING.ID, " +
									"PAGE_ELEMENT_P_VAL_UNDERLYING.VALUE_TYPE, " +
									"PAGE_ELEMENT_P_VAL_UNDERLYING.VALUE, " +
									"FROM PAGE_ELEMENT_P_VAL_UNDERLYING, HAS_VALUE " +
									"WHERE HAS_VALUE.PAGE_ELEMENT_ID = " + String.valueOf(segment.getId()) +
									" AND CPAGE_ELEMENT_P_VAL_UNDERLYING.ID = HAS_VALUE.PAGE_ELEMENT_PROPERTY_VALUE_ID"
							);

					ResultSetMetaData resultMeta = result.getMetaData();

					while(result.next()){

						if(resultMeta.getColumnCount() != 3){
							throw new MyBDDException("Erreur BDDAcces/getDataset/4.2.1 nombre de collones.");
						}

						int pvId = Integer.valueOf(result.getObject(1).toString());
						int valueType = Integer.valueOf(result.getObject(2).toString());
						String value = result.getObject(1).toString();

						if(valueType == DataTypeProperty.TRANSLATION){
							segment.setTraduction(new PageElementPropertyValue(pvId, "traduction", value));
							log.logInfo("Traduction du PESegment:" + segment.getId() + " importé (ID:" +
									pvId + ", value:" + value +")");
						}
						if(valueType == DataTypeProperty.TRANSCRIPTION){
							segment.setTraduction(new PageElementPropertyValue(pvId, "transcription", value));
							log.logInfo("Transcription du PESegment:" + segment.getId() + " importé (ID:" +
									pvId + ", value:" + value +")");
						}
					}

				}catch (Exception e) {
					if(e instanceof MyBDDException){
						throw new MyBDDException(((MyBDDException) e).getDescription());
					}else{
						e.printStackTrace();
					}
				}

				//// 4.2.2 recuperer les PE zones
				try{
					Statement state;
					state = conn.createStatement();
					ResultSet result = state.executeQuery(
							"SELECT PAGE_ELEMENT_UNDERLYING.ID, " +
									"PAGE_ELEMENT_UNDERLYING.BOUNDARY, " +
									"FROM ASSOCIATE_PAGE_ELEMENT, PAGE_ELEMENT_UNDERLYING " +
									"WHERE ASSOCIATE_PAGE_ELEMENT.PAGE_ELEMENT_ID = " + String.valueOf(segment.getId()) +
									" AND ASSOCIATE_PAGE_ELEMENT.ASSOCIATE_PAGE_ELEMENT_ID = PAGE_ELEMENT_UNDERLYING.ID"
							);

					ResultSetMetaData resultMeta = result.getMetaData();

					while(result.next()){

						if(resultMeta.getColumnCount() != 2){
							throw new MyBDDException("Erreur BDDAcces/getDataset/4.2.2 nombre de collones.");
						}

						int elementId = Integer.valueOf(result.getObject(1).toString());
						String boundary = result.getObject(2).toString();
						ArrayList<PageElementToken> tokens = new ArrayList<PageElementToken>();
						PageElementPropertyValue transcription = null;
						PageElementPropertyValue traduction = null;

						PageElementZone zone = new PageElementZone(elementId, boundary, tokens, transcription, traduction);
						log.logInfo("PageElementZone associé à PageElementSegment:" + segment.getId() + " importé (ID:" +
								elementId + ", boundary:" +
								boundary + ")");
						segment.getZones().add(zone);
					}

				}catch (Exception e) {
					if(e instanceof MyBDDException){
						throw new MyBDDException(((MyBDDException) e).getDescription());
					}else{
						e.printStackTrace();
					}
				}

				//// 4.2.3 boucle sur les PE zones
				for(PageElementZone zone : segment.getZones()){

					///// 4.2.3.1 recuperer traduction et transcription du PE zone
					try{
						Statement state;
						state = conn.createStatement();
						ResultSet result = state.executeQuery(
								"SELECT PAGE_ELEMENT_P_VAL_UNDERLYING.ID, " +
										"PAGE_ELEMENT_P_VAL_UNDERLYING.VALUE_TYPE, " +
										"PAGE_ELEMENT_P_VAL_UNDERLYING.VALUE, " +
										"FROM PAGE_ELEMENT_P_VAL_UNDERLYING, HAS_VALUE " +
										"WHERE HAS_VALUE.PAGE_ELEMENT_ID = " + String.valueOf(zone.getId()) +
										" AND CPAGE_ELEMENT_P_VAL_UNDERLYING.ID = HAS_VALUE.PAGE_ELEMENT_PROPERTY_VALUE_ID"
								);

						ResultSetMetaData resultMeta = result.getMetaData();

						while(result.next()){

							if(resultMeta.getColumnCount() != 3){
								throw new MyBDDException("Erreur BDDAcces/getDataset/4.2.3.1 nombre de collones.");
							}

							int pvId = Integer.valueOf(result.getObject(1).toString());
							int valueType = Integer.valueOf(result.getObject(2).toString());
							String value = result.getObject(1).toString();

							if(valueType == DataTypeProperty.TRANSLATION){
								zone.setTraduction(new PageElementPropertyValue(pvId, "traduction", value));
								log.logInfo("Traduction du PEZone:" + zone.getId() + " importé (ID:" +
										pvId + ", value:" + value +")");
							}
							if(valueType == DataTypeProperty.TRANSCRIPTION){
								zone.setTraduction(new PageElementPropertyValue(pvId, "transcription", value));
								log.logInfo("Transcription du PEZone:" + zone.getId() + " importé (ID:" +
										pvId + ", value:" + value +")");
							}
						}

					}catch (Exception e) {
						if(e instanceof MyBDDException){
							throw new MyBDDException(((MyBDDException) e).getDescription());
						}else{
							e.printStackTrace();
						}
					}

					///// 4.2.3.2 recuperer les PE tokens
					try{
						Statement state;
						state = conn.createStatement();
						ResultSet result = state.executeQuery(
								"SELECT PAGE_ELEMENT_UNDERLYING.ID, " +
										"PAGE_ELEMENT_UNDERLYING.NUMBER_OF_PIXELS, " +
										"PAGE_ELEMENT_UNDERLYING.TOPLEFTX, " +	
										"PAGE_ELEMENT_UNDERLYING.TOPLEFTY, " +
										"PAGE_ELEMENT_UNDERLYING.WIDTH, " +	
										"PAGE_ELEMENT_UNDERLYING.HEIGHT, " +	
										"FROM ASSOCIATE_PAGE_ELEMENT, PAGE_ELEMENT_UNDERLYING " +
										"WHERE ASSOCIATE_PAGE_ELEMENT.PAGE_ELEMENT_ID = " + String.valueOf(zone.getId()) +
										" AND ASSOCIATE_PAGE_ELEMENT.ASSOCIATE_PAGE_ELEMENT_ID = PAGE_ELEMENT_UNDERLYING.ID"
								);

						ResultSetMetaData resultMeta = result.getMetaData();

						while(result.next()){

							if(resultMeta.getColumnCount() != 6){
								throw new MyBDDException("Erreur BDDAcces/getDataset/4.2.3.2 nombre de collones.");
							}

							int tokenId = Integer.valueOf(result.getObject(1).toString());
							int pixels = Integer.valueOf(result.getObject(2).toString());
							int topLeftX = Integer.valueOf(result.getObject(3).toString());
							int topLeftY = Integer.valueOf(result.getObject(4).toString());
							int width = Integer.valueOf(result.getObject(5).toString());
							int height = Integer.valueOf(result.getObject(6).toString());
							PageElementPropertyValue transcription = null;
							PageElementPropertyValue traduction = null;

							PageElementToken token = new PageElementToken(tokenId, pixels, topLeftX, topLeftY, width, height, transcription, traduction);
							log.logInfo("PageElementToken associé a PageElementZone:" + zone.getId() + " importé (ID:" + 
									tokenId + ", pixels:" +
									pixels + ", topLeftX:" +
									topLeftX + ", topLeftY:" +
									topLeftY + ", width:" +
									width + ", height:" +
									height + ")");
							zone.getMots().add(token);
						}

					}catch (Exception e) {
						if(e instanceof MyBDDException){
							throw new MyBDDException(((MyBDDException) e).getDescription());
						}else{
							e.printStackTrace();
						}
					}

					///// 4.2.3.3 boucle sur les PE tokens
					for(PageElementToken token : zone.getMots()){

						////// 4.2.3.3.1 recuperer tradaction et transcription du PE token
						try{
							Statement state;
							state = conn.createStatement();
							ResultSet result = state.executeQuery(
									"SELECT PAGE_ELEMENT_P_VAL_UNDERLYING.ID, " +
											"PAGE_ELEMENT_P_VAL_UNDERLYING.VALUE_TYPE, " +
											"PAGE_ELEMENT_P_VAL_UNDERLYING.VALUE, " +
											"FROM PAGE_ELEMENT_P_VAL_UNDERLYING, HAS_VALUE " +
											"WHERE HAS_VALUE.PAGE_ELEMENT_ID = " + String.valueOf(token.getId()) +
											" AND CPAGE_ELEMENT_P_VAL_UNDERLYING.ID = HAS_VALUE.PAGE_ELEMENT_PROPERTY_VALUE_ID"
									);

							ResultSetMetaData resultMeta = result.getMetaData();

							while(result.next()){

								if(resultMeta.getColumnCount() != 3){
									throw new MyBDDException("Erreur BDDAcces/getDataset/4.2.3.3.1 nombre de collones.");
								}

								int pvId = Integer.valueOf(result.getObject(1).toString());
								int valueType = Integer.valueOf(result.getObject(2).toString());
								String value = result.getObject(1).toString();

								if(valueType == DataTypeProperty.TRANSLATION){
									token.setTraduction(new PageElementPropertyValue(pvId, "traduction", value));
									log.logInfo("Traduction du PEToken:" + token.getId() + " importé (ID:" +
											pvId + ", value:" + value +")");
								}
								if(valueType == DataTypeProperty.TRANSCRIPTION){
									token.setTraduction(new PageElementPropertyValue(pvId, "transcription", value));
									log.logInfo("Traduction du PEToken:" + token.getId() + " importé (ID:" +
											pvId + ", value:" + value +")");
								}
							}

						}catch (Exception e) {
							if(e instanceof MyBDDException){
								throw new MyBDDException(((MyBDDException) e).getDescription());
							}else{
								e.printStackTrace();
							}

						}

					}

				}

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


	public void close() throws SQLException {
		conn.close();
	}

	public int insertDataItem(String name,String flag) throws SQLException{
		int id = 0;


		String query = "INSERT INTO DAE.DATA_ITEM_UNDERLYING (DESCRIPTION, FLAG) VALUES (?,?)";
		String queryId = "SELECT seq_data_item.currval from dual";

		conn.setAutoCommit(false);
		CallableStatement state;
		state = conn.prepareCall(query);
		state.setObject(1, name);
		state.setObject(2, flag);


		int affectedRows = state.executeUpdate();
		if(affectedRows == 0){
			throw new SQLException("Creating data item failed");
		}
		Statement currvalStatement = conn.createStatement();
		ResultSet result = currvalStatement.executeQuery(queryId);

		if(result.next()){
			id = result.getInt(1);
		}


		conn.commit();
		conn.setAutoCommit(true);
		return id;
	}

	public void insert(String query,ArrayList<Object> collumns) throws SQLException{
		CallableStatement state;
		state = conn.prepareCall(query);
		for(int i=0;i<collumns.size();i++){
			state.setObject(i+1, collumns.get(i));
		}

		state.execute();
		//state.close();

	}

	public void insertImageDataItem(int id) throws SQLException{
		ArrayList<Object> collumns = new ArrayList<Object>();
		String query = "INSERT INTO DAE.IMAGE_DATA_ITEM (ID) VALUES (?)";
		collumns.add(id);
		insert(query,collumns);
	}

	public void insertLogicalImageDataItem(int id) throws SQLException{
		ArrayList<Object> collumns = new ArrayList<Object>();
		String query = "INSERT INTO DAE.LOGICAL_IMAGE_DATA_ITEM (ID) VALUES (?)";
		collumns.add(id);
		insert(query,collumns);
	}
	public void insertPhysicalImageDataItem(int id) throws SQLException{
		ArrayList<Object> collumns = new ArrayList<Object>();
		String query = "INSERT INTO DAE.PHYSICAL_IMAGE_DATA_ITEM (ID) VALUES (?)";
		collumns.add(id);
		insert(query,collumns);
	}

	public void deleteDataItem(int id) throws SQLException{
		String query = "DELETE FROM DAE.DATA_ITEM_UNDERLYING WHERE ID=" + id ;
		this.executeQuery(query);
	}

	public boolean insertContributor(int dataItemId,String name) throws SQLException{
		String query = "SELECT DAE.PERSON.ID FROM DAE.PERSON WHERE DAE.PERSON.NAME = '" + name + "'";
		ResultSet result = executeQuery(query);
		
		if(!result.next()){
			query = "INSERT INTO DAE.PERSON (ID,NAME) VALUES (?,?)";
			int id = 0;

			conn.setAutoCommit(false);

			String queryId = "SELECT seq_data_item.nextval from dual";
			Statement nextValStatement = conn.createStatement();
			result = nextValStatement.executeQuery(queryId);

			if(result.next()){
				id = result.getInt(1);
			}

			CallableStatement state;
			state = conn.prepareCall(query);
			state.setObject(1, id);
			state.setObject(2, name);


			int affectedRows = state.executeUpdate();
			if(affectedRows == 0){
				throw new SQLException("Creating data item failed");
			}



			conn.commit();
			conn.setAutoCommit(true);

			query = "INSERT INTO DAE.CONTRIBUTE (PERSON_ID,DATA_ITEM_ID) VALUES (?,?)";
			ArrayList<Object>collumns = new ArrayList<Object>();
			collumns.add(id);
			collumns.add(dataItemId);
			this.insert(query, collumns);
			return true;
		}
		return false;
	}


}
