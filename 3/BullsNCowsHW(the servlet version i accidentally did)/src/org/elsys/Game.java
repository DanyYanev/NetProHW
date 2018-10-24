package org.elsys;

import java.util.concurrent.ThreadLocalRandom;
import java.security.SecureRandom;

public class Game {
	private String id;
	private String number;
	private int guesses = 0;
	private boolean solved = false;
	private int lastBulls = 0;
	private int lastCows = 0;

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom rnd = new SecureRandom();
	
	private String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}	
	
	public Game() {
		do {
			number = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));
		} while(!hasUniqueCharacters(number));
		
		id = randomString(10);
	}
	
	public String getId() {
		return id;
	}
	
	public int getGuesses() {
		return guesses;
	}
	
	public boolean isSolved() {
		return solved;
	}
	
	public int getLastBulls() {
		return lastBulls;
	}
	
	public int getLastCows() {
		return lastCows;
	}
	
	public String getNumber() {
		if(solved) {
			return number;
		}else
			return "****";
	}
	
	public boolean solve(String guess) {
		if(solved)
			return true;
		
		guesses++;
		
		if(guess.equals(number)) {
			solved = true;
			lastCows = 0;
			lastBulls = 4;
			return true;
		}
		
		lastCows = 0;
		lastBulls = 0;
		
		for(int i= 0;i < 4;i++){
			if(guess.charAt(i) == number.charAt(i)){
				lastBulls++;
			}else if(number.contains(guess.charAt(i)+"")){
				lastCows++;
			}
		}
		
		return false;
		
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

