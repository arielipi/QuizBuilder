package quiz_builder.shared;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

public class OpenQuestion extends Question {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OpenQuestion() {
		super(Type.OPEN);
	}

	public OpenQuestion(Entity qEnt) {
		this.setCorrectAnswer(qEnt.getProperty("correct_answer").toString());
		this.setQuestionText(qEnt.getProperty("question_text").toString());
		this.setPoints((long) qEnt.getProperty("points"));
		this.setTime(new Long((long) qEnt.getProperty("time")).intValue());
		this.setHasImageQuestion((boolean)qEnt.getProperty("has_image_question"));
        this.setImageKey(qEnt.getProperty("imageKey").toString());
        //this.setImageQuestion(((Blob)(qEnt.getProperty("image_question"))).getBytes());
        //this.setBlobImageQuestion((Blob)(qEnt.getProperty("image_question")));
		this.type = Type.OPEN;
	}
	
	public Entity toEntity() {

		Entity question = new Entity("question");
		question.setProperty("points", this.points);
		question.setProperty("correct_answer", this.correct_answer);
		question.setProperty("type", this.type.name());
		question.setProperty("question_text", this.question_text);
		question.setProperty("time", this.time);
		question.setProperty("has_image_question", this.hasImageQuestion);
		question.setProperty("imageKey", this.imageKey);
		//Blob blob = new Blob(this.imageQuestion);
		//imageBlobTest = new Blob(this.imageQuestion);
		//question.setProperty("image_question", blob);
		//question.setProperty("image_question", imageBlobTest);
		//question.setProperty("image_question", this.imageQuestion);

		return question;
	}
	
	public String toJSONString(){
    	Gson gson = new Gson();
    	String jsonstr = gson.toJson(this);
    	return jsonstr;
    }
	
	public String toPrettyJSONStringTest(){
		String finalResult = "";
		String presetTab = "				"; // four tabs
		String questionType = this.getType().name();
		String qText = this.question_text;
		String cAnswer = this.correct_answer;
		String qPoints = "" + this.points;
		String qTime = "" + this.time;
		finalResult = finalResult + presetTab + "type : \"" + questionType + "\"\n"; // question type
		finalResult = finalResult + presetTab + "question_text : \"" + qText + "\"\n"; // question text
		finalResult = finalResult + presetTab + "correct_answer : \"" + cAnswer + "\"\n"; // correct answer
		finalResult = finalResult + presetTab + "points : \"" + qPoints + "\"\n"; // points
		finalResult = finalResult + presetTab + "time : \"" + qTime + "\"\n"; // time
		return finalResult;
    }

}
