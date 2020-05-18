package quiz_builder.site;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageSetter extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
		String thisURL = req.getParameter("myURL");
		String languageToSet = req.getParameter("mySelect");
		req.getSession().setAttribute("lang", languageToSet);
		resp.sendRedirect(thisURL);
	}

}
