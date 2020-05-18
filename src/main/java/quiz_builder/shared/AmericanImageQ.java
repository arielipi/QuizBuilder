package quiz_builder.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import quiz_builder.shared.Question.Type;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;

public class AmericanImageQ extends Question { //AmericanQuestion{

	//private List<byte[]> incorrect_answers_images = new ArrayList<byte[]>();
	//private byte[] correct_answer_image;
	protected List<String> incorrect_answers_keys = new ArrayList<String>();
	protected String correct_answer_image = "";

	public AmericanImageQ() {
		super(Type.AMERICANIMAGEQ);
	}

	public AmericanImageQ(Entity qEnt) {
		//TODO need to fix translation of images, use blob
		//this.setCorrectAnswer(qEnt.getProperty("correct_answer").toString());
		this.setQuestionText(qEnt.getProperty("question_text").toString());
		this.setPoints((long) qEnt.getProperty("points"));
		this.setTime(new Long((long) qEnt.getProperty("time")).intValue());
		String incorrects = qEnt.getProperty("incorrect_answers_keys").toString();
		this.incorrect_answers_keys = Arrays.asList(incorrects.split("\\|"));
		this.setHasImageQuestion((boolean)qEnt.getProperty("has_image_question"));
		this.setImageKey(qEnt.getProperty("imageKey").toString());
		this.setCorrectAnswerImage(qEnt.getProperty("correct_answer_image").toString());
        //this.setImageQuestion((byte[])qEnt.getProperty("image_question"));
		this.type = Type.AMERICANIMAGEQ;
	}
	
	public void setCorrectAnswerImage(String correct_answer_image) {
        this.correct_answer_image = correct_answer_image;
	}
	
	public String getCorrectAnswerImage() {
		return this.correct_answer_image;
	}

	public List<String> getIncorrectAnswers() {
		return incorrect_answers_keys;
	}
	
	public String getIncorrectAnswerByIndex(int index) {
		return this.incorrect_answers_keys.get(index);
	}

	public void addIncorrectAnswerImage(String incorrect_answer) {
		this.incorrect_answers_keys.add(incorrect_answer);
	}

	public void setIncorrectAnswersImage(List<String> incorrectAnswers) {
        this.incorrect_answers_keys = incorrectAnswers;
	}
	
	public int getIncorrectAnswersSize() {
		return this.incorrect_answers_keys.size();
	}
	
	// Create entity from this question.
	public Entity toEntity() {
		//TODO need to fix translation of images, use blob

		Entity question = new Entity("question");
		question.setProperty("question_text", this.question_text);
		question.setProperty("points", this.points);
		//question.setProperty("correct_answer", this.correct_answer);
		question.setProperty("correct_answer_image", this.correct_answer_image);
		question.setProperty("incorrect_answers_keys",
				StringUtils.join(this.incorrect_answers_keys, "|"));
		question.setProperty("type", this.type.name());
		question.setProperty("time", this.time);
		question.setProperty("has_image_question", this.hasImageQuestion);
		question.setProperty("imageKey", this.imageKey);
		//question.setProperty("image_question", this.imageQuestion);

		return question;
	}

	public String toJSONString(){
    	Gson gson = new Gson();
    	String jsonstr = gson.toJson(this);
    	return jsonstr;
    }
	
	public String toPrettyJSONStringTest() {
		return "";
	}
	
}
