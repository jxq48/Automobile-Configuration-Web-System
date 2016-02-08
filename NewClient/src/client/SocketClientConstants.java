package client;

public interface SocketClientConstants {
		int iDAYTIME_PORT = 4444;
	    boolean DEBUG = true;

		//Client sends CREATE_AUTO to request server creating Automobile based on given Properties 
		static final int CREATE_AUTO = 0;
		//Server sends CREATE_AUTO_SUCC when it finish building Automobile from given Properties
		static final int CREATE_AUTO_SUCC = 1;
		//Client sends GET_AVAILABLE_AUTO to request server providing a list of available models
		static final int GET_AVAILABLE_AUTO = 2;
		//Client sends SELECT_AUTO to request server providing the selected Automobile
		static final int SELECT_AUTO = 3;
		//Stop the client and restart the server
		static final int END_SERVE = 4;
	     
}
