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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import quiz_builder.shared.Game;
import quiz_builder.shared.Level;
import quiz_builder.shared.Question;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class RunningGame implements Serializable {

	private Game game = null;
	private int level_ind = -1;
	private int question_ind = 0;
	private int level_score = 0;
	private int game_score = 0;

	
    public RunningGame(String gameKey) throws EntityNotFoundException {
    	
    	Key key = KeyFactory.stringToKey(gameKey);
    	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    	Entity gameEntity = ds.get(key);
    	game = new Game(gameEntity);
    	game.startGame();
    	saveGame();

    }
    
    public String getNextResponse() {
    	
    	String response;
    	
    	//
    	if (level_ind < 0) {
    		level_ind++;
    		response = new ResponseFactory().getEndLevelResponse(game.getLevels().get(level_ind).getName());
    	}
    	
    	else if(question_ind >= game.getLevels().get(level_ind).getQuestions().size()) {
    		level_ind++;
    		question_ind = 0;
    		if (level_ind >= game.getLevels().size()) {
    			response = new ResponseFactory().getEndGameResponse(game_score);
    			game.finishGame(game_score);
    	    	saveGame();
    		}
    		else {
        		response = new ResponseFactory().getEndLevelResponse(game.getLevels().get(level_ind).getName());
    		}
    		level_score = 0;
    	}
    	
    	else {
    		response = new ResponseFactory().getQuestionResponse(game.getLevels().get(level_ind).getQuestions().get(question_ind));
    		question_ind++;
    	}
    	
    	return response;
    }
    
    private void saveGame() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		ds.put(game.toEntity());
    }
	
	public void addScore(int add) {
		game_score += add;
		level_score += add;
	}
	
	public int getLevelScore() {
		return level_score;
	}
	
	public int getGameScore() {
		return game_score;
	}
}
