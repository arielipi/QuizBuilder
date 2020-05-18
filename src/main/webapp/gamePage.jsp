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
	User user = (User)session.getAttribute("user");
	String language = (String)session.getAttribute("lang");
	session.setAttribute("lang", language);
	
	if (request.getParameter("key") != null){
		//get the game from the datastore
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		String strKey = request.getParameter("key");
		Key key = KeyFactory.stringToKey(strKey);
		gameEnt = ds.get(key);
		game = new Game(gameEnt);
	}
	else if (request.getParameter("gameName") != null && request.getParameter("public").equals("true") ){
		game = new Game(request.getParameter("gameName"), "public");
		gameEnt = game.toEntity();
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	}
	else if (request.getParameter("gameName") != null && !request.getParameter("public").equals("true")){
		game = new Game(request.getParameter("gameName"), user.getUserId());
		gameEnt = game.toEntity();
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	}
	else{
		game = (Game)session.getAttribute("game");
	}

	session.setAttribute("game", game);
%>

<head>
<link href="introjs.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=game.getName()%></title>

<script language="Javascript" type="text/javascript">

	var alertFillLevelName = <%="'" + LanguagesSupporter.downloadTemplateAlertFillLevelName(language) + "'"%>;
	function checkInput() {
		if (document.getElementById("levelName").value.length == 0) {
			setAlert(alertFillLevelName);
			return false;
		}
		return true;
	}
	
	function setAlert(text){
		var alertContainerDiv = document.getElementById("alertDiv");
		var alertDiv = document.createElement('div');
		alertDiv.className = "alert alert-danger";
		alertDiv.innerHTML = text;
		alertDiv.setAttribute("align","center");
		
		while (alertContainerDiv.firstChild) {
			alertContainerDiv.removeChild(alertContainerDiv.firstChild);
		}
		
		alertContainerDiv.appendChild(alertDiv);
	}

</script>


</head>

<jsp:include page="user.jsp" />

<body class="first">

	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">

				<a class="btn btn-large btn-success" href="javascript:void(0);"
					onclick="javascript:introJs().start();"><font size=4><%=LanguagesSupporter.gamesListGuide(language)%></font></a>
				<script type="text/javascript" src="intro.js"></script>

			</div>
			<div class="col-md-6">

				<H1 data-step="1"
					data-intro="<%=LanguagesSupporter.gamePageStepOne(language)%>">
					<%=LanguagesSupporter.gamePageGame(language)%>:
					<%=game.getName()%>
				</H1>
			</div>
			<div class="col-md-3"></div>
		</div>

		<div class="row">

			<div class="col-md-1"></div>

			<div class="col-md-10" align="center">

				<table class="games table table-hover table-bordered" data-step="6"
					data-intro="<%=LanguagesSupporter.gamePageStepSix(language)%>">

					<%
						int maxQs = 10;
											for (Level level: game.getLevels()) {
										if (level.getQuestions().size() >= maxQs){
											maxQs = level.getQuestions().size() + 1;
										}
											}
											
											int level_ind = 0;
											for (Level level: game.getLevels()) {
					%>

					<TR class="success">
						<TH class="games" colspan="9"
							style="width:<%=(maxQs-1)*(90/maxQs)%>%">
							<h3><%=level.getName()%></h3>
						</TH>

						<TH colspan="1" style="width:(90/<%=maxQs%>)%" align="center" valign="middle"
							valign="middle"><a class="btn btn-danger"
							href="DeleteLevel?lindex=<%=level_ind%>"> <span
								class="glyphicon glyphicon-remove"></span></a></TH>
					</TR>

					<TR>

						<%
							int question_ind=0;
														for (Question question: game.getLevels().get(level_ind).getQuestions()) {
						%>

						<TD colspan="1" style="width:(90/<%=maxQs%>)%">
							<div align="center">
								<a class="btn btn-info"
									href="EditQuestion?qindex=<%=question_ind%>&lindex=<%=level_ind%>">Q<%=question_ind+1%></a><br>
							</div>
							<br>
							<div align="center">
								<a class="btn btn-danger"
									href="DeleteQuestion?qindex=<%=question_ind%>&lindex=<%=level_ind%>">
									<span class="glyphicon glyphicon-remove"></span>
								</a>

							</div>
						</TD>

						<%
							question_ind++;
														} // end of question for
						%>

						<TD style="width:(90/<%=maxQs%>)%">
							<form method="get" action="AddQuestion">
								<input class="btn btn-success" type="submit" value="+"><br>
								<div class="radio">
									<input type="radio" checked="checked" name="type"
										value="<%=Question.Type.OPEN.name()%>"><%=LanguagesSupporter.gamePageOpen(language)%><br>
									<input type="radio" name="type"
										value="<%=Question.Type.AMERICAN.name()%>"><%=LanguagesSupporter.gamePageAmerican(language)%><br>
									<input type="radio" name="type"
										value="<%=Question.Type.AMERICANIMAGEQ.name()%>"><%=LanguagesSupporter.gamePageAmericanImage(language)%><br>
									<input type="hidden" name="lindex" value="<%=level_ind%>">
								</div>
							</form>
						</TD>

						<%
							for (int i=question_ind+1; i<maxQs; i++){
						%>

						<TD colspan="1" style="width:(90/<%=maxQs%>)%"></TD>

						<%
							}
						%>

					</TR>

					<%
						level_ind++;
											} // end of level for
					%>

					<TR class="games">
						<TD colspan="10">
							<form class="form-inline" method="get" action="AddLevel" onsubmit="return checkInput();"
								data-step="5"
								data-intro="<%=LanguagesSupporter.gamePageStepFive(language)%>">
								<input class="btn btn-success" type="submit"
									value="<%=LanguagesSupporter.gamePageAddLevel(language)%>">
								<input class="form-control" type="text" id="levelName" name="levelName">
							</form>
						</TD>
					</TR>

				</table>

			</div>
			<div class="col-md-1"></div>

		</div>

		<div class="row">

			<div class="col-md-2"></div>

			<div class="col-md-8" align="center">
				<div id="alertDiv"></div>
				<a class="btn btn-info" href="DownloadGame" target="_blank"	data-step="7"
					data-intro="<%=LanguagesSupporter.gamePageStepSeven(language)%>"><%=LanguagesSupporter.gamePageDownload(language)%></a>
				<a class="btn btn-success" href="Save" data-step="2"
					data-intro="<%=LanguagesSupporter.gamePageStepTwo(language)%>"><%=LanguagesSupporter.gamePageSave(language)%></a>
				<a class="btn btn-primary" href="gameStats.jsp" data-step="3"
					data-intro="<%=LanguagesSupporter.gamePageStepThree(language)%>"><%=LanguagesSupporter.gamePageViewStats(language)%></a>
				<a class="btn btn-primary" href="gamesList.jsp" data-step="4"
					data-intro="<%=LanguagesSupporter.gamePageStepFour(language)%>"><%=LanguagesSupporter.gameStatsBackToMainPage(language)%></a>

			</div>

			<div class="col-md-2"></div>


		</div>



	</div>
</body>
</html>
