package quiz_builder.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;


public class AmericanQuestion extends Question {

	
	protected List<String> incorrect_answers = new ArrayList<String>();

	public AmericanQuestion() {
		super(Type.AMERICAN);
	}

	public AmericanQuestion(Entity qEnt) {
		this.setCorrectAnswer(qEnt.getProperty("correct_answer").toString());
		this.setQuestionText(qEnt.getProperty("question_text").toString());
		this.setPoints((long) qEnt.getProperty("points"));
		this.setTime(new Long((long) qEnt.getProperty("time")).intValue());
		String incorrects = qEnt.getProperty("incorrect_answers").toString();
		this.incorrect_answers = Arrays.asList(incorrects.split("\\|"));
		this.setHasImageQuestion((boolean)qEnt.getProperty("has_image_question"));
        this.setImageKey(qEnt.getProperty("imageKey").toString());
		//this.setImageQuestion(((Blob)(qEnt.getProperty("image_question"))).getBytes());
		//this.setBlobImageQuestion((Blob)(qEnt.getProperty("image_question")));
        //this.setImageQuestion((byte[])qEnt.getProperty("image_question"));
		//this.setBlobImageQuestion(((Blob)(qEnt.getProperty("image_question_blob"))));
		this.type = Type.AMERICAN;
	}

	public List<String> getIncorrect_answers() {
		return incorrect_answers;
	}

	public void addIncorrectAnswer(String answer) {
		this.incorrect_answers.add(answer);
	}

	public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrect_answers = incorrectAnswers;
	}
	
	// Create entity from this question.
	public Entity toEntity() {

		Entity question = new Entity("question");
		question.setProperty("question_text", this.question_text);
		question.setProperty("points", this.points);
		question.setProperty("correct_answer", this.correct_answer);
		question.setProperty("incorrect_answers",
				StringUtils.join(this.incorrect_answers, "|"));
		question.setProperty("type", this.type.name());
		question.setProperty("time", this.time);
		question.setProperty("has_image_question", this.hasImageQuestion);
		//Blob blob = new Blob(this.imageQuestion);
		//this.imageBlobTest = new Blob(this.imageQuestion);
		//imageBlobTest = new Blob(this.imageQuestion);
		//question.setProperty("image_question", blob);
		//question.setProperty("image_question", this.imageBlobTest);
		//question.setProperty("image_question", imageBlobTest);
		//question.setProperty("image_question", this.imageQuestion);
		question.setProperty("imageKey", this.imageKey);

		return question;
	}

	public String toJSONString(){
    	Gson gson = new Gson();
    	String jsonstr = gson.toJson(this);
    	return jsonstr;
    }
	
	public String toPrettyJSONStringTest() {
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
		finalResult = finalResult + presetTab + "incorrect_answers {\n";
		for(String incorrectAnswerToHandle : this.incorrect_answers) {
			finalResult = finalResult + presetTab + "	\"" + incorrectAnswerToHandle + "\"\n";
		}
		finalResult = finalResult + presetTab + "}\n";
		return finalResult;
    }
	
}
