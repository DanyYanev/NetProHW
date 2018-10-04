package org.elsys.netprog.servlet;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> data = new HashMap<String, String>();

    /**
     * Default constructor. 
     */
    public Servlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String output = "<html><body><font size=30>" +
		"<form method='POST'>";
		
		
		output += "<form method='POST'>" +
				"<input type='text' name='key' placeholder='Key' />" +
				"<input type='text' name='value' placeholder='Value' /> " +
				"<input type='submit' />" +
			"</form>";
		
		 Set set = data.entrySet();
	     Iterator iterator = set.iterator();
	     while(iterator.hasNext()) {
	    	 Map.Entry mentry = (Map.Entry)iterator.next();
	         
	         output += "<font size=30>" + mentry.getKey()
				+ " : " + mentry.getValue() + "</font>" + "<br>";
	     }
	     
	     output += "</body></html>";
		
		response.getOutputStream().println(output);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		
		data.put(key, value);
		
		doGet(request, response);
	}

}
