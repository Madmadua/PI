import BDDAcces.BDDAcces;
import DAEStructure.Dataset;
import Exception.MyBDDException;


public class Main {

	public static void main(String[] args) {
		
		BDDAcces acces = new BDDAcces();
		try {
			Dataset dataset = acces.getDataset(650796, 650796);
		} catch (MyBDDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
