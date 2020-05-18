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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
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
public class EditQuestion extends HttpServlet {
	private final static Logger log = Logger.getLogger(EditQuestion.class.getName());
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Game game = (Game)req.getSession().getAttribute("game");
		req.getSession().setAttribute("game", game);
		
		int qindex = Integer.parseInt(req.getParameter("qindex"));
		int lindex = Integer.parseInt(req.getParameter("lindex"));
		
		Question question = game.getLevels().get(lindex).getQuestions().get(qindex);
		//byte[] imageByteArray = question.getImageQuestion();
		req.setAttribute("question", question);
		//String decoded = new String(question.getImageQuestion(), "UTF-8");
		//String decoded = new String(question.getImageQuestion());
		//req.setAttribute("imageName2", SampleImage.sampleImage);
		//req.setAttribute("imageName2", decoded);
		req.setAttribute("qindex", qindex);
		req.setAttribute("lindex", lindex);
		
		
		
		req.getRequestDispatcher(Util.getEditPageByType(question.getType())).forward(req,  resp);;
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Game game = (Game)req.getSession().getAttribute("game");
		req.getSession().setAttribute("game", game);
		int qindex = 0;
		int lindex = 0;
		int incorrectAnswersImageCounter = 0;
		int leftOvers = 0;
		Level level = null;
		Question question = null;
		String imageKey = "";
		String correctAnswerImage = "";
		List<String> incorrectAnswersImage = new ArrayList<String>();
		List<String> incorrectAnswersImageLeftOversCleaner = new ArrayList<String>();
		String deleteQuestionImage = "";
		byte[] b = null;
		boolean hadOldImage = false;
		boolean hasOldImageRemoved = false;
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
					if(fieldname.equals("question_text")) {
						question.setQuestionText(fieldvalue);
					} else if(fieldname.equals("correct_answer")) {
						question.setCorrectAnswer(fieldvalue);
					} else if(fieldname.equals("has_image_question")) {
						question.setHasImageQuestion(Boolean.parseBoolean(fieldvalue));
					} else if(fieldname.equals("has_question_stored_image")) {
						deleteQuestionImage = fieldvalue;
						if(deleteQuestionImage.equals("true")) {
							if(hasOldImageRemoved == false) {
								question.setImageKey("");
		            			question.setHasImageQuestion(false);
		            			try {
									Image image = new Image(imageKey);
									image.deleteFromDS();
								} catch (EntityNotFoundException e) {
									e.printStackTrace();
								}
							}
						}
					} else if(fieldname.startsWith("incorrect") && !fieldname.startsWith("incorrecti")) {
						((AmericanQuestion)question).addIncorrectAnswer(fieldvalue);
					} else if(fieldname.equals("points")) {
						question.setPoints(Integer.parseInt(fieldvalue));
					} else if(fieldname.equals("time")) {
						question.setTime(Integer.parseInt(fieldvalue));
					} else if(fieldname.equals("lindex")) {
						lindex = Integer.parseInt(fieldvalue);
						level = game.getLevels().get(lindex);
					} else if(fieldname.equals("qindex")) {
						qindex = Integer.parseInt(fieldvalue);
						imageKey = level.getQuestions().get(qindex).getImageKey();
						hadOldImage = level.getQuestions().get(qindex).getHasImageQuestion();
						if(level.getQuestions().get(qindex).getType().name().equals("AMERICANIMAGEQ")) {
							//log.warning("yes it is american!");
							correctAnswerImage = ((AmericanImageQ)level.getQuestions().get(qindex)).getCorrectAnswerImage();
							incorrectAnswersImage = ((AmericanImageQ)level.getQuestions().get(qindex)).getIncorrectAnswers();
							incorrectAnswersImageLeftOversCleaner = ((AmericanImageQ)level.getQuestions().get(qindex)).getIncorrectAnswers();
							//log.warning("size is" + incorrectAnswersImageLeftOversCleaner.size());
						}
						level.getQuestions().remove(qindex);
					} else if(fieldname.equals("type")) {
						if(fieldvalue.equals("AMERICAN")) {
							question = new AmericanQuestion();
						} else if(fieldvalue.equals("OPEN")) {
							question = new OpenQuestion();
						} else if(fieldvalue.equals("AMERICANIMAGEQ")) {
							//log.warning("building a image q");
							question = new AmericanImageQ();
						}
					}
	            } else {
	            	// Process form file field (input type="file").
	            	String fieldname = item.getFieldName();
	            	String filename = FilenameUtils.getName(item.getName());
	            	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            	b = new byte[4096];
	            	int bytesRead = 0;
	            	while ((bytesRead = stream.read(b, 0, b.length)) != -1) {
	            		bos.write(b, 0, bytesRead);
	            	}
	            	
	            	/*if(fieldname.equals("image_question")) {
	            		if(filename.equals("")) {
	            			question.setImageQuestion(imageQuestion.getBytes());
	            			question.setBlobImageQuestion(imageQuestion);
	            		} else {
	            			question.setImageQuestion(b);
	            			Blob semiBlob = new Blob(question.getImageQuestion());
	            			question.setBlobImageQuestion(semiBlob);
	            			question.setHasImageQuestion(true);
	            		}
	            	}*/
	            	
	            	if(fieldname.equals("image_question")) {
	            		if(filename.equals("")) {
	            			question.setImageKey(imageKey);
	            			question.setHasImageQuestion(hadOldImage);
	            		} else {
	            			
	            			if (!imageKey.equals("")){
	            				try {
									Image image = new Image(imageKey);
									image.deleteFromDS();
								} catch (EntityNotFoundException e) {
									e.printStackTrace();
								}
	            			}
	            			
	            			Image image = new Image(bos.toByteArray());
	            			image.saveToDS();
	            			question.setImageKey(image.getStringKey());
	            			hasOldImageRemoved = true;
	            		}
	            	} else if(fieldname.startsWith("incorrecti")) {
	            		if(filename.equals("")) {
	            			if(incorrectAnswersImageCounter < incorrectAnswersImage.size()) {
	            				((AmericanImageQ)question).addIncorrectAnswerImage(
	            						incorrectAnswersImage.get(incorrectAnswersImageCounter));
	            				incorrectAnswersImageCounter++;
	            				//if(!incorrectAnswersImageLeftOversCleaner.isEmpty()) {
	            				//	incorrectAnswersImageLeftOversCleaner.;
	            				//}
	            			}
	            		} else {
	            			if(incorrectAnswersImageCounter < incorrectAnswersImage.size()) {
	            				if (!incorrectAnswersImage.get(incorrectAnswersImageCounter).equals("")) {
	            					try {
	            						Image image = new Image(incorrectAnswersImage.get(incorrectAnswersImageCounter));
	            						image.deleteFromDS();
	            						//if(!incorrectAnswersImageLeftOversCleaner.isEmpty()) {
	            						//	incorrectAnswersImageLeftOversCleaner.remove();
	            						//}
	            					} catch (EntityNotFoundException e) {
	            						e.printStackTrace();
	            					}
	            				}
	            			}
	            			
	            			Image image = new Image(bos.toByteArray());
	            			image.saveToDS();
	            			((AmericanImageQ)question).addIncorrectAnswerImage(image.getStringKey());
	            			incorrectAnswersImageCounter++;
	            		}
	            	} else if(fieldname.equals("correct_answer_replace")) {
	            		if(filename.equals("")) {
	            			((AmericanImageQ)question).setCorrectAnswerImage(correctAnswerImage);
	            		} else {
	            			if (!correctAnswerImage.equals("")) {
	            				try {
									Image image = new Image(correctAnswerImage);
									image.deleteFromDS();
								} catch (EntityNotFoundException e) {
									e.printStackTrace();
								}
	            			}
	            			
	            			Image image = new Image(bos.toByteArray());
	            			image.saveToDS();
	            			((AmericanImageQ)question).setCorrectAnswerImage(image.getStringKey());
	            		}
	            	}
	            }
	        }
	    } catch (FileUploadException e) {
	    }
		/*
		for(String iter : incorrectAnswersImageLeftOversCleaner) {
			try {
				Image image = new Image(iter);
				image.deleteFromDS();
			} catch (EntityNotFoundException e) {
				e.printStackTrace();
			}
		}*/
		
		
		
		
		level.getQuestions().add(qindex, question);
		
		req.getSession().setAttribute("game", game);
		
		//update the datastore
		//DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		//Entity gameEnt = (Entity) req.getSession().getAttribute("game_entity");
		//Entity levelEnt = (Entity) req.getSession().getAttribute("level_entity");
		//ds.delete(key);
		//gameEnt = game.toEntity(gameEnt);
		//ds.put(gameEnt);
		//req.getSession().setAttribute("game_entity", gameEnt);
		
		//req.getRequestDispatcher("gamePage.jsp").forward(req, resp);
		
		//levelEnt = level.toEntity();
		//ds.put(levelEnt);
		//req.getSession().setAttribute("level_entity", levelEnt);
		resp.sendRedirect("gamePage.jsp");
		
	}


	
	
}//End class
