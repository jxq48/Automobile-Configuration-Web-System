package com.jin;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Automobile;
import model.OptionSet;

@SuppressWarnings("serial")
public class GetOptions extends ConnectServer{
	
	public GetOptions() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException {
		    
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		String ModelName = request.getParameter("ModelName");
		
		Automobile automobile = null;
		//Start ExecutorService
		start();
		automobile = autos.get(ModelName);		
		//Send the automobile object to jin.jsp
		ServletContext context = this.getServletContext();
        context.setAttribute("HELLO.AUTO", automobile);
		//Shut down the executor service
        service.shutdownNow();
        
        //Print Web page of Basic Car Choice
		out.println(
				"<html> \n" +
				        "<head> \n" +
				          "<title>GetOptions </title> \n" +
				        "</head> \n" +
				        "<body> \n" +
				        "<h1>Basic Car Choice</h1> \n"+
				        	"<form action=\"jin.jsp\" method=\"post\"> \n"+
				        	"<table border=\"1\">"+
				        		"<tr> \n"+
				        			"<td>Make/Model: </td> \n" + 
				        			"<td>"+ModelName +"</td>\n"+
				        		"</tr>"
				   );
		
		//Print all OptionSet information of the automobile
		for(int i = 0; i < automobile.getOpsetSize(); i++) {
			StringBuilder OptionSet = new StringBuilder();
			OptionSet opset = automobile.getOpset(i);
			String OpsetName = automobile.getOpsetName(opset);
			OptionSet.append("<select name=");
			OptionSet.append("\"" + OpsetName + "\"");
			OptionSet.append("id=\"" + OpsetName + "\">");
			//Print Options of each OptionSet
			for (int j = 0; j < automobile.getOptSize(opset); j++) {
				String OptName = automobile.getOptName(j, opset);
				OptionSet.append("<option value=\""+OptName+"\">"+OptName+"</option>");
			}
			OptionSet.append("</select>");
			out.println("<tr> \n"+
							"<td>"+OpsetName+"</td> \n"+
							"<td>"+OptionSet+"</td> \n"+
						"</tr>");
			out.println("<br>");
		} 
			
		out.println(	
						"</table> \n"+	
						"<input type=submit value=\"Done\"> \n" +
		            	"</form> \n" +
				        "</body> \n" +
				      "</html>" 
				);
	}
	protected void doPost(HttpServletRequest request, 
		      HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
