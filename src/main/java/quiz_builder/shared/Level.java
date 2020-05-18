package quiz_builder.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Level  implements Serializable{
	
	
	private ArrayList<Question> questions;
	
	private String name;
	private Entity entity;
	//private String key;

	public Level(String name) {
		super();
		this.name = name;
		this.questions = new ArrayList<Question>();
		this.entity = new Entity("level");
	}
	
	
	
	public Level(Entity level){
		
		 //Create new questions list.
		 this.questions = new ArrayList<Question>();
		 this.entity = level;
		 
		 this.name = (String) level.getProperty("name");
		 //this.key = KeyFactory.keyToString(level.getKey());
		 
		 @SuppressWarnings("unchecked")
		 ArrayList<EmbeddedEntity> questionsArrEntity = (ArrayList<EmbeddedEntity>) level.getProperty("questionsArrEntity");
		
		 //Create question from each embedded entity.
		 if (questionsArrEntity != null){
			 for (EmbeddedEntity embeddedEntity : questionsArrEntity){
				 
				 //From embedded entity to entity.
				 Key key = embeddedEntity.getKey();
				 
				 //Entity with key from embeddedEntity.
				 Entity entityQuestion = new Entity(key);
				 
				//Import data to entity.
				 entityQuestion.setPropertiesFrom(embeddedEntity);
				 
				 //Create question from entity and push to List.
				 switch (Question.Type.valueOf((String)entityQuestion.getProperty("type"))){
				 case OPEN:
					 this.questions.add(new OpenQuestion(entityQuestion));
					 break;
				 case AMERICAN:
					 this.questions.add(new AmericanQuestion(entityQuestion));
					 break;
				 case AMERICANIMAGEQ:
					 this.questions.add(new AmericanImageQ(entityQuestion));
					 break;
				 }
				
			 }//End for
		 }
		
	}//End cont
	
	
	
	public void addQuestion(Question question){
		
		this.questions.add(question);
		
	}//End add question

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void removeQuestion(int index) {
		this.questions.remove(index);
	}
	
	public Key getKey(){
		return this.entity.getKey();
	}
	
	/*public String getKey(){
		return this.key;
	}*/
	
	public Entity toEntity() {
		Entity entityLevel = this.entity;
		
		entityLevel.setProperty("name", this.name);
		
		//List of embedded entities for the questions
        List<EmbeddedEntity> questionsArrEntity = new ArrayList<EmbeddedEntity>();
        
        /*Convert every question to entity,
         * than to embedded entity,
         * and insert to list of embedded entities.
         */
        for (Question question: this.questions) {
        	//Receive question as entity.
        	Entity eQuestion = question.toEntity();
        	//Receive key
        	Key key = eQuestion.getKey();
            //Embedded, in order to insert it to the game.
            EmbeddedEntity embeddedEntityQuestion = new EmbeddedEntity();
            //Setting the entity key to embedded entity
            embeddedEntityQuestion.setKey(key);
            //Receive the properties of the question entity.
            embeddedEntityQuestion.setPropertiesFrom(eQuestion);
            //Add to array.
        	questionsArrEntity.add(embeddedEntityQuestion);
        }

        //Insert the embedded questionsArrEntity to the game.
        //Entity entityLevel = new Entity("level");
        entityLevel.setProperty("questionsArrEntity", questionsArrEntity);
        return entityLevel;
	
	}
	
	public Level prepareDownload() {
		Level levelToReturn = new Level(this.getName());
		for(Question questionIte : this.questions) {
			if(questionIte.getHasImageQuestion()) {
				;
			} else if(questionIte.getType().equals(Question.Type.AMERICANIMAGEQ)) {
				;
			} else {
				if(questionIte.getType().equals(Question.Type.AMERICAN)) {
					levelToReturn.addQuestion(questionIte);
				} else {
					levelToReturn.addQuestion(questionIte);
				}
			}
		}
		return levelToReturn;
	}
	
	public String toPrettyJSONStringTest() {
		String finalResult = "";
		String presetTab = "		"; // two tabs
		String levelName = this.getName();
		finalResult = finalResult + presetTab + "LevelName : \"" + levelName + "\"\n";
		finalResult = finalResult + presetTab + "questions {\n";
		for(Question questionToHandle : this.questions) {
			finalResult = finalResult + presetTab + "	{\n";
			finalResult = finalResult + questionToHandle.toPrettyJSONStringTest();
			finalResult = finalResult + presetTab + "	}\n";
		}
		finalResult = finalResult + presetTab + "}";
		return finalResult;
	}
	
	/** example of a format
	 *  GameName : "Recognize The"
		levels {
			{ one tab
				LevelName : "Shape" two tabs
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
	
}
