package quiz_builder.site;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownloadTemplate extends HttpServlet {
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String oneTab    = "	";
		String twoTabs   = "		";
		String threeTabs = "			";
		String fourTabs  = "				";
		String fiveTabs  = "					";
		
		int levelCounter = 1;
		int openQNumber = 0; //openQuestionsNumberLevel
		int americanQNumber = 0; //americanQuestionsNumberLevel
		
		String finalString = "";
		String gameName = req.getParameter("game_name");
		
		finalString = finalString + "GameName : \"" + gameName + "\"\n"; // GameName : "..."
		finalString = finalString + "levels {\n"; // levels {
		boolean endFlag = false;
		
		
		while(!endFlag) {
			String levelName = req.getParameter("level" + levelCounter);
			if(levelName == null) {
				endFlag = true;
			} else if(levelName.equals("")) {
				endFlag = true;
			} else {
				openQNumber = Integer.parseInt(req.getParameter("openQuestionsNumberLevel" + levelCounter));
				americanQNumber = Integer.parseInt(req.getParameter("americanQuestionsNumberLevel" + levelCounter));
				
				//one tab
				finalString = finalString + oneTab + "{\n"; // A
				
				//two tabs
				finalString = finalString + twoTabs + "LevelName : \"" + levelName + "\"\n"; // LevelName : "..."
				finalString = finalString + twoTabs + "questions {\n"; // questions {
				
				for(int i = 0; i < openQNumber; i++) {
					//three tabs
					finalString = finalString + threeTabs + "{\n"; // B
					
					finalString = finalString + fourTabs + "type : \"OPEN\"\n";
					finalString = finalString + fourTabs + "question_text : \"\"\n";
					finalString = finalString + fourTabs + "correct_answer : \"\"\n";
					finalString = finalString + fourTabs + "points : \"\"\n";
					finalString = finalString + fourTabs + "time : \"\"\n";
					
					finalString = finalString + threeTabs + "}\n"; // close B
				}
				for(int i = 0; i < americanQNumber; i++) {
					//three tabs
					finalString = finalString + threeTabs + "{\n"; // B
					
					finalString = finalString + fourTabs + "type : \"AMERICAN\"\n";
					finalString = finalString + fourTabs + "question_text : \"\"\n";
					finalString = finalString + fourTabs + "correct_answer : \"\"\n";
					finalString = finalString + fourTabs + "points : \"\"\n";
					finalString = finalString + fourTabs + "time : \"\"\n";
					finalString = finalString + fourTabs + "incorrect_answers {\n"; // C
					finalString = finalString + fiveTabs + "\"\"\n";
					finalString = finalString + fourTabs + "}\n"; // close C
					
					finalString = finalString + threeTabs + "}\n"; // close B
				}
				
				finalString = finalString + twoTabs + "}\n"; // close questions {
				
				finalString = finalString + oneTab + "}\n"; // close A
				
				levelCounter++;
				//finalString = finalString + levelName + openQNumber + americanQNumber;
			}
			//openQNumber = Integer.parseInt(req.getParameter("openQuestionsNumberLevel" + levelCounter));
			//americanQNumber = Integer.parseInt(req.getParameter("americanQuestionsNumberLevel" + levelCounter));
			//levelCounter++;
			//if(levelName.equals("")) {
			//	endFlag = true;
			//} else {
			//	finalString = finalString + levelName + openQNumber + americanQNumber;
			//}
		}
		finalString = finalString + "}"; // close levels {
		resp.setHeader("Content-disposition", "attachment; filename="+gameName+".json");
		resp.setContentType("application/json");
		byte[] toReturn = finalString.getBytes("UTF8");
		resp.getOutputStream().write(toReturn, 0, toReturn.length);
		
    }
	
	/** example of a format
	 *  GameName : "Recognize The"
		levels {
			{ A
				LevelName : "Shape"
				questions {
					{ B
						type : "AMERICAN"
						question_text : "A square is a shape with"
						correct_answer : "Four edges"
						points : "10"
						time : "0"
						incorrect_answers { C
							"Three edges"
							"Five edges"
							"No edges"
						}
					} close B
					{
						type : "OPEN"
						question_text : "another test"
						correct_answer : "Yest"
						points : "10"
						time : "0"
					}
					{
						type : "OPEN"
						question_text : "test new design level one"
						correct_answer : "yes"
						points : "10"
						time : "0"
					}
				}
			} close A
			{
				LevelName : "Color"
				questions {
					{
						type : "AMERICAN"
						question_text : "Israel's flag's colors are"
						correct_answer : "Blue and white"
						points : "10"
						time : "0"
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
						points : "10"
						time : "0"
					}
				}
			}
		}
	 */
}
