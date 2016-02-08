package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CreateSocket extends DefaultSocketServer {
	 private ServerSocket serverSocket = null;
	 private Socket clientSocket = null;

	public CreateSocket(Socket clientSocket) {
		super(clientSocket);
	}

	public void CreateServerSocket() {
		try {
			serverSocket = new ServerSocket(iDAYTIME_PORT);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 4444.");
			System.exit(1);
		}
	}

	public void startServer() {
        System.out.println("Waiting...");
        CreateServerSocket();
		while (true) {
			try {
				clientSocket = serverSocket.accept();
		         System.out.println("Accepted connection : " + clientSocket);
		         CreateSocket d1 = new CreateSocket(clientSocket);
				d1.start();

			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		}
	}


}
