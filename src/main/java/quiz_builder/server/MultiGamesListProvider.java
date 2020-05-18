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
import java.util.logging.Logger;

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
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.labs.repackaged.org.json.JSONArray;

public class MultiGamesListProvider implements Serializable {
	
	public List<GameItem> getGamesList(String gameKey) {
		List<GameItem> gamesList = new ArrayList<GameItem>();
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		//works
		Query q = new Query("RunningMultiGame");
		q.setFilter(new Query.FilterPredicate("gameKey", FilterOperator.EQUAL , gameKey));
		
		/* original
		Query q = new Query("RunningMultiGame");
		q.setFilter(new Query.FilterPredicate( "gameKey", FilterOperator.EQUAL , gameKey));
		q.addProjection(new PropertyProjection("name", String.class));
		*/
		PreparedQuery games = ds.prepare(q);
		int i = 1;
		try {
			for(Entity game: games.asIterable()) {
				boolean hasStarted = (boolean)game.getProperty("started");
				if(!hasStarted) {
					String name = (String)game.getProperty("name");
					String key = KeyFactory.keyToString(game.getKey());
					gamesList.add(new GameItem(name, key));
				}
			}
		} catch(IllegalStateException e) {
			e.printStackTrace();
		} catch(IllegalArgumentException  e) {
			e.printStackTrace();
		} //catch(Exception e) {
		//	log.warning("got Exception inside getGamesList of MultiGamesListProvider");
		//	e.printStackTrace();
		//}
		return gamesList;
		
	}

}
