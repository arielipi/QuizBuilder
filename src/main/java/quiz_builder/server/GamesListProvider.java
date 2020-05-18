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
import java.util.List;

import quiz_builder.shared.GameItem;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;

public class GamesListProvider implements Serializable {
	
	public List<GameItem> getGamesList(){
		
		List<GameItem> gamesList = new ArrayList<GameItem>();
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("game");
		q.addProjection(new PropertyProjection("name", String.class));
		//q.setKeysOnly();
		
		PreparedQuery games = ds.prepare(q);
		
		int i = 1;
		for(Entity game: games.asIterable()){
			String name = (String)game.getProperty("name");
			String key = KeyFactory.keyToString(game.getKey());
			gamesList.add(new GameItem(name, key));
		}
		
		return gamesList;
		
	}

}
