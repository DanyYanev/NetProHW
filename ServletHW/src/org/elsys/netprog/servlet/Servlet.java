package org.elsys.netprog.servlet;

import java.io.IOException;
import org.json.*;
import java.io.BufferedReader;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet/*")
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
		/*
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
		*/
		
		String key = request.getPathInfo().substring(1);
		
		response.setContentType("application/json");
		
		Set set = data.entrySet();
	    Iterator iterator = set.iterator();
	    JSONObject json = new JSONObject();
	    
	    while(iterator.hasNext()) {
	    	Map.Entry mentry = (Map.Entry)iterator.next();
	    	
	    	if(mentry.getKey().equals(key)) {
	    		try {
		    		json.put(mentry.getKey().toString(), mentry.getValue());
		    	} catch (JSONException e) {
		    		
		    	}
	    	}
	    	
	    }
	    response.getOutputStream().println(json.toString());
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getPathInfo().substring(1);
		
		if(data.containsKey(key)) {
			data.remove(key);
		}
		
		doGet(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getPathInfo().substring(1);
		
		if(!data.containsKey(key))
		{
			response.setStatus(400);
		}
		
		BufferedReader reader = request.getReader();
		
		String body = new String();
		String value = new String();
		
		while(reader.ready()) {
			body += reader.readLine();
			body += '\n';
		}
		
		try {
			JSONObject json = new JSONObject(body);
			if(!json.has("value")) {
				throw new Exception("bad");
			};
			
			value = json.getString("value");
			
			data.replace(key, value);
		
		}catch (JSONException e){
			if(e.getMessage().equals("bad")) {
				response.setStatus(400);
			}
		}catch (Exception e) {
			if(e.getMessage().equals("bad")) {
				response.setStatus(400);
			}
		}
		
		doGet(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String key = request.getParameter("key");
		String value = request.getParameter("value");
	
		BufferedReader reader = request.getReader();
		
		String body = new String();
		
		while(reader.ready()) {
			body += reader.readLine();
			body += '\n';
		}
		
		try {
			JSONObject json = new JSONObject(body);
			if(!json.has("key") && !json.has("value")) {
				throw new Exception("bad");
			};
			
			key = json.getString("key");
			value = json.getString("value");
			
			data.put(key, value);
		}catch (JSONException e){
			if(e.getMessage().equals("bad")) {
				response.setStatus(400);
			}
		}catch (Exception e) {
			if(e.getMessage().equals("bad")) {
				response.setStatus(400);
			}
		}
		
		//System.out.println(body);
		
		doGet(request, response);
	}

}
