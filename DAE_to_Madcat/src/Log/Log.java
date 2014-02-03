package Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {
	
	private String path = "/var/log/OpenHaRT.log";

	public Log() {
		super();
		
	}
	
	public void logError(String error){
		try {
			Date maDate = new Date();
			String aEcrire = "[" + maDate.toString() + " : Error ] ";
			aEcrire = aEcrire + error + "\n";
			//aEcrire = "<font color=\"#FF0000\">" + aEcrire + "</font>";
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter output = new BufferedWriter(fw);
			output.write(aEcrire);
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logWarn(String warn){
		try {
			Date maDate = new Date();
			String aEcrire = "[" + maDate.toString() + " : Warn ] ";
			aEcrire = aEcrire + warn + "\n";
			//aEcrire = "<font color=\"#FF0000\">" + aEcrire + "</font>";
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter output = new BufferedWriter(fw);
			output.write(aEcrire);
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logInfo(String info){
		
		try {
			Date maDate = new Date();
			String aEcrire = "[" + maDate.toString() + " : Info ] ";
			aEcrire = aEcrire + info + "\n";
			//aEcrire = "<font color=\"#FF0000\">" + aEcrire + "</font>";
			FileWriter fw = new FileWriter(path, true);
			BufferedWriter output = new BufferedWriter(fw);
			output.write(aEcrire);
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
