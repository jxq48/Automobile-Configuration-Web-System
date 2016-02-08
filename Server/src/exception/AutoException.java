package exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@SuppressWarnings("serial")
public class AutoException extends Exception implements FixAuto{
	private int errorno;
	private String errormsg;

	public AutoException(int errorno, String errormsg) {
		super();
		this.errorno = errorno;
		this.errormsg = errormsg;
		writeToLog(errorno, errormsg);
	}

	/*Log AutoException with timestams into a log file*/
	public void writeToLog(int errorno, String errormsg) {		
		try {
		long tsp = System.currentTimeMillis();
        File file = new File("exception_log.txt");
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(Long.toString(tsp)+" ");
        output.write(Integer.toString(errorno)+" "+errormsg);
    	output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getErrorno() {
		return errorno;
	}

	public String getErrormsg() {
		return errormsg;
	}
	
	/*Handle exceptions with different fix methods in Fix1to5 class according to errorno.
	 * It will return a new filename when IOException is raised and return null in other situations.*/
	public String fix(int errorno, String errormsg) {
		Fix1to5 f1 = new Fix1to5();
		String result = null;
		switch(errorno) {
		case 2 : f1.fix_wrongBaseprice(errorno, errormsg);break;
		case 1 : result = f1.fix_missFilename(errorno, errormsg);break;
		case 3 : f1.fix_missModelName(errorno, errormsg);break;
		case 4 : f1.fix_missOptionName(errorno, errormsg);break;
		case 5 : f1.fix_missOptionSet(errorno, errormsg);break;
		}
		return result;
	}
	

}
