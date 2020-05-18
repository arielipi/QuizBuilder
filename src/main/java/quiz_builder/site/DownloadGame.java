package quiz_builder.site;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quiz_builder.shared.Game;

public class DownloadGame extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileFormat = ".aypk"; // .json
		Game game = (Game)req.getSession().getAttribute("game");
		Game gameDownload = game.prepareDownload();
		resp.setHeader("Content-disposition", "attachment; filename="+gameDownload.getName()+fileFormat);
		// works resp.setHeader("Content-disposition", "attachment; filename="+gameDownload.getName()+".txt");
		resp.setContentType("application/json"); // x-json ?
		//byte[] toReturn = gameDownload.toJSONString().getBytes("UTF8");
		byte[] toReturn = gameDownload.toPrettyJSONStringTest().getBytes("UTF8");
		resp.getOutputStream().write(toReturn, 0, toReturn.length);
		//ServletOutputStream out = resp.getOutputStream();
		
		//req.getSession().setAttribute("game", game);
		
		//resp.sendRedirect("gamePage.jsp");
	}
	
}