package quiz_builder.site;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import quiz_builder.shared.Game;
import quiz_builder.shared.Image;
import quiz_builder.shared.Question;
import quiz_builder.shared.Util;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ImageServlet extends HttpServlet {
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Game game = (Game)request.getSession().getAttribute("game");
		//request.getSession().setAttribute("game", game);
		
		int qindex = Integer.parseInt(request.getParameter("qindex"));
		int lindex = Integer.parseInt(request.getParameter("lindex"));
		
		Question question = game.getLevels().get(lindex).getQuestions().get(qindex);
		byte[] imageByteArray = question.getBlobImageQuestion().getBytes();
		ByteArrayInputStream iStream = new ByteArrayInputStream(imageByteArray);
		int length = imageByteArray.length;
		
		response.setContentType("image/gif");
		response.setContentLength(length);
		
		ServletOutputStream oStream = response.getOutputStream();
		byte [] buffer = new byte[4096];//1024];
		int len;
		while ((len = iStream.read(buffer)) != -1) {
			oStream.write(buffer, 0, len);
		}
		iStream.close();
		
		oStream.flush();
		oStream.close();
		
		// And we're done. Just let the method return at this point.
	}*/
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	Util.addCORSHeaders(request, response);
    	
    	String strKey = request.getParameter("key");
    	Key key = KeyFactory.stringToKey(strKey);
    	
    	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    	
    	try {
			Entity imageEnt = ds.get(key);
			Image image = new Image(imageEnt);
			
			byte[] bytes = image.getBytes();
			
			int length = bytes.length;
			
			ByteArrayInputStream iStream = new ByteArrayInputStream(bytes);
			
			response.setContentType("image/gif");
			response.setContentLength(length);
			
			ServletOutputStream oStream = response.getOutputStream();
			byte [] buffer = new byte[4096];//1024];
			int len;
			while ((len = iStream.read(buffer)) != -1) {
				oStream.write(buffer, 0, len);
			}
			iStream.close();
			
			oStream.flush();
			oStream.close();
			
			
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}    	
    }
    

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
