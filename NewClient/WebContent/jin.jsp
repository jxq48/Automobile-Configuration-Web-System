<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import = "model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jin </title>
</head>
<body>
	  Here is what you selected:
	  <br>
	  <table border="1">
	  <%
	    //Accept the Automobile object from servlet
		Automobile auto = (Automobile) getServletContext().getAttribute("HELLO.AUTO");
		String ModelName = auto.getModel();
		String make = auto.getMake();
		int baseprice = (int)auto.getBaseprice();
		/*
		 * Get choices from Servlet using getParameter method. 
		 * Print the choices that user selected and calculating
		 * final price.
		 */
		out.println(	
						"<tr> \n"+
							"<td>"+make+" "+ModelName+"</td> \n"+
							"<td>base price</td> \n"+
							"<td>"+baseprice+"</td> \n"+
						"</tr>");
	    StringBuilder Option = new StringBuilder();	    
	    int finalPrice = baseprice;
		for(int i = 0; i < auto.getOpsetSize(); i++) {
			OptionSet opset = auto.getOpset(i);
			String OpsetName = auto.getOpsetName(opset);
			String OptName = request.getParameter(OpsetName);
			int optPrice = (int)auto.getOptPrice(opset, OptName);
			out.println(
						"<tr> \n"+
							"<td>"+OpsetName+"</td> \n"+
							"<td>"+OptName+"</td> \n"+
							"<td>"+optPrice+"</td> \n"+
						"</tr>");
			finalPrice += optPrice;
		}
		String Price = "<td>"+"$"+finalPrice+"</td> \n";
		out.println("<tr> \n"+
						"<td>Total Cost</td> \n"+
						"<td></td> \n"+
						Price+
					"</tr>");
	  %>
	  </table>
</body>
</html>