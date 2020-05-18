<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*"%>
<%@ page import="com.google.appengine.api.datastore.*"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="quiz_builder.site.LanguagesSupporter"%>

<%
	String language = "";
	if(session.getAttribute("lang") == null) {
		language = "English";
	} else {
		language = (String)session.getAttribute("lang");
	}
	session.setAttribute("lang", language);
	session.setAttribute("game", null);
%>

<html>
<head>
<link href="introjs.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<meta charset="utf-8">
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<title><%=LanguagesSupporter.getGamesListTitle(language)%></title>

<script language="Javascript" type="text/javascript">
	var alertFillGameName =
<%="'" + LanguagesSupporter.downloadTemplateAlertFillGameName(language) + "'"%>
	;
	var alertFillFile =
<%="'" + LanguagesSupporter.downloadTemplateAlertFillFile(language) + "'"%>
	;
	function checkInput(givenGameNameID) {
		if (document.getElementById(givenGameNameID).value.length == 0) {
			setAlert(alertFillGameName);
			return false;
		}
		return true;
	}
	function checkInputPrivate(givenGameNameID) {
		if (document.getElementById(givenGameNameID).value.length == 0) {
			setAlertPrivate(alertFillGameName);
			return false;
		}
		return true;
	}

	function checkFileInput(givenSrc) {
		if (document.getElementById(givenSrc).value == "") {
			setAlert(alertFillFile);
			return false;
		}
		return true;
	}
	function checkPrivateFileInput(givenSrc) {
		if (document.getElementById(givenSrc).value == "") {
			setAlertPrivate(alertFillFile);
			return false;
		}
		return true;
	}

	function setAlert(text) {
		var alertContainerDiv = document.getElementById("alertDiv");
		var alertDiv = document.createElement('div');
		alertDiv.className = "alert alert-danger";
		alertDiv.innerHTML = text;
		alertDiv.setAttribute("align", "center");

		while (alertContainerDiv.firstChild) {
			alertContainerDiv.removeChild(alertContainerDiv.firstChild);
		}
		alertContainerDiv.appendChild(alertDiv);
	}
	function setAlertPrivate(text) {
		var alertContainerDiv = document.getElementById("alertDivPrivate");
		var alertDiv = document.createElement('div');
		alertDiv.className = "alert alert-danger";
		alertDiv.innerHTML = text;
		alertDiv.setAttribute("align", "center");

		while (alertContainerDiv.firstChild) {
			alertContainerDiv.removeChild(alertContainerDiv.firstChild);
		}
		alertContainerDiv.appendChild(alertDiv);
	}
</script>

</head>


<jsp:include page="user.jsp" />


<body class="first">

	<%
		UserService userService = UserServiceFactory.getUserService();
	%>


	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3">
				<a class="btn btn-large btn-success" href="javascript:void(0);"
					onclick="javascript:introJs().start();"><font size=4><%=LanguagesSupporter.gamesListGuide(language)%></font></a>
				<script type="text/javascript" src="intro.js"></script>
			</div>
			<div class="col-md-6">
				<h1>
					<%=LanguagesSupporter.gamesListInfo(language)%>
				</h1>
			</div>
			<div class="col-md-3"></div>
		</div>

		<div class="row">
			<div class="col-md-1"></div>


			<div class="col-md-5">
				<h3 align="center">
					<strong><%=LanguagesSupporter.gamesListYourGames(language)%></strong>
				</h3>
				<table id="gamesListTablePrivateGames"
					class="table table-striped table-hover" data-step="3"
					data-intro="<%=LanguagesSupporter.gamesListStepThree(language)%>">
					<%
						if (userService.isUserLoggedIn()) {
												User user = userService.getCurrentUser();
												DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
												Query q = new Query("game");
												q.addProjection(new PropertyProjection("name", String.class));
												q.addFilter("userID", Query.FilterOperator.EQUAL, user.getUserId());
												//q.setKeysOnly();
												PreparedQuery games = ds.prepare(q);
												if (games.countEntities() == 0) {
					%>
					<tr>
						<td><%=LanguagesSupporter.gamesListYouHaveNoGames(language)%></td>
					</tr>
					<%
						} else {
													int i = 1;
													for(Entity game: games.asIterable()) {
														String name = (String)game.getProperty("name");
														String key = KeyFactory.keyToString(game.getKey());
					%>
					<TR>
						<TD></TD>
						<TD><a href="gamePage.jsp?key=<%=key%>"><%=name%></a></TD>
						<TD><a class="btn btn-info" href="gameStats.jsp?key=<%=key%>"><%=LanguagesSupporter.gamesListStats(language)%></a></TD>
						<TD><a class="btn btn-danger" href="DeleteGame?key=<%=key%>"
							onclick="return confirm('Are you sure?')"> <span
								class="glyphicon glyphicon-remove"></span></a>
						</TD>
						<TD>
							<a class="btn btn-success" href="http://play-a-quiz.appspot.com/game.jsp?<%=key%>">
								<%=LanguagesSupporter.play(language)%></span></a>
						</TD>
					</TR>
					<%
						++i;
													}
												}
											} else {
					%>
					<tr>
						<td><%=LanguagesSupporter.gamesListYouMustBeLogged(language)%></td>
					</tr>
					<%
						}
					%>
				</table>
				<%
					if (userService.isUserLoggedIn()) {
				%>
				<form class="form-horizontal" method=post
					onsubmit="return checkInputPrivate('privateGameName');"
					action=gamePage.jsp data-step="4"
					data-intro="<%=LanguagesSupporter.gamesListStepFour(language)%>">

					<div class="form-group">

						<div class="col-md-5">

							<input type="submit" class="form-control"
								value="<%=LanguagesSupporter.gamesListCreateNewGame(language)%>">
						</div>
						<div class="col-md-7">
							<input type="text" class="form-control" id="privateGameName"
								name="gameName" size="20"> <input type="hidden"
								name="public" value="false">
						</div>
					</div>
				</form>


				<!-- check the action and texts.... -->
				<form class="form-horizontal" method=post action="/ImportGame"
					onsubmit="return checkPrivateFileInput('privateFile');"
					enctype="multipart/form-data" data-step="2"
					data-intro="<%="INTRO"%>">
					<div class="form-group">

						<div class="col-md-3">
							<input type="submit" class="form-control"
								value="<%="Import game!"%>">
						</div>
						<div class="col-md-6">
							<input type="file" class="form-control" id="privateFile"
								name="gameName" size="20"> <input type="hidden"
								name="public" value="false">
						</div>

						<div class="col-md-3">

							<a class="btn btn-info" href="downloadTemplate.jsp" data-step="8"
								data-intro="<%=LanguagesSupporter.gamePageStepEight(language)%>"><%=LanguagesSupporter.gamePageDownloadTemplate(language)%></a>

						</div>
					</div>
				</form>
				<div id="alertDivPrivate"></div>
				<%
					}
				%>
			</div>

			<!-- PUBLIC GAMES SECTION -->

			<div class="col-md-5">
				<h3 align="center">
					<strong><%=LanguagesSupporter.gamesListPublicGames(language)%></strong>
				</h3>
				<table id="gamesListTablePublicGames"
					class="table table-striped table-hover" data-step="1"
					data-intro="<%=LanguagesSupporter.gamesListStepOne(language)%>">
					<%
						User user = userService.getCurrentUser();
											DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
											Query q = new Query("game");
											q.addProjection(new PropertyProjection("name", String.class));
											q.addFilter("userID", Query.FilterOperator.EQUAL, "public");
											//q.setKeysOnly();
											PreparedQuery games = ds.prepare(q);
											if (games.countEntities() == 0) {
					%>
					<tr>
						<td><%=LanguagesSupporter.gamesListNoPublicGames(language)%></td>
					</tr>
					<%
						} else {
												int i = 1;
												for(Entity game: games.asIterable()) {
													String name = (String)game.getProperty("name");
													String key = KeyFactory.keyToString(game.getKey());
					%>
					<TR>
						<TD></TD>
						<TD><a href="gamePage.jsp?key=<%=key%>"><%=name%></a></TD>
						<TD><a class="btn btn-info" href="gameStats.jsp?key=<%=key%>"><%=LanguagesSupporter.gamesListStats(language)%></a></TD>
						<TD><a class="btn btn-danger" href="DeleteGame?key=<%=key%>"
							onclick="return confirm('Are you sure?')"><span
								class="glyphicon glyphicon-remove"></span> </a></TD>
								<TD>
							<a class="btn btn-success" href="http://1-dot-play-a-quiz.appspot.com/game.jsp?<%=key%>">
								<%=LanguagesSupporter.play(language)%></span></a>
						</TD>
					</TR>
					<%
						++i;
												}
											}
					%>
				</table>
				<form class="form-horizontal" method=post action=gamePage.jsp
					onsubmit="return checkInput('publicGameName');" data-step="2"
					data-intro="<%=LanguagesSupporter.gamesListStepTwo(language)%>">

					<div class="form-group">

						<div class="col-md-5">

							<input type="submit" class="form-control"
								value="<%=LanguagesSupporter.gamesListCreateNewPublicGame(language)%>">
						</div>
						<div class="col-md-7">
							<input type="text" class="form-control" id="publicGameName"
								name="gameName" size="20"> <input type="hidden"
								name="public" value="true">
						</div>
					</div>
				</form>

				<!-- check the action and texts.... -->
				<form class="form-horizontal" method=post action="/ImportGame"
					onsubmit="return checkFileInput('publicFile');"
					enctype="multipart/form-data" data-step="2"
					data-intro="<%="INTRO"%>">
					<div class="form-group">

						<div class="col-md-3">

							<input type="submit" class="form-control"
								value="<%="Import game!"%>">
						</div>

						<div class="col-md-6">
							<input type="file" class="form-control" id="publicFile"
								name="gameName" size="20"> <input type="hidden"
								name="public" value="true">
						</div>

						<div class="col-md-3">

							<a class="btn btn-info" href="downloadTemplate.jsp" data-step="8"
								data-intro="<%=LanguagesSupporter.gamePageStepEight(language)%>"><%=LanguagesSupporter.gamePageDownloadTemplate(language)%></a>

						</div>
					</div>
				</form>
				<div id="alertDiv"></div>

			</div>



			<div class="col-md-1"></div>
		</div>
	</div>

</body>
</html>
