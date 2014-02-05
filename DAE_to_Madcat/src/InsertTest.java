import java.sql.SQLException;
import java.util.ArrayList;

import BDDAccess.BDDAccess;
import DAEStructure.Dataset;
import DAEStructure.PageElementSegment;
import DAEStructure.PageElementToken;
import DAEStructure.PageElementZone;
import DAEStructure.PageImage;


public class InsertTest {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		BDDAccess bdd = new BDDAccess();
		
		Dataset dataset = new Dataset();
		dataset.setName("OpenHart Document");
		dataset.setId(650839);
		//dataset.insert(bdd);
		
		PageImage image = new PageImage();
		String path = "/dae/database/Demo-generated/AAW_ARB_20070121.0096_8_LDC0108.tif";
		
		image.setHdpi(600);
		image.setVdpi(600);
		image.setPath(path);
		image.setHeight(6600);
		image.setWidth(5100);
		
		//image.insert(bdd, dataset);
		image.setId(650842);
		
		PageElementSegment segment = new PageElementSegment();
		segment.setBoundary("(3987;916),(3794,916)");
		segment.setName("S1");
		
		segment.insert(bdd, image);
		
		PageElementZone zone = new PageElementZone();
		zone.setBoundary("(3987;916),(3794,916)");
		zone.setName("Z1");
		//zone.insert(bdd,segment);
		zone.setId(650849);
		
		PageElementToken token = new PageElementToken();
		token.setHeight(500);
		token.setWidth(500);
		token.setTopLeftX(3500);
		token.setTopLeftY(1000);
		
		token.insert(bdd,zone);
		
		bdd.close();
	}

}
