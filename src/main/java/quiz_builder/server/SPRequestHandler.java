/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package quiz_builder.server;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quiz_builder.shared.Question;
import quiz_builder.shared.Util;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SPRequestHandler extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		//response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		Util.addCORSHeaders(request, response);
		
		//read JSON string from request
		StringBuffer jsReq = new StringBuffer();
		String line;
		BufferedReader reader = request.getReader();
	    while ((line = reader.readLine()) != null) {
	    	jsReq.append(line);
	    }
	    String requestType = null;
	    JSONObject j = null;
	    
	    try {
	    	j = new JSONObject(jsReq.toString());
	    	requestType = j.getString("type");
	    	REQUEST_PROTOCOL type;
	    	
	    	try {
	    		type = REQUEST_PROTOCOL.valueOf(requestType);
	    	} catch (IllegalArgumentException e) {
	    		type = REQUEST_PROTOCOL.ERROR;
	    	} catch (NullPointerException e) {
	    		type = REQUEST_PROTOCOL.ERROR;
	    	}
	    	
	    	RunningGame rg; 
	    	HttpSession session; 
	    	
	    	switch(type) {
	    	case NEXT_QUESTION:
	    		session = request.getSession(true);
				rg = null;
				rg = (RunningGame)session.getAttribute("GAME");
				rg.addScore(j.getInt("value"));
				sendNextGameResponse(rg, response);
				session.setAttribute("GAME", rg);
				return;
	    	case NEW_GAME: 
	    		try {
					rg = new RunningGame(j.getString("value"));
					session = request.getSession(true);
					sendNextGameResponse(rg, response);
					session.setAttribute("GAME", rg);
					return;
				} catch (EntityNotFoundException e) {
					response.getWriter().write("ERROR");
					return;
				}	
	    	case GAMES_LIST:
	    		GamesListProvider glp = new GamesListProvider();
				response.getWriter().write(new ResponseFactory().getGamesListResponse(glp.getGamesList()));
				return;
	    	case ERROR:
			default:
	    		response.getWriter().write("ERROR");
				return;
	    	}
	
		} 
	    catch (JSONException e) {
	    	requestType = "ERROR";
		}
	    
	    return;
	}
	
	private void sendNextGameResponse(RunningGame rg, HttpServletResponse response) throws IOException
	{
		String gameResponse = rg.getNextResponse();
		//response.getWriter().write(gameResponse);

		byte[] utf8JsonString = gameResponse.getBytes("UTF8");
		response.getOutputStream().write(utf8JsonString, 0, utf8JsonString.length);
		//response.getWriter().writeB(utf8JsonString, 0, utf8JsonString.length);
	}
	
}
