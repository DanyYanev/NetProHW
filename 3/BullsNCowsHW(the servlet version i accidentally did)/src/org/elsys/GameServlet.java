package org.elsys;

import java.io.IOException;

import org.json.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Servlet implementation class game
 */
@WebServlet({ "/game", "/game/startGame", "/game/games", "/game/guess/*" })
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Game> games = new HashMap<String, Game>();
       
	private static final String START_GAME = "/game/startGame";
	private static final String GUESS_GAME = "/game/guess";
	private static final String SHOW_GAMES = "/game/games";
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void StartGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	Game newgame = new Game();
    	games.put(newgame.getId(), newgame);
    	
    	response.setStatus(201);
		response.getWriter().append(newgame.getId());
	}
    
    protected void ShowGames(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	JSONArray jarray = new JSONArray();
    	
    	for (Game game : games.values()) {
    		JSONObject json = new JSONObject();
    		try {
    			json.put("gameId", game.getId());
        		json.put("turnsCount", game.getGuesses());
        		json.put("secret", game.getNumber());
        		json.put("success", game.isSolved());
        		jarray.put(json);
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    	}
    	    	
    	response.setStatus(200);
    	response.setContentType("application/json");
		response.getWriter().append(jarray.toString());
	}
    
    protected void GuessGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	
    	String pathInfo = request.getPathInfo(); // /{gameId}/{number}
    	String[] pathParts = pathInfo.split("/");
    	String gameId = pathParts[1]; // {gameID}
    	String number = pathParts[2]; // {number}
    	
    	if(!hasUniqueCharacters(number) && number.length() == 4) {
    		response.setStatus(400);
    	}else if(!games.containsKey(gameId)) {
    		response.setStatus(404);
    	}else {
    		Game game = games.get(gameId);
    		game.solve(number);
    		
    		JSONObject json = new JSONObject();
    		try {
    			json.put("gameId", game.getId());
    			json.put("cowsNumber", game.getLastCows());
    			json.put("bullsNumber", game.getLastBulls());
        		json.put("turnsCount", game.getGuesses());
        		json.put("success", game.isSolved());
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    		
    		response.setStatus(201);
    		response.setContentType("application/json");
    		response.getWriter().append(json.toString());
    	}
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
	    String path = request.getServletPath();
	    switch (path) {
	      case START_GAME:
	    	try {
	    		StartGame(request, response);
	    	}catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
	        break;
	      case SHOW_GAMES:
	    	  try {
	    		  ShowGames(request, response);
	    	  }catch (Exception e) {
	    		  System.out.println(e.getMessage());
	    	  }
	    	  break;
	      case GUESS_GAME:
	    	  try {
		    	  GuessGame(request, response);
	    	  }catch (Exception e) {
	    		  System.out.println(e.getMessage());
	    	  }
	    	  break;
	      default:
	    	  response.setStatus(403);
	    	  try {
	    		  response.getWriter().append(request.getServletPath());
	    	  } catch (Exception e) {
	    		 System.out.println(e.getMessage());
	    	  }
	        break;
	    }
	}

	
	private boolean hasUniqueCharacters(String str) 
    { 
        // If at any time we encounter 2 same 
        // characters, return false 
        for (int i = 0; i < str.length(); i++) 
            for (int j = i + 1; j < str.length(); j++) 
                if (str.charAt(i) == str.charAt(j)) 
                    return false; 
  
        // If no duplicate characters encountered, 
        // return true 
        return true; 
    } 
}