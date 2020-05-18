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
package quiz_builder.site;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import quiz_builder.shared.AmericanImageQ;
import quiz_builder.shared.AmericanQuestion;
import quiz_builder.shared.Game;
import quiz_builder.shared.Image;
import quiz_builder.shared.Level;
import quiz_builder.shared.OpenQuestion;
import quiz_builder.shared.Question;
import quiz_builder.shared.Question.Type;
import quiz_builder.shared.Util;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class AddQuestion extends HttpServlet {	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//Question.Type type = Type.valueOf(req.getParameter("type"));
		Question.Type type = Type.valueOf(Question.Type.class, req.getParameter("type"));
		req.getRequestDispatcher(Util.getAddPageByType(type)).forward(req, resp);
		
	}
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//Question.Type type = Type.valueOf(req.getParameter("type"));
		/*
		Question.Type type = Type.valueOf(Question.Type.class, req.getParameter("type"));
		int lindex = Integer.parseInt(req.getParameter("lindex"));
		*/
		Question question = null;
		/*
		switch(type){
		case OPEN:
			//create a question from the parameters
			question = new OpenQuestion();
			break;
			
		case AMERICAN:
			question = new AmericanQuestion();
			Enumeration<String> params = req.getParameterNames();
			while (params.hasMoreElements()) { 
			    String param = params.nextElement();
			    if (param.startsWith("incorrect")){
			    	((AmericanQuestion)question).addIncorrectAnswer(req.getParameter(param));
			    }
			}
			break;
		case AMERICANIMAGEQ:
			question = new AmericanImageQ();
			Enumeration<String> paramsAIQ = req.getParameterNames();
			while (paramsAIQ.hasMoreElements()) { 
			    String param = paramsAIQ.nextElement();
			    if (param.startsWith("incorrect_answers_i")){
			    	((AmericanImageQ)question).addIncorrectAnswerImage((req.getParameter(param).getBytes()));
			    }
			    else if (param.startsWith("correct_answer_i")){
			    	((AmericanImageQ)question).setCorrectAnswerImage((req.getParameter(param).getBytes()));
			    }
			}
			break;
		}

		question.setQuestionText(req.getParameter("question_text"));
		question.setPoints(Integer.parseInt(req.getParameter("points")));
		question.setCorrectAnswer(req.getParameter("correct_answer"));
		question.setTime(Integer.parseInt(req.getParameter("time")));
		question.setHasImageQuestion(Boolean.parseBoolean(req.getParameter("has_image_question")));
		*/

		byte[] b = {};
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int lindex = 0;
		try {
			ServletFileUpload upload = new ServletFileUpload();
			FileItemIterator iter = upload.getItemIterator(req);
			while(iter.hasNext()) {
				FileItemStream item = iter.next();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					// Process regular form field (input type="text|radio|checkbox|etc", select, etc).
					String fieldname = item.getFieldName();
					String fieldvalue = IOUtils.toString(stream, "UTF-8");
					if(fieldname.equals("type")) {
						if(fieldvalue.equals("AMERICAN")) {
							question = new AmericanQuestion();
						} else if(fieldvalue.equals("OPEN")) {
							question = new OpenQuestion();
						} else if(fieldvalue.equals("AMERICANIMAGEQ")) {
							question = new AmericanImageQ();
						}
					} else if(fieldname.equals("question_text")) {
						question.setQuestionText(fieldvalue);
					} else if(fieldname.equals("correct_answer")) {
						question.setCorrectAnswer(fieldvalue);
					} else if(fieldname.equals("has_image_question")) {
						question.setHasImageQuestion(Boolean.parseBoolean(fieldvalue));
					} else if(fieldname.startsWith("incorrect") && !fieldname.startsWith("incorrecti")) {
						((AmericanQuestion)question).addIncorrectAnswer(fieldvalue);
					} else if(fieldname.equals("points")) {
						question.setPoints(Integer.parseInt(fieldvalue));
					} else if(fieldname.equals("time")) {
						question.setTime(Integer.parseInt(fieldvalue));
					} else if(fieldname.equals("lindex")) {
						lindex = Integer.parseInt(fieldvalue);
					}
	            } else {
	            	// Process form file field (input type="file").
	            	String fieldname = item.getFieldName();
	            	String filename = FilenameUtils.getName(item.getName());

	            	b = new byte[4096];
	            	int bytesRead = 0;
	            	bos = new ByteArrayOutputStream();
	            	while ((bytesRead = stream.read(b, 0, b.length)) != -1) {
	            		bos.write(b, 0, bytesRead);
	            	}
	            	
	            	if(fieldname.equals("correct_answer")) {
	            		Image image = new Image(bos.toByteArray());
	        			image.saveToDS();
	        			((AmericanImageQ)question).setCorrectAnswerImage(image.getStringKey());
	            	} else if(fieldname.startsWith("incorrecti")) {
	            		Image image = new Image(bos.toByteArray());
	        			image.saveToDS();
	        			((AmericanImageQ)question).addIncorrectAnswerImage(image.getStringKey());
	            	} else if(fieldname.equals("image_question")) {
	            		if(!filename.equals("")) {
	            			Image image = new Image(bos.toByteArray());
	            			image.saveToDS();
	            			question.setImageKey(image.getStringKey());
	            		}
	            	}
	            	
	            }
	        }
	    } catch (FileUploadException e) {
	    }
		
		/*if(question.getHasImageQuestion()){
			Image image = new Image(bos.toByteArray());
			//question.setImageKey(image.getStringKey());
			image.saveToDS();
			question.setImageKey(image.getStringKey());
		}*/
		
		//question.setImageQuestion(b);
		//Blob semiBlob = new Blob(question.getImageQuestion());
		//question.setBlobImageQuestion(semiBlob);
		
		//add the question to the session game
		Game game = (Game)req.getSession().getAttribute("game");
		//game.addQuestion(question);
		//req.getSession().setAttribute("game", game);
		
		Level level = game.getLevels().get(lindex);
		level.addQuestion(question);
		req.getSession().setAttribute("game", game);
		
		//update the datastore
		//DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		//Entity gameEnt = (Entity) req.getSession().getAttribute("game_entity");
		//Entity levelEnt = (Entity) req.getSession().getAttribute("level_entity");
		//ds.delete(key);
		//gameEnt = game.toEntity(gameEnt);
		//ds.put(gameEnt);
		//req.getSession().setAttribute("game_entity", gameEnt);
		
		/*Entity levelEnt = level.toEntity();
		//ds.put(levelEnt);
		gameEnt = game.toEntity();
		ds.put(gameEnt);
		req.getSession().setAttribute("game_entity", gameEnt);
		*/
		
		resp.sendRedirect("gamePage.jsp");
		
		
		
	}//End do post


	
}//End class
