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

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.sun.org.apache.regexp.internal.RE;

import quiz_builder.shared.Game;
import quiz_builder.shared.GameItem;
import quiz_builder.shared.Question;

public class ResponseFactory {
	
	private class Response
	{
		private String type;
		private String value;
		
		public String toJSONString() {
			Gson gson = new Gson();
			String jsonStr = gson.toJson(this);
			
			return jsonStr;
		}
	}
	
	private class ListResponse
	{
		private String type;
		private List<GameItem> value;
		
		public String toJSONString() {
			Gson gson = new Gson();
			String jsonStr = gson.toJson(this);
			return jsonStr;
		}
	}
	
	private class ScoresListResponse
	{
		private String type;
		private Integer[] value;
		private int place;
		
		public String toJSONString() {
			Gson gson = new Gson();
			String jsonStr = gson.toJson(this);
			return jsonStr;
		}
	}
	
	public String getGamesListResponse(List<GameItem> gameList) {
		ListResponse response = new ListResponse();
		response.type = REQUEST_PROTOCOL.GAMES_LIST.name();
		response.value = gameList;
		return response.toJSONString();
	}
	
	public String getMultiGamesListResponse(List<GameItem> gameList) {
		ListResponse response = new ListResponse();
		response.type = REQUEST_PROTOCOL.MULTI_GAMES_LIST.name();
		response.value = gameList;
		return response.toJSONString();
	}
	
	public String getQuestionResponse(Question question) {
		Response response = new Response();
		response.type = REQUEST_PROTOCOL.QUESTION.name();
		response.value = question.toJSONString();
		return response.toJSONString();
		//return question.toJSONString();
	}
	
	public String getEndGameResponse(int score) {
		Response response = new Response();
		response.type = REQUEST_PROTOCOL.END_GAME.name();
		response.value = new Integer(score).toString();
		return response.toJSONString();
	}
	
	public String getEndLevelResponse(String levelName) {
		Response response = new Response();
		response.type = REQUEST_PROTOCOL.END_LEVEL.name();
		response.value = levelName;
		return response.toJSONString();
	}

	public String getWaitResponse(int numPlayers) {
		Response response = new Response();
		response.type = REQUEST_PROTOCOL.WAIT.name();
		response.value = String.valueOf(numPlayers);
		return response.toJSONString();
	}
	
	public String getEndMultiGameResponse(Integer[] scores, int place) {
		ScoresListResponse response = new ScoresListResponse();
		response.type = REQUEST_PROTOCOL.END_GAME.name();
		response.value = scores;
		response.place = place;
		
		return response.toJSONString();
	}
}