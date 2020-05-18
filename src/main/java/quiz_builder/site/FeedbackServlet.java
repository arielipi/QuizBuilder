package quiz_builder.site;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FeedbackServlet extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String sourceFeedback = req.getParameter("feedbackSource");
        //String name = new String(req.getParameter("name").toString().getBytes("UTF-8"));
        String name = req.getParameter("name");
        //String description = new String(req.getParameter("description").toString().getBytes("UTF-8"));
        String description = req.getParameter("description");
        //String email = req.getParameter("email");
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
 
        String msgBody = name + "\n" + description;// + "\n" + email;
        Date myDate = new Date();
 
        try {
        	String encodingOptions = "text/html; charset=UTF-8";
            MimeMessage msg = new MimeMessage(session);
            msg.setHeader("Content-Type", encodingOptions);
            msg.setFrom(new InternetAddress(
            		//"your_admin@googlemail.com", "Your admin"));
            		"arielpinchover@gmail.com", "User Feedback"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    //"your_email@example.com", "Your name"));
            		"chessserver777@gmail.com", "QB Support Team"));
            msg.setSubject("Feedback from " + name + " " +  myDate.getTime());
            msg.setText(msgBody, "UTF-8");
            Transport.send(msg);
 
        } catch (Exception e) {
            resp.setContentType("text/plain");
            resp.getWriter().println("Something went wrong. Please try again.");
            throw new RuntimeException(e);
        }
 
        resp.setContentType("text/plain");
        resp.getWriter().println(
                "Thank you for your feedback. An Email has been send out.");
        if(sourceFeedback.equals("quizBuilder")) {
        	resp.sendRedirect("gamesList.jsp");
        } else if(sourceFeedback.equals("playQuiz")) {
        	resp.sendRedirect("http://1.play-a-quiz.appspot.com/index.jsp");
        }
    }
}
