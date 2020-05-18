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

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;


public abstract class Question implements Serializable{

	
	public enum Type {AMERICAN, OPEN, AMERICANIMAGEQ};
	
	protected Type type;
	protected String question_text = null;
    protected String correct_answer = null;
    protected long points = 0;
    protected int time = 0;
    protected boolean hasImageQuestion = false;
    //protected byte[] imageQuestion = null;
    //protected Blob imageBlobTest = null;
    protected String imageKey = "";
    
    public Question()
    {}

    public Question(Type type) {
    	this.type=type;
    }
   
    //Create question from entity.
    public Question(Entity qEnt) {
        this.setCorrectAnswer(qEnt.getProperty("correct_answer").toString());
        this.setQuestionText(qEnt.getProperty("question_text").toString());
        this.setPoints((long)qEnt.getProperty("points"));
        this.setTime((int)qEnt.getProperty("time"));
        this.setHasImageQuestion((boolean)qEnt.getProperty("has_image_question"));
        //this.setImageQuestion(((Blob)(qEnt.getProperty("image_question"))).getBytes());//((byte[])qEnt.getProperty("image_question"));
        //this.setBlobImageQuestion((Blob)(qEnt.getProperty("image_question")));
        //this.setImageQuestion((byte[])qEnt.getProperty("image_question"));
        this.setImageKey(qEnt.getProperty("imageKey").toString());
        this.type=Type.OPEN;
    }

    public Type getType() {
		return type;
    }

	public void setType(Type type) {
		this.type = type;
	}

	public String getQuestion_text() {
		return question_text;
	}
 
	public String getCorrect_answer() {
		return correct_answer;
	}

		

	public long getPoints() {
		return points;
	}

	public void setCorrectAnswer(String answer) {
            this.correct_answer = answer;
    }

    public void setQuestionText(String text) {
            this.question_text = text;
    }

    public void setPoints(long points) {
            this.points = points;
    }
    
    public int getTime() {
		return time;
    }

	public void setTime(int time) {
		this.time = time;
	}
	
	public void setHasImageQuestion(boolean hasImageQuestion) {
		this.hasImageQuestion = hasImageQuestion;
	}
	
	public boolean getHasImageQuestion() {
		return hasImageQuestion;
    }
	
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	
	/*public void setBlobImageQuestion(Blob blobQuestion) {
		this.imageBlobTest = blobQuestion;
	}*/
	
	public String getImageKey() {
		return imageKey;
    }
	
	/*public Blob getBlobImageQuestion() {
		return imageBlobTest;
    }
	
	public String getImageQuestionAsString() {
		return imageQuestion.toString();
	}
	
	public byte[] getImageQuestionBlob() {
		return imageBlobTest.getBytes();
    }
	
	public String getImageQuestionAsStringBlob() {
		return imageBlobTest.getBytes().toString();
	}*/
	
	public String getDisplayBlockNone() {
		if(this.hasImageQuestion == true) {
			return "block";
		} else {
			return "none";
		}
	}

       

    //Create entity from this question.
    public abstract Entity toEntity() ;
    
    public abstract String toJSONString();//{
    	//Gson gson = new Gson();
    	//String jsonstr = gson.toJson(this);
    	//return jsonstr;
    //}
    
    public abstract String toPrettyJSONStringTest();

}

