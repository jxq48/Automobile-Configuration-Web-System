package exception;

import java.util.Scanner;

import adapter.ProxyAutomobile;




public class Fix1to5 {
	public void fix_wrongBaseprice(int errorno, String errormsg) {
		System.out.println("Error: [errorno=" + errorno + ", errormsg=" + errormsg+"]");
		System.out.println();
		ProxyAutomobile.flag = true;
	}
	
	@SuppressWarnings("resource")
	/*If there is no valid filename, user need to input a new filename in Console*/
	public String fix_missFilename(int errorno, String errormsg) {
		System.out.println("Error: [errorno=" + errorno + ", errormsg=" + errormsg+"]");
		System.out.println("Please input a file name: ");
		Scanner str = new Scanner(System.in);
		String filename = str.next();
	    return filename;
	}

	public void fix_missModelName(int errorno, String errormsg) {
		System.out.println("Error: [errorno=" + errorno + ", errormsg=" + errormsg+"]");
		System.out.println();
		ProxyAutomobile.flag = true;
	}

	public void fix_missOptionName(int errorno, String errormsg) {
		System.out.println("Error: [errorno=" + errorno + ", errormsg=" + errormsg+"]");
		System.out.println();
		ProxyAutomobile.flag = true;
	}

	public void fix_missOptionSet(int errorno, String errormsg) {
		System.out.println("Error: [errorno=" + errorno + ", errormsg=" + errormsg+"]");
		System.out.println();
		ProxyAutomobile.flag = true;
	}

}
