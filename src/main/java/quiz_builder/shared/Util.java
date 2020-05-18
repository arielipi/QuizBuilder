package quiz_builder.shared;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	
	public static void addCORSHeaders(HttpServletRequest request, HttpServletResponse response){
		String url = request.getHeader("Origin");
		response.setHeader( "Access-Control-Allow-Origin", url );
    	response.setHeader("Access-Control-Allow-Credentials", "true" );
    	response.setHeader( "Access-Control-Allow-Methods", "POST, GET"); 
		//response.addHeader( "Access-Control-Allow-Origin", url );
    	//response.addHeader("Access-Control-Allow-Credentials", "true" );
    	//response.addHeader( "Access-Control-Allow-Methods", "POST, GET"); 
	}

	public static String getEditPageByType(Question.Type type){
		switch(type){
		case AMERICAN:
			return "editAmericanQuestion.jsp";
		case AMERICANIMAGEQ:
			return "editAmericanImageQ.jsp";
		case OPEN:
			return "editOpenQuestion.jsp";
		default:
			return "gamePage.jsp";
		}
	}
	
	public static String getAddPageByType(Question.Type type){
		switch(type){
		case AMERICAN:
			return "addAmericanQuestion.jsp";
		case AMERICANIMAGEQ:
			return "addAmericanImageQ.jsp";
		case OPEN:
			return "addOpenQuestion.jsp";
		default:
			return "gamePage.jsp";
		}
	}
}
