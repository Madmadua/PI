package metricFileMaker;

public class MFMMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MetricFileMaker mfm = new MetricFileMaker("AAW_ARB_20070121.0096_8_LDC0108");
		mfm.makeRequests();
		mfm.makeHypSGML();
	}

}
