package metricFileMaker;

public class MFMMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MetricFileMaker ref = new MetricFileMaker(args[0]);
		ref.makeRequests();
		ref.makeRefSGML(args[2]);
		ref.makeRefTRN(args[2]);
		
		MetricFileMaker hyp = new MetricFileMaker(args[1]);
		hyp.makeRequests();
		hyp.makeHypSGML(args[2]);
		hyp.makeHypTRN(args[2]);
		hyp.makeSRC(args[2]);
		
		}

}
