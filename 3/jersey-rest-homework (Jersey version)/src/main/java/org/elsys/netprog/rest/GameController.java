package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.elsys.netprog.rest.Game;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sun.jersey.spi.resource.*;;

@Singleton
@Path("/game")
public class GameController {
	
	private HashMap<String, Game> games = new HashMap<String, Game>();
	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException{
		
		Game newgame = new Game();
    	games.put(newgame.getId(), newgame);
    	
		return Response.ok(newgame.getId(), MediaType.APPLICATION_JSON).status(201).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception{
		//TODO: Add your code here
	
    	
    	if(!hasUniqueCharacters(guess) || guess.length() != 4) {
    		return Response.status(400).build();
    	}else if(!games.containsKey(gameId)) {
    		return Response.status(404).build();
    	}else {
    		Game game = games.get(gameId);
    		game.solve(guess);
    		
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
    		
    		return Response.ok(json.toString(), MediaType.APPLICATION_JSON).status(200).build();
    		
    	}
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() {
		//TODO: Add your code here
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
    	    	
    	
		return Response.ok(jarray.toString(), MediaType.APPLICATION_JSON).status(201).build();
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
