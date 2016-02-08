package com.jin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;


@SuppressWarnings("serial")
public class GetModels extends ConnectServer{
	
	public GetModels() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();		
		StringBuffer option = new StringBuffer();
		//Start ExecutorService
		start();
		Set<String> modelname = autos.keySet();
		
		Iterator<String> imodelname = modelname.iterator();
		String a = null;
		while (imodelname.hasNext()) {
			 a = imodelname.next();
			option.append("<option value=\""+a+"\">"+a+"</option>");
		}
		//Shut down the executor service
		service.shutdownNow();
		//Print Web page of available models' names
		out.println (
		 "<html> \n" +
	        "<head> \n" +
	          "<title>GetModels </title> \n" +
	        "</head> \n" +
	        "<body> \n" +
	        	"<form action=\"GetOptions\" method=\"post\"> \n" +
	        	"<p>Available Car Models</p> \n" +
            		"<select name=\"ModelName\"> \n" +
            			option+
            		"</select> \n" +
            		"<input type=submit value=\"Done\">" +
            	"</form> \n" +
	        "</body> \n" +
	      "</html>" );
		
	}	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
	
}
