package com.jin;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServlet;

import model.Automobile;
import client.CarClient;
import client.SocketClientConstants;

@SuppressWarnings("serial")
public abstract class ConnectServer extends HttpServlet implements
		SocketClientConstants {

	protected ExecutorService service = null;
	private Future<LinkedHashMap<String, Automobile>> task = null;
	protected LinkedHashMap<String, Automobile> autos;

	protected void start() {
		String strLocalHost = "";
		try {
			strLocalHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			System.err.println("Unable to find local host");
		}
		//Get ExecutorService from Executors utility class, thread pool size is 1
		service = Executors.newFixedThreadPool(1);
		//Submit Callable tasks to be executed by thread pool
		task = service.submit(new CarClient(strLocalHost, iDAYTIME_PORT));
		//Get return value using Future
		try {
			autos = task.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	//Shut down the executor service
	protected void shutdown() {
		service.shutdownNow();
	}
}
