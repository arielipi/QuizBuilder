package quiz_builder.site;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import quiz_builder.shared.Game;

public class SaveServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	

		Game game = (Game)req.getSession().getAttribute("game");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity gameEnt = game.toEntity();
		ds.put(gameEnt);
		
		req.getSession().setAttribute("game", game);
		
		resp.sendRedirect("gamePage.jsp");
	}
	
}
