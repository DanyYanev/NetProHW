package org.elsys.netprog.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(10001);
			
			ArrayList<PrintWriter> clientListOutput = new ArrayList<PrintWriter>();
						
			while(true) {
				Socket clientSocket = serverSocket.accept();
			    System.out.println("client connected from " +
			    		clientSocket.getInetAddress());
			    
			    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				clientListOutput.add(out);
				
				ClientHandler newClient = new ClientHandler(clientListOutput, clientSocket);
				newClient.start();
			}
		    
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			
			System.out.println("Server closed");
		}
	}

}

class ClientHandler implements Runnable {
	   private ArrayList<PrintWriter> clientsListOut;
	   private Socket clientSocket;
	   private BufferedReader in;
	   
	   ClientHandler( ArrayList<PrintWriter> clientsListOutp, Socket clientSocket) {
		   clientsListOut = clientsListOutp;
		   this.clientSocket = clientSocket;
		   
		   try {
			   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
		   } catch (Throwable t) {
				System.out.println(t.getMessage());
		   } 
	   }
	   
	   public void run() {
		   String inputLine;
		   try {
			   while ((inputLine = in.readLine()) != null) {
				   for(int i = 0; i < clientsListOut.size(); i++) {
					   clientsListOut.get(i).println(inputLine);
				   }
			   }  
		   } catch (Throwable t) {
			   System.out.println(t.getMessage());
		   }
	   }
	   
	   public void start () {
		   Thread t = new Thread (this);
		   t.start ();
	   }
	}
