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
package quiz_builder.shared;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;












import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class Game implements Serializable {

	private static final String NAME = "name";
	private static final String USER_ID = "userID";
	private static final String START_COUNT = "started_count";
	private static final String COMP_COUNT = "completed_count";
	private static final String AVG_SCORE = "average_score";

     /**
	 * It's Serializable because we need to save it to session
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Level> levels;
	private String name;
	private String userID;
	private Entity entity;
	
	private long started_count;
	private long completed_count;
	private double average_score;


	/*public Game(ArrayList<Question> questions) {
		this.questions = questions;
	}*/

	public Game(String name, String userID) {
		this.name = name;
		this.levels = new ArrayList<Level>();
		this.userID = userID;
		this.entity = new Entity("game");
		this.started_count = 0;
		this.completed_count = 0;
		this.average_score = 0f;
	}
	
//	public Game(String givenFile, String publicity, int dummyInt) {
//		String[] processFile = givenFile.split("\n");
//		for(String iter : processFile)
//		this.name = name;
//		this.levels = new ArrayList<Level>();
//		this.userID = publicity;
//		this.entity = new Entity("game");
//		this.started_count = 0;
//		this.completed_count = 0;
//		this.average_score = 0f;
//	}


	public Game(Entity game) {
		//Receive the list of embedded entity.
		 
		 this.name = (String) game.getProperty(NAME);
		 this.userID = (String) game.getProperty(USER_ID);
		 this.entity = game;

		 this.started_count = (Long)game.getProperty(START_COUNT);
		 this.completed_count = (Long)game.getProperty(COMP_COUNT);
		 this.average_score = (Double)game.getProperty(AVG_SCORE);
		 
		 //Create new levels list.
		 this.levels = new ArrayList<Level>();
		 @SuppressWarnings("unchecked")
		 ArrayList<EmbeddedEntity> levelsArrEntity =
		 (ArrayList<EmbeddedEntity>) game.getProperty("levelsArrEntity");
		 
		 //Create question from each embedded entity.
		 if (levelsArrEntity != null) {
			 for (EmbeddedEntity embeddedEntity : levelsArrEntity) {
				 
				 //From embedded entity to entity.
				 Key key = embeddedEntity.getKey();
				 
				 //Entity with key from embeddedEntity.
				 Entity entityLevel = new Entity(key);
				 
				//Import data to entity.
				 entityLevel.setPropertiesFrom(embeddedEntity);
				 //Create level from entity and push to List.
				 this.levels.add(new Level(entityLevel));
				
			 }//End for
		 }
	}//End constructor.


	public ArrayList<Level> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<Level> givenLevels) {
		this.levels = givenLevels;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getName() {
		return this.name;
	}


	//Create entity from this question.
    public Entity toEntity() {
    	
        this.entity.setProperty(NAME, this.name);
        this.entity.setProperty(USER_ID, this.userID);
        this.entity.setProperty(START_COUNT, this.started_count);
        this.entity.setProperty(COMP_COUNT, this.completed_count);
        this.entity.setProperty(AVG_SCORE, this.average_score);
       
        //List of embedded entities for the questions
        List<EmbeddedEntity> levelsArrEntity = new ArrayList<EmbeddedEntity>();
        
        for (Level level : this.levels) {
        	//Receive question as entity.
        	Entity eLevel = level.toEntity();
            //Add to array.*/
        	EmbeddedEntity embeddedEntityLevel = new EmbeddedEntity();
        	embeddedEntityLevel.setPropertiesFrom(eLevel);
            levelsArrEntity.add(embeddedEntityLevel);
        }

        //Insert the embedded questionsArrEntity to the game.
        this.entity.setProperty("levelsArrEntity", levelsArrEntity);
        return this.entity;
    }//End to entity.
    
    public void startGame() {
    	this.started_count++;
    }
    
    public void finishGame(int score) {
    	double score_to_now = this.completed_count * this.average_score;
    	score_to_now += score;
    	this.completed_count++;
    	this.average_score = score_to_now / this.completed_count; 
    }

	public long getStarted_count() {
		return started_count;
	}


	public long getCompleted_count() {
		return completed_count;
	}


	public double getAverage_score() {
		return average_score;
	}
    
	public String toJSONString() {
		Gson gson = new Gson();
    	String jsonstr = gson.toJson(this);
    	return jsonstr;
	}

	public Game prepareDownload() {
		Game gameToReturn = new Game(this.name, this.userID);
		gameToReturn.average_score = this.average_score;
		gameToReturn.completed_count = this.completed_count;
		gameToReturn.started_count = this.started_count;
		for(Level levelIte : this.levels) {
			gameToReturn.levels.add(levelIte.prepareDownload());
		}
		return gameToReturn;
	}
	
	public String toPrettyJSONString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String jsonstr = gson.toJson(this);
    	return jsonstr;
	}

	public String toPrettyJSONStringTest() {
		String finalResult = "";
		String presetTab = "	"; // one tab
		String gameName = this.getName();
		finalResult = finalResult + "GameName : \"" + gameName + "\"\n";
		finalResult = finalResult + "levels {\n";
		for(Level levelToHandle : this.levels) {
			finalResult = finalResult + presetTab + "{\n"; // one tab
			finalResult = finalResult + levelToHandle.toPrettyJSONStringTest();
			finalResult = finalResult + "\n" + presetTab + "}\n"; // one tab
		}
		finalResult = finalResult + "}";
		return finalResult;
	}
	
	/** example of a format
	 *  GameName : "Recognize The"
		levels {
			{
				LevelName : "Shape"
				questions {
					{
						type : "AMERICAN"
						question_text : "A square is a shape with"
						correct_answer : "Four edges"
						points : 10
						time : 0
						incorrect_answers {
							"Three edges"
							"Five edges"
							"No edges"
						}
					}
					{
						type : "OPEN"
						question_text : "another test"
						correct_answer : "Yest"
						points : 10
						time : 0
					}
				}
			}
			{
				LevelName : "Color"
				questions {
					{
						type : "AMERICAN"
						question_text : "Israel\u0027s flag\u0027s colors are"
						correct_answer : "Blue and white"
						points : 10
						time : 0
						incorrect_answers {
							"Blue"
							"White"
							"Blue and white and red"
						}
					}
					{
						type : "OPEN"
						question_text : "test new design"
						correct_answer : "yes"
						points : 10
						time : 0
					}
				}
			}
		}
	 */

}//End class.


