package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.Callable;

import model.Automobile;

public abstract class DefaultSocketClient implements
		Callable<LinkedHashMap<String, Automobile>>, SocketClientInterface,
		SocketClientConstants {

	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private Socket sock;
	private String strHost;
	private int iPort;
	private LinkedHashMap<String, Automobile> Autos = new LinkedHashMap<String, Automobile>();

	public DefaultSocketClient(String strHost, int iPort) {
		setPort(iPort);
		setHost(strHost);
	}

	public void setHost(String strHost) {
		this.strHost = strHost;
	}

	public void setPort(int iPort) {
		this.iPort = iPort;
	}

	//return the LinkedHahMap Autos executing this Callable task
	public LinkedHashMap<String, Automobile> call() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
		return Autos;
	}

	@Override
	public boolean openConnection() {
		try {
			sock = new Socket(strHost, iPort);
		} catch (IOException socketError) {
			if (DEBUG)
				System.err.println("Unable to connect to " + strHost);
			return false;
		}
		// set up output stream and then input stream
		try {
			writer = new ObjectOutputStream(sock.getOutputStream());
			reader = new ObjectInputStream(sock.getInputStream());
		} catch (Exception e) {
			if (DEBUG)
				System.err
						.println("Unable to obtain stream to/from " + strHost);
			return false;
		}
		return true;
	}

	@Override
	public void handleSession() {
		if (DEBUG)
			System.out
					.println("Handling session with " + strHost + ":" + iPort);
		Scanner strInput = new Scanner(System.in);
		handleInput(strInput);
	}

	public void handleInput(Scanner strInput) {
		String[] list = null;

		/*
		 * Send GET_AVAILABLE_AUTO command to server to request a list of
		 * available models.
		 */
		sendOutput(GET_AVAILABLE_AUTO);
		try {
			 //Read the list of model names and save into String[] list.		 
			String line1 = (String) reader.readObject();
			String line2 = line1.substring(1, line1.length() - 1);
			list = line2.split(",");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Send SELECT_AUTO command and the selected model's name to server	 
		for (String name : list) {
			name = name.trim();
			sendOutput(SELECT_AUTO);
			sendOutput(name);
			/*
			 * Accept the available Automobile from server. Put it to a
			 * LinkedHashMap named Autos.
			 */
			try {
				Automobile automobile = (Automobile) reader.readObject();
				Autos.put(name, automobile);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sendOutput(END_SERVE);
	}

	public ObjectOutputStream getWriter() {
		return writer;
	}

	public ObjectInputStream getReader() {
		return reader;
	}

	// Send object to server
	public void sendOutput(Object output) {
		try {
			writer.writeObject(output);
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Error writing to " + strHost);
		}
	}

	@Override
	public void closeSession() {
		try {
			writer = null;
			reader = null;
			sock.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing socket to " + strHost);
		}
	}

}
