package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.Set;

public abstract class DefaultSocketServer extends Thread implements
		SocketClientInterface, SocketClientConstants {
	private ObjectOutputStream writer;
	private ObjectInputStream reader;
	private Socket clientSocket;

	public DefaultSocketServer(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public void run() {
		if (openConnection()) {
			handleSession();
			closeSession();
		}
	}

	@Override
	public boolean openConnection() {
		//set up output stream and then input stream
		try {
			writer = new ObjectOutputStream(clientSocket.getOutputStream());
			reader = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Unable to obtain stream");
			return false;
		}
		return true;
	}

	@Override
	public void handleSession() {
		try {
				handleInput();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleInput() throws ClassNotFoundException, IOException {
		while (true) {
			/* Read command from client */
			int input = (int) reader.readObject();
			BuildCarModelOptions build = new BuildCarModelOptions();

			if (input == CREATE_AUTO) {
				/*
				 * Accept properties object from client, create an Automobile
				 * and add it to LinkedHashMap
				 */
				Properties props = (Properties) reader.readObject();
				build.buildAutoFromProperty(props);
				/* Send a command to client when Automobile is created */
				sendOutput(CREATE_AUTO_SUCC);
				
			} else if (input == GET_AVAILABLE_AUTO) {
				/*Send a list of available models to client*/
				Set<String> list = build.ProvideAutoList();
				sendOutput(list.toString());
				
			} else if (input == SELECT_AUTO) {
				/*Accept a model name from client and send the selected Automobile to client*/
				String model = (String) reader.readObject();
				sendOutput(build.ProvideAuto(model));
				
			} else if (input == END_SERVE) {
				/*Exit the loop if client sends a end command*/
				break;
			}
		}
	}
	//Send object to the client
	public void sendOutput(Object output) {
		try {
			writer.writeObject(output);
		} catch (IOException e) {
			if (DEBUG)
				System.out.println("Error writing to client");
		}
	}

	@Override
	public void closeSession() {
		try {
			writer = null;
			reader = null;
			clientSocket.close();
		} catch (IOException e) {
			if (DEBUG)
				System.err.println("Error closing clientSocket");
		}
	}

}
