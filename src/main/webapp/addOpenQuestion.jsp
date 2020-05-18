<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quiz_builder.shared.Question"%>
<%@ page import="quiz_builder.site.LanguagesSupporter"%>
<html>
<%
	int lindex = Integer.parseInt(request.getParameter("lindex"));
	String language = (String) session.getAttribute("lang");
	session.setAttribute("lang", language);
%>
<head>
<link href="introjs.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=LanguagesSupporter.addQuestion(language)%></title>

<script language="Javascript" type="text/javascript">
	var alertFillQuestionText =
<%="'" + LanguagesSupporter.addQuestionFillQText(language)
					+ "'"%>
	;
	var alertFillCorrectAnswer =
<%="'"
					+ LanguagesSupporter.addQuestionFillCorrectAnswer(language)
					+ "'"%>
	;
	var alertFillPoints =
<%="'" + LanguagesSupporter.addQuestionFillPoints(language)
					+ "'"%>
	;
	var alertFillTime =
<%="'" + LanguagesSupporter.addQuestionFillTime(language)
					+ "'"%>
	;

	function checkInput() {
		var timeValue = parseInt(document.getElementById("time").value, 10);
		var pointsValue = parseInt(document.getElementById("points").value, 10);
		if (document.getElementById("question_text").value.length == 0) {
			setAlert(alertFillQuestionText);
			return false;
		}
		if (document.getElementById("correct_answer").value.length == 0) {
			setAlert(alertFillCorrectAnswer);
			return false;
		}
		if (document.getElementById("points").value.length == 0) {
			setAlert(alertFillPoints);
			return false;
		}
		if (pointsValue < 0) {
			setAlert(alertFillPoints);
			return false;
		}
		if (document.getElementById("image_question").value == "") {
			document.getElementById("has_image_question").value = false;
		} else {
			document.getElementById("has_image_question").value = true;
		}
		if (timeValue < 0) {
			setAlert(alertFillTime);
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

	function yesnoCheck() {
		if (document.getElementById("yesCheck").checked) {
			document.getElementById("ifYes").style.display = "block";
		} else
			document.getElementById("ifYes").style.display = "none";

	}

	function imageCheck() {
		if (document.getElementById("yesImageCheck").checked) {
			document.getElementById("ifYesImage").style.display = "block";
			document.getElementById("has_image_question").value = true;
		} else {
			document.getElementById("ifYesImage").style.display = "none";
			document.getElementById("has_image_question").value = false;
		}

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
				<H1>
					<%=LanguagesSupporter.addQuestion(language)%>:
				</H1>
			</div>
			<div class="col-md-3"></div>

		</div>
		<div class="row">

			<div class="col-md-3"></div>
			<div class="col-md-6">

				
					<form class="form-horizontal" action="AddQuestion"
						onsubmit="return checkInput();" method="post"
						enctype="multipart/form-data">

						<input type="hidden" name="type"
							value="<%=Question.Type.OPEN.name()%>">

						<div class="form-group">
							<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionQText(language)%>:</label>
							<div class="col-md-9">
								<input class="form-control" id="question_text" type="text"
									size="31" name="question_text" data-step="1"
									data-intro="<%=LanguagesSupporter.addAIQStepOne(language)%>">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionQImage(language)%>:
							</label>
							<div class="col-md-1">
								<input type="checkbox" onclick="imageCheck();"
									name="imageYesNoCheck" id="yesImageCheck" data-step="2"
									data-intro="<%=LanguagesSupporter.addAIQStepTwo(language)%>">
							</div>

							<div class="col-md-8" id="ifYesImage" style="display: none">
								<input class="form-control" id="image_question" type="file"
									name="image_question"> <input type="hidden"
									id="has_image_question" name="has_image_question" value=false>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionCorrectAnswer(language)%>:</label>

							<div class="col-md-9">
								<input class="form-control" id="correct_answer" type="text"
									size="31" name="correct_answer" data-step="3"
									data-intro="<%=LanguagesSupporter.addAIQStepThree(language)%>">
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionPoints(language)%>:</label>

							<div class="col-md-9">
								<input class="form-control" id="points" type="text"
									name="points" size="5" data-step="4"
									data-intro="<%=LanguagesSupporter.addAIQStepSeven(language)%>">
							</div>

						</div>

						<div class="form-group">
							<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionTime(language)%>:
							</label>

							<div class="col-md-1">
								<input type="checkbox" onclick="yesnoCheck();" name="yesno"
									id="yesCheck" data-step="5"
									data-intro="<%=LanguagesSupporter.addAIQStepEight(language)%>">
							</div>

							<div class="col-md-8" id="ifYes" style="display: none"
								align="center">
								<input class="form-control" id="time" type="text" name="time"
									size="5" value="0">
							</div>

						</div>
					<div id="alertDiv"></div>

						<div class="form-group">
							<div class="col-md-offset-3 col-md-9">
								<button type="submit" class="btn btn-success">
									<%=LanguagesSupporter.generalSubmit(language)%>
								</button>


								<a class="btn btn-warning" href="gamePage.jsp"> <%=LanguagesSupporter.addQuestionBackToGamePage(language)%>
								</a>
							</div>
						</div>

						<input type="hidden" name="lindex" value="<%=lindex%>">
					</form>

			</div>

			<div class="col-md-3"></div>

		</div>

	</div>



</body>
</html>