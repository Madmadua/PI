import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;
import DAEStructure.Dataset;


public class InsertTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		BDDAccess bdd = new BDDAccess();
		
		int id = bdd.insertDataItem("OpenHart Document", "dataset");
		System.out.println(id);
		bdd.insertImageDataItem(id);
		bdd.insertLogicalImageDataItem(id);
		
		Dataset dataset= new Dataset();
		dataset.setId(id);
		dataset.setName("OpenHart Document");
		
		dataset.insert(bdd);
		
		bdd.close();
	}

}
