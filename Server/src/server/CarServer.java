package server;

import adapter.BuildAuto;
import adapter.CreateAuto;

public class CarServer {
	public static void main(String arg[]) {
		/*
		 * Create one model with normal format file and put it into
		 * LinkedHashMap before start serving.
		 */
		CreateAuto buildauto = new BuildAuto();
		buildauto.buildAuto("HondaCivic.txt", 0);
		buildauto.buildAuto("ToyotaCorolla.txt", 0);
		CreateSocket server = new CreateSocket(null);
		server.startServer();
	}
}
