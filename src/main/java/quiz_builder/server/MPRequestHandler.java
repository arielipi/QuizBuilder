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
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quiz_builder.shared.Util;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MPRequestHandler extends HttpServlet {
	private final static Logger log = Logger.getLogger(MPRequestHandler.class.getName());
	//adding to test
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Util.addCORSHeaders(request, response);
    	
		//read JSON string from request
		StringBuffer jsReq = new StringBuffer();
		String line;
		BufferedReader reader = request.getReader();
	    while((line = reader.readLine()) != null) {
	    	jsReq.append(line);
	    }
	    String requestType = null;
	    JSONObject j = null;
    	REQUEST_PROTOCOL type = null;
	    
	    try {
	    	j = new JSONObject(jsReq.toString());
	    	requestType = j.getString("type");

	    	try {
	    		type = REQUEST_PROTOCOL.valueOf(requestType);
	    	} catch (IllegalArgumentException e) {
	    		type = REQUEST_PROTOCOL.ERROR;
	    	} catch (NullPointerException e) {
	    		type = REQUEST_PROTOCOL.ERROR;
	    	}
	    } catch (JSONException e) {
	    	requestType = "ERROR";
		} 
	    
    	RunningMultiGame rmg = new RunningMultiGame(); 
    	HttpSession session = request.getSession(true); 
    	String rmgKey;
    	String gameKey;
    	
    	try {
	    	switch(type) {
	    	case MULTI_GAMES_LIST:
	    		MultiGamesListProvider glp = new MultiGamesListProvider();
	    		gameKey = j.getString("value");
				//response.getWriter().write(new ResponseFactory().getGamesListResponse(glp.getGamesList(gameKey)));
	    		response.getWriter().write(new ResponseFactory().getMultiGamesListResponse(glp.getGamesList(gameKey)));
	    		break;
	    	case CREATE_MULTI_GAME:
	    		gameKey = j.getString("value1");
	    		String name = j.getString("value2");
	    		rmg = new RunningMultiGame(gameKey, name);
	    		rmg.joinPlayer(session.getId());
	    		sendNextGameResponse(rmg, response, session.getId());
	    		rmg.saveToDS();
	    		session.setAttribute("running_game_key", rmg.getKey());
	    		break;
	    	case JOIN_MULTI_GAME:
	    		rmgKey = j.getString("value");
	    		rmg.getFromDS(rmgKey);
	    		rmg.joinPlayer(session.getId());
	    		sendNextGameResponse(rmg, response, session.getId());
	    		rmg.saveToDS();
	    		session.setAttribute("running_game_key", rmg.getKey());
	    		//request.getSession().setAttribute("running_game_key", rmg.getKey());
	    		break;
	    	case START_MULTI_GAME:
	    		rmgKey = (String) session.getAttribute("running_game_key");
	    		//gameKey = (String) request.getSession().getAttribute("running_game_key");
	    		//log.warning("gameKey is " + gameKey);
	    		rmg.getFromDS(rmgKey);
	    		rmg.startGame(session.getId());
	    		sendNextGameResponse(rmg, response, session.getId());
	    		rmg.saveToDS();
	    		session.setAttribute("running_game_key", rmg.getKey());
	    		break;
	    	case NEXT_QUESTION:
	    		rmgKey = (String) session.getAttribute("running_game_key");
	    		rmg.getFromDS(rmgKey);
	    		rmg.addScore(session.getId(), j.getInt("value"));
	    		sendNextGameResponse(rmg, response, session.getId());
	    		rmg.saveToDS();
	    		session.setAttribute("running_game_key", rmg.getKey());
	    		break;
	    	case ERROR:
	    	default:
	    		response.getWriter().write("ERROR");
	    		break;
	    	}
    	} catch(JSONException e) {
    		e.printStackTrace();
    	}
    	/*catch (Exception e) {

    		//log.warning("caught last exception");
    		e.printStackTrace();
    		//log.warning(e.printStackTrace());
    		//response.getWriter().write("ERROR");
    		requestType = "ERROR";
    	}*/ catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	private void sendNextGameResponse(RunningMultiGame rmg, HttpServletResponse response, String playerSessionID) throws IOException
	{
		//String gameResponse = rmg.getNextResponse(playerSessionID);
		//response.getWriter().write(gameResponse);
		
		String gameResponse = rmg.getNextResponse(playerSessionID);
		//response.getWriter().write(gameResponse);

		byte[] utf8JsonString = gameResponse.getBytes("UTF8");
		log.warning(gameResponse);
		log.warning(utf8JsonString.toString());
		response.getOutputStream().write(utf8JsonString, 0, utf8JsonString.length);
		//response.getWriter().writeB(utf8JsonString, 0, utf8JsonString.length);
	}
	
}
