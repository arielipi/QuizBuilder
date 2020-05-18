package quiz_builder.site;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import quiz_builder.shared.Game;
import quiz_builder.shared.Level;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class DeleteLevel extends HttpServlet {
		
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			

		Game game = (Game)req.getSession().getAttribute("game");
		req.getSession().setAttribute("game", game);
		
		int lindex = Integer.parseInt(req.getParameter("lindex"));
		
		game.getLevels().remove(lindex);
		resp.sendRedirect("gamePage.jsp");
			
	}


		
}//End class
