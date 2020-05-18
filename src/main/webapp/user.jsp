
<%@ page import="quiz_builder.site.LanguagesSupporter"%>
<%@ page import="java.util.*, com.google.appengine.api.datastore.*, 
	com.google.appengine.api.users.UserService, com.google.appengine.api.users.UserServiceFactory
	,com.google.appengine.api.users.User" %>

<%
	String languageUser = "";
	if(session.getAttribute("lang") == null) {
		languageUser = "English";
	} else {
		languageUser = (String)session.getAttribute("lang");
	}
	session.setAttribute("lang", languageUser);
%>

<head>
	<meta charset="utf-8">  
	<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
</head>

<nav class="navbar navbar-default navbar-static-top navbar-fixed-top"
	role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="brand" href="/"> <img src="images/logo.png"
				width="100" height="50"></a>
				
				 
		</div>
		
		<div class = "navbar-left">
		
				<a class = "btn btn-info navbar-btn" href="http://1-dot-play-a-quiz.appspot.com/">Play a Quiz</a>
		
			<a class = "btn btn-info navbar-btn" href="Feedback.jsp"><%=LanguagesSupporter.feedback(languageUser)%></a>
			
			<a class = "btn btn-info navbar-btn" href="About.jsp"><%=LanguagesSupporter.about(languageUser)%></a>
		</div>
		
		<div class = "navbar-right">
		
			<%
				UserService userService = UserServiceFactory.getUserService();
				String thisURL = request.getRequestURL().toString();
				String gamesListURL = "gamesList.jsp";
		
				if (userService.isUserLoggedIn()) {
					User user = userService.getCurrentUser();
			%>
					<p class="navbar-text">
						User: <%= user.getNickname() %>. 
					</p>
					<a class = "btn btn-info navbar-btn" href=" <%=userService.createLogoutURL(gamesListURL)%>" ><%=LanguagesSupporter.logout(languageUser)%></a>
			<% 
					session.setAttribute("user", user);
				} else {
			%>
					<a class = "btn btn-info navbar-btn" href=" <%=userService.createLoginURL(thisURL)%>"><%=LanguagesSupporter.signIn(languageUser)%></a>
			<%
				}
			%>
			</div>
			<div class = "navbar-right">
		
			<form class="navbar-form navbar-right" method="post" action="/LanguageSetter">
				
					<select class="form-control" id="mySelect" name="mySelect" onchange="this.form.submit()">
						<option value="Default">Choose Language</option>
		  				<option value="English">English</option>
		  				<option value="Hebrew">Hebrew</option>
					</select>
					<input id="myURL" name="myURL" type="hidden" value="<%=thisURL%>">
				
			</form>
		</div>
		
		
		
		
	</div>
</nav>
