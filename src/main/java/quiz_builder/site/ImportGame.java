package quiz_builder.site;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import quiz_builder.shared.AmericanQuestion;
import quiz_builder.shared.Game;
import quiz_builder.shared.Level;
import quiz_builder.shared.OpenQuestion;

public class ImportGame extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		byte[] b = {};
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	String gameFile = "";
    	String publicity = "";
		int dummyInt = 0;
		try {
			ServletFileUpload upload = new ServletFileUpload();
			//resp.setContentType("text/plain");
			FileItemIterator iter = upload.getItemIterator(req);
			while(iter.hasNext()) {
				FileItemStream item = iter.next();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
					// Process regular form field (input type="text|radio|checkbox|etc", select, etc).
					String fieldname = item.getFieldName();
					String fieldvalue = IOUtils.toString(stream, "UTF-8");
					if(fieldname.equals("public")) {
						publicity = fieldvalue;
						if(publicity.equals("true")) {
							publicity = "public";
						} else if(publicity.equals("false")) {
							publicity = "private";
							UserService userService = UserServiceFactory.getUserService();
							User user = userService.getCurrentUser();
							publicity = user.getUserId();
						}
					}
//					if(fieldname.equals("type")) {
//						if(fieldvalue.equals("AMERICAN")) {
//							//question = new AmericanQuestion();
//						} else if(fieldvalue.equals("OPEN")) {
//							//question = new OpenQuestion();
//						} else if(fieldvalue.equals("AMERICANIMAGEQ")) {
//							//question = new AmericanImageQ();
//						}
//					} else if(fieldname.equals("question_text")) {
//						//question.setQuestionText(fieldvalue);
//					} else if(fieldname.equals("correct_answer")) {
//						//question.setCorrectAnswer(fieldvalue);
//					} else if(fieldname.equals("has_image_question")) {
//						//question.setHasImageQuestion(Boolean.parseBoolean(fieldvalue));
//					} else if(fieldname.startsWith("incorrect") && !fieldname.startsWith("incorrecti")) {
//						//((AmericanQuestion)question).addIncorrectAnswer(fieldvalue);
//					} else if(fieldname.equals("points")) {
//						//question.setPoints(Integer.parseInt(fieldvalue));
//					} else if(fieldname.equals("time")) {
//						//question.setTime(Integer.parseInt(fieldvalue));
//					} else if(fieldname.equals("lindex")) {
//						lindex = Integer.parseInt(fieldvalue);
//					}
	            } else {
	            	// Process form file field (input type="file").
	            	String fieldname = item.getFieldName();
	            	String filename = FilenameUtils.getName(item.getName());

	            	b = new byte[4096];
	            	int bytesRead = 0;
	            	bos = new ByteArrayOutputStream();
	            	while ((bytesRead = stream.read(b, 0, b.length)) != -1) {
	            		//resp.getOutputStream().write(b, 0, bytesRead);
	            		bos.write(b, 0, bytesRead);
	            	}
	            	gameFile = //(String)bos.toByteArray();
	            	new String(bos.toByteArray());
	            	
	            	//if(fieldname.equals("correct_answer")) {
	            		//Image image = new Image(bos.toByteArray());
	        			//image.saveToDS();
	        			//((AmericanImageQ)question).setCorrectAnswerImage(image.getStringKey());
	            	//} else if(fieldname.startsWith("incorrecti")) {
	            		//Image image = new Image(bos.toByteArray());
	        			//image.saveToDS();
	        			//((AmericanImageQ)question).addIncorrectAnswerImage(image.getStringKey());
	            	//} else if(fieldname.equals("image_question")) {
	            		//if(!filename.equals("")) {
	            		//	Image image = new Image(bos.toByteArray());
	            		//	image.saveToDS();
	            		//	question.setImageKey(image.getStringKey());
	            		//}
	            	//}
	            	
	            }
	        }
	    } catch (FileUploadException e) {
	    }
		
		String[] parseFile = gameFile.split("\n");
		String gameName = "";
		String levelName = "";
		String typeName = "";
		String questionText = "";
		String correctAnswer = "";
		String points = "";
		String time = "";
		String incorrectAnswers = "";
		
		Game tempGame = null;
		Level tempLevel = null;
		OpenQuestion tempOpenQuestion = null;
		AmericanQuestion tempAmericanQuestion = null;
		
		int questionTypeFlag = 0; // 0 = nothing, 1 = american, 2 = open
		
		for(String iter : parseFile) {
			
			if(iter.startsWith("GameName")) {
				// Game Name
				gameName = iter.substring(12);
				gameName = gameName.substring(0, gameName.length()-1);
				tempGame = new Game(gameName, publicity);
			}
			else if(iter.startsWith("		LevelName")) {
				// Level Name
				levelName = iter.substring(15);
				levelName = levelName.substring(0, levelName.length()-1);
				tempLevel = new Level(levelName);
			}
			else if(iter.startsWith("				type")) {
				// type
				typeName = iter.substring(12);
				typeName = typeName.substring(0, typeName.length()-1);
				if(typeName.equals("AMERICAN")) {
					tempAmericanQuestion = new AmericanQuestion();
					questionTypeFlag = 1;
				}
				else if(typeName.equals("OPEN")) {
					tempOpenQuestion = new OpenQuestion();
					questionTypeFlag = 2;
				}
			}
			else if(iter.startsWith("				question_text")) {
				// question text
				questionText = iter.substring(21);
				questionText = questionText.substring(0, questionText.length()-1);
				if(questionTypeFlag == 1) {
					tempAmericanQuestion.setQuestionText(questionText);
				}
				else if(questionTypeFlag == 2) {
					tempOpenQuestion.setQuestionText(questionText);
				}
			}
			else if(iter.startsWith("				correct_answer")) {
				// correct answer
				correctAnswer = iter.substring(22);
				correctAnswer = correctAnswer.substring(0, correctAnswer.length()-1);
				if(questionTypeFlag == 1) {
					tempAmericanQuestion.setCorrectAnswer(correctAnswer);
				}
				else if(questionTypeFlag == 2) {
					tempOpenQuestion.setCorrectAnswer(correctAnswer);
				}
			}
			else if(iter.startsWith("				points")) {
				// points
				points = iter.substring(14);
				points = points.substring(0, points.length()-1);
				if(questionTypeFlag == 1) {
					tempAmericanQuestion.setPoints(Long.valueOf(points).longValue());
				}
				else if(questionTypeFlag == 2) {
					tempOpenQuestion.setPoints(Long.valueOf(points).longValue());
				}
			}
			else if(iter.startsWith("				time")) {
				// time
				time = iter.substring(12);
				time = time.substring(0, time.length()-1);
				if(questionTypeFlag == 1) {
					tempAmericanQuestion.setTime(Integer.valueOf(time).intValue());
				}
				else if(questionTypeFlag == 2) {
					tempOpenQuestion.setTime(Integer.valueOf(time).intValue());
				}
			}
			else if(iter.startsWith("					\"")) {
				// incorrect answer
				incorrectAnswers = iter.substring(6);
				incorrectAnswers = incorrectAnswers.substring(0, incorrectAnswers.length()-1);
				if(questionTypeFlag == 1) {
					tempAmericanQuestion.addIncorrectAnswer(incorrectAnswers);
				}
				else if(questionTypeFlag == 2) {
					;
				}
			}
			else if(iter.startsWith("			}")) {
				// add question to level
				if(questionTypeFlag == 1) {
					tempLevel.addQuestion(tempAmericanQuestion);
				}
				else if(questionTypeFlag == 2) {
					tempLevel.addQuestion(tempOpenQuestion);
				}
				questionTypeFlag = 0;
			}
			else if(iter.startsWith("	}")) {
				// add level to game
				tempGame.getLevels().add(tempLevel);
			}
		}
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity gameEnt = tempGame.toEntity();
		ds.put(gameEnt);
		
		req.getSession().setAttribute("game", tempGame);
		
		resp.sendRedirect("gamePage.jsp");
	}
	
}
