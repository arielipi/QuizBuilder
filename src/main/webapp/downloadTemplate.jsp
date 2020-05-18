<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quiz_builder.site.LanguagesSupporter"%>
<html>

<%
	String language = (String) session.getAttribute("lang");
	session.setAttribute("lang", language);
%>
<head>
<link href="introjs.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=LanguagesSupporter.downloadTemplateTitle(language)%></title>

<script language="Javascript" type="text/javascript">
	var counter = 1;
	var rowCounter = 4;

	var alertFillGameName = <%="'" + LanguagesSupporter.downloadTemplateAlertFillGameName(language) + "'"%>;
	var alertFillLevelName = <%="'" + LanguagesSupporter.downloadTemplateAlertFillLevelName(language) + "'"%>;
	var alertFillNumberOfOpenQuestions = <%="'" + LanguagesSupporter.downloadTemplateAlertFillNumberOfOpenQuestions(language) + "'"%>;
	var alertFillNumberOfAmericanQuestions = <%="'" + LanguagesSupporter.downloadTemplateAlertFillNumberOfAmericanQuestions(language) + "'"%>;
	
	function addLevel() {
		counter++;
		var newdiv = document.createElement('div');
		newdiv.innerHTML = "<div id='levelDIV" + counter +"'>"
							+ "<div class='form-group'>"
							+ "<label class='col-md-3 control-label'>"
							+ "<%=LanguagesSupporter.downloadTemplateLevelName(language)%>" + "</label>"
							+ "<div class='col-md-9'>"
							+ "<input class='form-control' id='level" + counter + "' type='text' name='level" + counter + "' >"
							+ "</div></div>"
							+ "<div class='form-group'>"
							+ "<label class='col-md-3 control-label'>"
							+ "<%=LanguagesSupporter.downloadTemplateOpenQuestions(language)%>"
							+ "</label>"
							+ "<div class='col-md-3'>"
							+ "	<input class='form-control' id='openQuestionsNumberLevel" + counter + "'type='text' name='openQuestionsNumberLevel" + counter + "'>"
							+ "</div>"
							+ "<label class='col-md-3 control-label'>"
							+ "<%=LanguagesSupporter.downloadTemplateAmericanQuestions(language)%>"
							+ "</label>"
							+ "<div class='col-md-3'>"
							+ "	<input class='form-control' id='americanQuestionsNumberLevel" + counter + "' type='text' name='americanQuestionsNumberLevel" + counter + "'>"
							+ "	</div></div></div>";
		var incoDiv = document.getElementById('levels');
		incoDiv.appendChild(newdiv);
		rowCounter++;
	}

	function removeLevel() {
		if (counter > 1) {
			var name = "levelDIV" + counter;
			var element = document.getElementById(name);
			element.parentNode.removeChild(element);
			counter--;
			rowCounter--;
		}
	}

	function checkInput() {
		var i = 2;
		var openQValue = parseInt(document
				.getElementById("openQuestionsNumberLevel1").value, 10);
		var americanQValue = parseInt(document
				.getElementById("americanQuestionsNumberLevel1").value, 10);
		if (document.getElementById("game_name").value.length == 0) {
			setAlert(alertFillGameName);
			return false;
		}
		if (document.getElementById("level1").value.length == 0) {
			setAlert(alertFillLevelName);
			return false;
		}
		if (document.getElementById("openQuestionsNumberLevel1").value.length == 0) {
			setAlert(alertFillNumberOfOpenQuestions);
			return false;
		}
		if (document.getElementById("americanQuestionsNumberLevel1").value.length == 0) {
			setAlert(alertFillNumberOfAmericanQuestions);
			return false;
		}
		if (openQValue < 0) {
			setAlert(alertFillNumberOfOpenQuestions);
			return false;
		}
		if (americanQValue < 0) {
			setAlert(alertFillNumberOfAmericanQuestions);
			return false;
		}
		if (rowCounter == 4) {
			return true;
		} else {
			var localCounter = rowCounter;
			while (localCounter > 4) {
				var levelName = "level" + i;
				var localOpen = "openQuestionsNumberLevel" + i;
				var localAmerican = "americanQuestionsNumberLevel" + i;
				openQValue = parseInt(document.getElementById(localOpen).value,10);
				americanQValue = parseInt(document
						.getElementById(localAmerican).value, 10);
				if (document.getElementById(levelName).value.length == 0) {
					setAlert(alertFillLevelName);
					return false;
				}
				if (document.getElementById(localOpen).value.length == 0) {
					setAlert(alertFillNumberOfOpenQuestions);
					return false;
				}
				if (document.getElementById(localAmerican).value.length == 0) {
					setAlert(alertFillNumberOfAmericanQuestions);
					return false;
				}
				if (openQValue < 0) {
					setAlert(alertFillNumberOfOpenQuestions);
					return false;
				}
				if (americanQValue < 0) {
					setAlert(alertFillNumberOfAmericanQuestions);
					return false;
				}
				i++;
				localCounter--;
			}
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
				<H1><%=LanguagesSupporter.downloadTemplateHeader(language)%>:
				</H1>
			</div>
			<div class="col-md-3"></div>
		</div>

		<div class="row">

			<div class="col-md-3"></div>
			<div class="col-md-6">

				<form class="form-horizontal" action="DownloadTemplate"
					onsubmit="return checkInput();" target="_blank" method="get">


					<div class="form-group">
						<label class="col-md-3 control-label"><%=LanguagesSupporter.downloadTemplateGameName(language)%></label>
						<div class="col-md-9">
							<input class="form-control" id="game_name" type="text"
								name="game_name" data-step="1"
								data-intro="<%=LanguagesSupporter.downloadTemplateStepOne(language)%>">
						</div>
					</div>
					<div id="levels">
						<div id="levelDIV1">
							<div class="form-group">
									<label class="col-md-3 control-label"><%=LanguagesSupporter.downloadTemplateLevelName(language)%></label>
									<div class="col-md-9">
										<input class="form-control" id="level1" type="text"
											name="level1" data-step="2"
											data-intro="<%=LanguagesSupporter.downloadTemplateStepTwo(language)%>">
									</div>
							</div>
							<div class="form-group">
								<label class="col-md-3 control-label"><%=LanguagesSupporter
					.downloadTemplateOpenQuestions(language)%></label>
								<div class="col-md-3">
									<input class="form-control" id="openQuestionsNumberLevel1"
										type="text" name="openQuestionsNumberLevel1" data-step="5"
										data-intro="<%=LanguagesSupporter.downloadTemplateStepFive(language)%>">
								</div>

								<label class="col-md-3 control-label"><%=LanguagesSupporter
					.downloadTemplateAmericanQuestions(language)%></label>
								<div class="col-md-3">
									<input class="form-control" id="americanQuestionsNumberLevel1"
										type="text" name="americanQuestionsNumberLevel1" data-step="6"
										data-intro="<%=LanguagesSupporter.downloadTemplateStepSix(language)%>">
								</div>
							</div>
						</div>

					</div>
					<div class="form-group">
						<div class="col-md-offset-3 col-md-9">

							<a class="btn btn-defualt btn-lg" href="#" onclick="addLevel();"
								data-step="3"
								data-intro="<%=LanguagesSupporter.downloadTemplateStepThree(language)%>">
								<span class="glyphicon glyphicon-plus"></span>
							</a> <a class="btn btn-defualt btn-lg" href="#"
								onclick="removeLevel();" data-step="4"
								data-intro="<%=LanguagesSupporter.downloadTemplateStepFour(language)%>">
								<span class="glyphicon glyphicon-minus"></span>
							</a>

						</div>
					</div>
					<div id="alertDiv"></div>

					<div class="form-group">
						<div class="col-md-offset-3 col-md-9">
							<button type="submit" class="btn btn-success">
								<%=LanguagesSupporter.generalSubmit(language)%>
							</button>

							<a class="btn btn-info" href="gamesList.jsp"> <%=LanguagesSupporter.gameStatsBackToMainPage(language)%>
							</a>
						</div>
					</div>
				</form>

			</div>
			<div class="col-md-3"></div>
		</div>

	</div>
</body>
</html>