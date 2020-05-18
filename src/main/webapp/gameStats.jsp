<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="com.google.appengine.api.datastore.*"%>
<%@ page import="quiz_builder.shared.*"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="quiz_builder.shared.Util"%>
<%@ page import="quiz_builder.site.LanguagesSupporter"%>
<html>

<%
	Game game = null;
	Entity gameEnt = null;
	String language = (String) session.getAttribute("lang");
	session.setAttribute("lang", language);
	if (request.getParameter("key") != null) {
		//get the game from the datastore
		DatastoreService ds = DatastoreServiceFactory
				.getDatastoreService();
		String strKey = request.getParameter("key");
		Key key = KeyFactory.stringToKey(strKey);
		gameEnt = ds.get(key);
		game = new Game(gameEnt);
	} else {
		game = (Game) session.getAttribute("game");
	}

	session.setAttribute("game", game);
%>

<head>
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=game.getName()%></title>
</head>

<jsp:include page="user.jsp" />

<body class="first">

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<H1>
					<%=LanguagesSupporter.gameStatsStatsFor(language)%><%=game.getName()%>:</font>
				</H1>
			</div>
			<div class="col-md-2"></div>
		</div>

		<div class="row">

			<div class="col-md-3"></div>

			<div class="col-md-6">

				<table class="table table-striped table-hover table-border">
					<tr>
						<th><%=LanguagesSupporter.gameStatsGameStarts(language)%></th>
						<td><%=game.getStarted_count()%>
					</tr>

					<tr>
						<th><%=LanguagesSupporter.gameStatsGameCompletions(language)%></th>
						<td><%=game.getCompleted_count()%>
					</tr>

					<tr>
						<th><%=LanguagesSupporter.gameStatsAverageScore(language)%></th>
						<td><%=game.getAverage_score()%>
					</tr>

				</table>

			</div>

			<div class="col-md-3"></div>
		</div>
		
		<div class="row">

			<div class="col-md-3"></div>
			<div class="col-md-6" align = "center">
				<a class="btn btn-success" href="gamePage.jsp"><%=LanguagesSupporter.gameStatsEditGame(language)%></a>
				<a class="btn btn-success" href="gamesList.jsp"><%=LanguagesSupporter.gameStatsBackToMainPage(language)%></a>
			</div>
			<div class="col-md-3"></div>
		</div>
		

	</div>
</body>
</html>
