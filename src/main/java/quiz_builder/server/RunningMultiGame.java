package quiz_builder.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import quiz_builder.shared.Game;
import quiz_builder.shared.Level;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class RunningMultiGame implements Serializable {

	
	private Game game = null;
	private int level_ind = 0;
	private int question_ind = 0;
	private boolean started = false;
	private Entity entity;
	private String gameKey;
	private String name;
	
	//private List<String> playerSessionIds = new ArrayList<String>();
	
	//private HashMap<String, Integer> playerQInd = new HashMap<String, Integer>(); 
	//private HashMap<String, Integer> playerLInd = new HashMap<String, Integer>(); 
	
	//private HashMap<String, Integer> playerScores = new HashMap<String, Integer>(); 
	private HashMap<String, Player> playerHM = new HashMap<String, Player>(); 
	
	//private int playersUpToDate = 0;

	private class Player implements Serializable 
	{
		public int player_level_ind;
		public int player_question_ind;
		public int player_score;
		
		public Player() {
			player_level_ind = -1;
			player_question_ind = 0;
			player_score = 0;
		}
		
		public Player(Entity givenEntity) {
			//new Long((long) givenEntity.getProperty("pli")).intValue();
			player_level_ind = new Long((long) givenEntity.getProperty("pli")).intValue();//(int)givenEntity.getProperty("pli");
			player_question_ind = new Long((long) givenEntity.getProperty("pqi")).intValue();//(int)givenEntity.getProperty("pqi");
			player_score = new Long((long) givenEntity.getProperty("ps")).intValue();//(int)givenEntity.getProperty("ps");
		}
		
		public void setLInd(int givenLevelIndex) {
			player_level_ind = givenLevelIndex;
		}
		
		public void setQInd(int givenQIndex) {
			player_question_ind = givenQIndex;
		}
		
		public void setScore(int givenScore) {
			player_score = givenScore;
		}
		
		public Entity toEntity() {
			Entity playerEnt = new Entity("playerEnt");
			playerEnt.setProperty("pli", player_level_ind);
			playerEnt.setProperty("pqi", player_question_ind);
			playerEnt.setProperty("ps", player_score);
			return playerEnt;
		}
	}
	
	private class PlayerItem implements Serializable {
		public Player player;
		public String playerString;
		
		public PlayerItem(String givenPlayerString, Player givenPlayer) {
			this.playerString = givenPlayerString;
			this.player = givenPlayer;
		}
		
		public Entity toEntity() {
			Entity item = new Entity("playerItem");
			item.setProperty("playerKey", playerString);
			Entity playerEnt = player.toEntity();
			EmbeddedEntity embeddedEntityPlayer = new EmbeddedEntity();
			embeddedEntityPlayer.setPropertiesFrom(playerEnt);
			item.setProperty("player", embeddedEntityPlayer);
			return item;
		}
		
	}
	
    public RunningMultiGame(String gameKey, String name) throws EntityNotFoundException {
    	
    	Key key = KeyFactory.stringToKey(gameKey);
    	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    	Entity gameEntity = ds.get(key);
    	game = new Game(gameEntity);
    	this.gameKey = gameKey;
    	this.name = name;
    	
    	this.entity = new Entity("RunningMultiGame");
    }
    
    private void toEntityGame() {
    	Entity gameEnt = this.game.toEntity();
    	EmbeddedEntity embeddedEntityGame = new EmbeddedEntity();
    	embeddedEntityGame.setPropertiesFrom(gameEnt);
    	this.entity.setProperty("game", embeddedEntityGame);
    }
    
    private void toEntityPlayerHM() {
    	List<EmbeddedEntity> playerEmbEnt = new ArrayList<EmbeddedEntity>();
        
    	List<PlayerItem> playerItemList = new ArrayList<PlayerItem>();
    	for(Entry<String, Player> entryPlayer : playerHM.entrySet()) {
    		playerItemList.add(new PlayerItem(entryPlayer.getKey(), entryPlayer.getValue()));
    	}
    	
    	for(PlayerItem playerIte : playerItemList) {
    		Entity ePlayer = playerIte.toEntity();
        	EmbeddedEntity embeddedEntityPlayer = new EmbeddedEntity();
        	embeddedEntityPlayer.setPropertiesFrom(ePlayer);
        	playerEmbEnt.add(embeddedEntityPlayer);
    	}
    	this.entity.setProperty("playerHM", playerEmbEnt);
    }
    
    public Entity toEntity() {
    	
    	this.toEntityGame();    	
    	this.entity.setProperty("level_ind", this.level_ind);
    	this.entity.setProperty("question_ind", this.question_ind);
    	this.entity.setProperty("started", this.started);
    	this.entity.setProperty("gameKey", this.gameKey);
    	this.entity.setProperty("name", this.name);
    	this.toEntityPlayerHM();
    	
    	return this.entity;
    }
    
    public void saveToDS() {
    	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    	//ds.put(this.entity);
    	ds.put(this.toEntity());
    }
    
    public RunningMultiGame(){}
    
    public void getFromDS(String key) throws EntityNotFoundException {
    	
    	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    	Entity entity = ds.get(KeyFactory.stringToKey(key));
    	
    	this.game = null;
    	this.entity = entity;
    	EmbeddedEntity gameEmbeddedEnt = (EmbeddedEntity) entity.getProperty("game");
    	if(gameEmbeddedEnt != null) {
    		Key keyGame = gameEmbeddedEnt.getKey();
			Entity entityGame = new Entity(keyGame);
			entityGame.setPropertiesFrom(gameEmbeddedEnt);
			this.game = new Game(entityGame);
    	}
    	//rmg.game = (Game) entity.getProperty("game");
    	this.level_ind = new Long((long) entity.getProperty("level_ind")).intValue();//(int)entity.getProperty("level_ind");
    	this.question_ind = new Long((long) entity.getProperty("question_ind")).intValue();//(int)entity.getProperty("question_ind");
    	this.started = (boolean)entity.getProperty("started");
    	this.gameKey = (String)entity.getProperty("gameKey");
    	this.name = (String)entity.getProperty("name");
    	this.playerHM = extractPlayerHM(entity);
    }
    
    private HashMap<String, Player> extractPlayerHM(Entity givenEntity) {
    	
    	HashMap<String, Player> tempPlayerHM = new HashMap<String, Player>();
    	List<EmbeddedEntity> playerEmbeddedEnt = (List<EmbeddedEntity>) givenEntity.getProperty("playerHM");

    	if(playerEmbeddedEnt != null) {
    		for (EmbeddedEntity embeddedEntityPlayer : playerEmbeddedEnt) {
    			Key keyPlayer = embeddedEntityPlayer.getKey();
    			Entity entityPlayer = new Entity(keyPlayer);
    			entityPlayer.setPropertiesFrom(embeddedEntityPlayer);
    			EmbeddedEntity playerBuild = (EmbeddedEntity) entityPlayer.getProperty("player");
    			Key keyPlayerTwo = playerBuild.getKey();
    			Entity entityPlayerTwo = new Entity(keyPlayerTwo);
    			entityPlayerTwo.setPropertiesFrom(playerBuild);
    			Player tempPlayer = new Player(entityPlayerTwo);
    			tempPlayerHM.put((String)entityPlayer.getProperty("playerKey"), tempPlayer);
    		}
    	}
    	return tempPlayerHM;
    }
    
    
    public String getKey() {
    	return KeyFactory.keyToString(entity.getKey());
    }
    
    
    public boolean joinPlayer(String playerSessionID) {
    	if (!started) {
        	
        	Player playerToAdd = new Player();
        	this.playerHM.put(playerSessionID, playerToAdd);
        	//playersUpToDate++;
        	return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public void startGame(String playerSessionID) {
    	this.started = true;
    	game.startGame();
    }
    
    private void saveGame() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		//original: why is it? ds.put(game.toEntity());
		ds.put(game.toEntity());
    }
    
	
	public void addScore(String playerSessionID, int add) {
		
		//this.playerScores.put(playerSessionID, this.playerScores.get(playerSessionID) +add);
		
		this.playerHM.get(playerSessionID).setScore(this.playerHM.get(playerSessionID).player_score + add);
		//which of the versions? 
		
		/*Player playerToUpdate = this.playerHM.get(playerSessionID);
		playerToUpdate.player_score = playerToUpdate.player_score + add;
		this.playerHM.put(playerSessionID, playerToUpdate); */
		
	}
	
	
	public int getGameScore(String playerSessionID) {
		//return this.playerScores.get(playerSessionID);
		return this.playerHM.get(playerSessionID).player_score;
	}
	
	public List<Integer> getAllScores() {
		List<Integer> scoreList = new ArrayList<Integer>();
		List<Player> playerList = new ArrayList<Player>(this.playerHM.values());
		
		for(Player playerIte : playerList) {
			scoreList.add(playerIte.player_score);
		}
		
		return scoreList;
	}

    
    public String getNextResponse(String playerSessionID) {
    	
    	String response = "";
    	ResponseFactory rf = new ResponseFactory();
    	
    	//TODO - fill this
    	
    	if (!started) {
    		response = rf.getWaitResponse(playerHM.size());
    	}
    	
    	else if(level_ind >= game.getLevels().size()) {
    		/**
    		 * test area for multi game 12/06
    		 */
    		boolean allFinished = true;
    		
    		for (Player player : playerHM.values()) {
    			if (player.player_level_ind < level_ind) {
    				allFinished = false;
    				player.player_level_ind = level_ind;
    				break;
    			}
    		}
    		
    		if (allFinished) {
    			List<Integer> scoreList = new ArrayList<Integer>(getAllScores());
        		Collections.sort(scoreList);
        		Integer[] scores = scoreList.toArray(new Integer[0]);
        		int place = scoreList.indexOf(playerHM.get(playerSessionID).player_score);
        		
        		response = rf.getEndMultiGameResponse(scores, place);
    		} else {
    			response = rf.getWaitResponse(playerHM.size());
    		}
    		/**
    		 * 
    		 */
    		/**
    		List<Integer> scoreList = new ArrayList<Integer>(getAllScores());
    		Collections.sort(scoreList);
    		Integer[] scores = scoreList.toArray(new Integer[0]);
    		int place = scoreList.indexOf(playerHM.get(playerSessionID).player_score);
    		
    		response = rf.getEndMultiGameResponse(scores, place);
    		*/
    	}
    	
    	else if(playerHM.get(playerSessionID).player_level_ind < level_ind) {
    		response = rf.getEndLevelResponse(game.getLevels().get(level_ind).getName());
    		//Player playerToUpdate = playerHM.get(playerSessionID);
    		//playerToUpdate.player_level_ind = level_ind;
    		//playerToUpdate.player_question_ind = 0;
    		playerHM.get(playerSessionID).player_level_ind = level_ind;
    		playerHM.get(playerSessionID).player_question_ind = 0;
    		//playerHM.put(playerSessionID, playerToUpdate);
    	}
    	
    	else if(playerHM.get(playerSessionID).player_question_ind == question_ind) {
    		response = rf.getQuestionResponse(game.getLevels().get(level_ind).getQuestions().get(question_ind));
    		//Player playerToUpdate = playerHM.get(playerSessionID);
    		//playerToUpdate.player_question_ind = question_ind+1;
    		playerHM.get(playerSessionID).player_question_ind = question_ind+1;
    		//playerHM.put(playerSessionID, playerToUpdate);
    		//playerQInd.put(playerSessionID, question_ind+1);
    		
    		boolean allMore = true;
    		
    		for (Player player : playerHM.values()) {
    			if (player.player_question_ind <= question_ind) {
    				allMore = false;
    				break;
    			}
    		}
    		
    		if (allMore) {
    			question_ind++;
	    		
	    		if (question_ind >= game.getLevels().get(level_ind).getQuestions().size()) {
	    			level_ind++;
	    			question_ind = 0;
	    		}
    		}
    		
    	}
    	
    	else {
    		response = rf.getWaitResponse(playerHM.size());
    	}
    	
    	
    	return response;
    }
}