package quiz_builder.site;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import quiz_builder.shared.AmericanQuestion;
import quiz_builder.shared.Game;
import quiz_builder.shared.Level;
import quiz_builder.shared.OpenQuestion;
import quiz_builder.shared.Question;
import quiz_builder.shared.Question.Type;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")


public class AddLevel extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setCharacterEncoding("UTF-8");
		
		Game game = (Game)req.getSession().getAttribute("game");
		String lname = req.getParameter("levelName");
		Level level = new Level(lname);
		game.getLevels().add(level);
		req.getSession().setAttribute("game", game);
		
		
		resp.sendRedirect("gamePage.jsp");
	}
	
	
}
