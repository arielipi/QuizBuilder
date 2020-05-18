<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
  "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="quiz_builder.shared.*"%>
<%@ page import="quiz_builder.site.LanguagesSupporter"%>
<html>
<%
	int qindex = (Integer)request.getAttribute("qindex");
	int lindex = (Integer)request.getAttribute("lindex");
	AmericanImageQ question = (AmericanImageQ)request.getAttribute("question");	
	int incorrectSizeTwo = question.getIncorrectAnswers().size() - 1;
	int newIncorrectAnswersTwo = 0;
	String language = (String)session.getAttribute("lang");
	session.setAttribute("lang", language);
%>

<head>
<link href="introjs.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=LanguagesSupporter.editQuestion(language)%></title>

	<script language="Javascript" type="text/javascript">
		var counter = <%=question.getIncorrectAnswers().size()%>;
		var rowCounter = <%=question.getIncorrectAnswers().size()%>	+ 4;
		var incorrectSize =	<%=incorrectSizeTwo%>;
		var newIncorrectAnswers = 0;
		var alertFillQuestionText =	<%="'" + LanguagesSupporter.addQuestionFillQText(language) + "'"%>;
		var alertFillCorrectAnswer = <%="'"	+ LanguagesSupporter.addQuestionFillCorrectAnswer(language)	+ "'"%>;
		var alertFillIncorrectAnswer = <%="'" + LanguagesSupporter.addQuestionFillIncorrectAnswer(language) + "'"%>;
		var alertFillPoints = <%="'" + LanguagesSupporter.addQuestionFillPoints(language) + "'"%>;
		var alertFillTime =	<%="'" + LanguagesSupporter.addQuestionFillTime(language) + "'"%>;
	
		function addQuestion() {
			counter++;
			var newdiv = document.createElement('div');
			var name = "incorrecti" + counter;
			var divID = "divID_" + counter;
			var nameTwoR = "incorrecti_answer_replace_checkbox" + counter;
			var nameTwoDIV = "incorrectDIVi" + counter;
			
			
			newdiv.innerHTML = "<div class='form-group' id=" + divID + ">"
								+	"<div class='col-md-offset-3 col-md-1'>"
										+ "<input type='checkbox'"
											+	"onclick='replaceImage('" + nameTwoR + "','" + nameTwoDIV + "');'"
											+	"name='" + nameTwoR + "' id='" + nameTwoR + "'>"
								+	"</div>"
								+ "<div class='col-md-8' id='" 
										+ name 
										+ "' style='display: block' align='center'>"
											+ "<input class='form-control' id='" + name + "' type='file'>"
								+ "</div></div>";
					
			var incorrectsDiv = document.getElementById('incorrects');
			incorrectsDiv.appendChild(newdiv);
			rowCounter++;
			newIncorrectAnswers++;
		}
	
		function removeQuestion() {
			var localCounter = rowCounter;
			if (localCounter > 5) {
	
				var divID = "divID_" + counter;
				var element = document.getElementById(divID);
				element.parentNode.removeChild(element);
				counter--;
				rowCounter--;
				if (newIncorrectAnswers == 0) {
					if (incorrectSize > 0) {
						incorrectSize--;
					}
				} else {
					newIncorrectAnswers--;
				}
			}
		}
	
		function checkInput() {
			var i = 2;
			var timeValue = parseInt(document.getElementById("time").value, 10);
			var pointsValue = parseInt(document.getElementById("points").value, 10);
			if (document.getElementById("question_text").value.length == 0) {
				setAlert(alertFillQuestionText);
				return false;
			}
			if (document.getElementById("correct_answer_replace").value == "") {
				document.getElementById("correct_answer_replace_checkbox").value = false;
			} else {
				document.getElementById("correct_answer_replace_checkbox").value = true;
			}
			if (document.getElementById("points").value.length == 0) {
				setAlert(alertFillPoints);
				return false;
			}
			if (pointsValue < 0) {
				setAlert(alertFillPoints);
				return false;
			}
			if (document.getElementById("incorrecti1").value == "") {
				document.getElementById("incorrecti_answer_replace_checkbox1").value = false;
			} else {
				document.getElementById("incorrecti_answer_replace_checkbox1").value = true;
			}
			if (timeValue < 0) {
				setAlert(alertFillTime);
				return false;
			}
			if (document.getElementById("image_question").value == "") {
				document.getElementById("has_image_question").value = false;
			} else {
				document.getElementById("has_image_question").value = true;
			}
			if (rowCounter == 5) {
				return true;
			} else {
				var localCounter = rowCounter;
				var newIncorrect = 5 + incorrectSize;
				var oldIncorrect = 5;
				i = newIncorrectAnswers + incorrectSize + 1;
				while (localCounter > newIncorrect) {
					var name = "incorrecti" + i;
					if (document.getElementById(name).value == "") {
						setAlert(alertFillIncorrectAnswer);
						return false;
					}
					i--;
					localCounter--;
				}
				i = 2;
				while (localCounter > oldIncorrect) {
					var name = "incorrecti" + i;
					var nameTwo = "incorrecti_answer_replace_checkbox" + i;
					if (document.getElementById(name).value == "") {
						document.getElementById(nameTwo).value = false;
					} else {
						document.getElementById(nameTwo).value = true;
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
	
		function removeImage(givenStr) {
			if (document.getElementById(givenStr).checked) {
				document.getElementById(givenStr).value = "true";
			} else {
				document.getElementById(givenStr).value = "false";
			}
		}
	
		function replaceImage(givenCheckboxStr, givenDivStr) {
			if (document.getElementById(givenCheckboxStr).checked) {
				document.getElementById(givenCheckboxStr).value = "true";
				document.getElementById(givenDivStr).style.display = "block";
			} else {
				document.getElementById(givenCheckboxStr).value = "false";
				document.getElementById(givenDivStr).style.display = "none";
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
					onclick="javascript:introJs().start();"><font size="4"><%=LanguagesSupporter.gamesListGuide(language)%></font></a>
				<script type="text/javascript" src="intro.js"></script>

			</div>
			<div class="col-md-6">
				<h1><%=LanguagesSupporter.editQuestion(language)%></h1>
			</div>
			<div class="col-md-3"></div>
		</div>

		<div class="row">

			<div class="col-md-3"></div>
			<div class="col-md-6">

				<form class="form-horizontal" action="/EditQuestion"
					onsubmit="return checkInput();" method="post"
					enctype="multipart/form-data">

					<input type="hidden" name="lindex" value="<%=lindex%>"> <input
						type="hidden" name="qindex" value="<%=qindex%>"> <input
						type="hidden" name="type"
						value="<%=Question.Type.AMERICANIMAGEQ.name()%>">

					<div class="form-group">
						<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionQText(language)%>:</label>
						<div class="col-md-9">
							<input class="form-control" id="question_text" type="text"
								size="31" name="question_text"
								value="<%=question.getQuestion_text()%>" data-step="1"
								data-intro="<%=LanguagesSupporter.addAIQStepOne(language)%>">
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionQImage(language)%>:</label>
						<div class="col-md-1">
							<input type="checkbox" onclick="imageCheck();"
								name="imageYesNoCheck" id="yesImageCheck" data-step="2"
								data-intro="<%=LanguagesSupporter.addAIQStepTwo(language)%>">
						</div>

						<div class="col-md-8" id="ifYesImage" style="display: none"
							align="center">
							<input class="form-control" type="hidden" id="has_image_question"
								name="has_image_question"
								value="<%=question.getHasImageQuestion()%>"> <input
								id="image_question" type="file" name="image_question">
						</div>
					</div>

					<div class="form-group"
						style="display: <%=question.getDisplayBlockNone()%>">
						<label class="col-md-3 control-label"><%=LanguagesSupporter.editQuestionToRemoveImage(language)%>:</label>
						<div class="col-md-1">
							<input type="checkbox"
								onclick="removeImage('has_question_stored_image');"
								name="has_question_stored_image" id="has_question_stored_image"
								value="false">
						</div>
						<div class="col-md-8">
							<%
								if(question.getHasImageQuestion() == true) {
							%>
							<img id="question_stored_image" name="question_stored_image"
								src="${pageContext.request.contextPath}/ImageServlet?key=<%=question.getImageKey()%>"
								alt="">
							<%
								}
							%>
						</div>
					</div>

					<div class="form-group">
						<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionCorrectAnswer(language)%>:</label>
						<div class="col-md-1">
							<input type="checkbox"
								onclick="replaceImage('correct_answer_replace_checkbox', 'correct_answer_replace_div');"
								name="correct_answer_replace_checkbox"
								id="correct_answer_replace_checkbox" data-step="3"
								data-intro="<%=LanguagesSupporter.editAIQStepThree(language)%>">
						</div>
						<div class="col-md-8" id="correct_answer_replace_div"
							style="display: none" align="center">
							<input class="form-control" id="correct_answer_replace"
								type="file" name="correct_answer_replace">
						</div>
						<div class="col-md-8" style="display: block" align="center">
							<img id="correct_answer" name="correct_answer"
								src="${pageContext.request.contextPath}/ImageServlet?key=<%=question.getCorrectAnswerImage()%>"
								alt="">
						</div>
					</div>

					<!-- ADD THE INCORRECTS HERE!!!! -->

					<div id="incorrects">
						<%
							int i = 1;
							for (String incorrect : question.getIncorrectAnswers()) {
								String name = "incorrecti" + i;
								String fetchedName = "incorrectFetchedI" + i;
								String nameDIV = "incorrectDIVi" + i;
								String replaceCheckboxDiv = "incorrecti_answer_replace_checkbox" + i;
								String divID = "divID_" + i;
								i++;
								if (i == 2) {
						%>
									<div class="form-group">
										<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionIncorrectAnswer(language)%>:</label>
										<div class="col-md-1">
											<input type="checkbox" onclick="replaceImage('<%=replaceCheckboxDiv%>', '<%=nameDIV%>');"	name='<%=replaceCheckboxDiv%>' id='<%=replaceCheckboxDiv%>' data-step="4" data-intro="<%=LanguagesSupporter.editAIQStepFour(language)%>">
										</div>
										<div class="col-md-8" id='<%=nameDIV%>' style="display: none" align="center">
											<input class="form-control" id='<%=name%>' type="file" name='<%=name%>'>
										</div>
										<div style="display: block" align="center">
											<img id='<%=fetchedName%>' name='<%=fetchedName%>' src="${pageContext.request.contextPath}/ImageServlet?key=<%=incorrect%>">
										</div>
									</div>
						<%
								} else {
						%>
									<div class="form-group" id="<%=divID%>">
										<div class="col-md-offset-3 col-md-1">
											<input type="checkbox" onclick="replaceImage('<%=replaceCheckboxDiv%>','<%=nameDIV%>');" name='<%=replaceCheckboxDiv%>' id='<%=replaceCheckboxDiv%>'>
										</div>
										<div class="col-md-8" id='<%=nameDIV%>' style="display: none" align="center">
											<input class="form-control" id='<%=name%>' type="file" name='<%=name%>'>
										</div>
										<div style="display: block" align="center">
											<img id='<%=fetchedName%>' name='<%=fetchedName%>' src="${pageContext.request.contextPath}/ImageServlet?key=<%=incorrect%>">
										</div>
									</div>
						<%
								}
							}
						%>


					</div>

					<div class="form-group">
						<div class="col-md-offset-3 col-md-9">

							<a class="btn btn-defualt btn-lg" href="#"
								onclick="addQuestion();" data-step="5"
								data-intro="<%=LanguagesSupporter.addAIQStepFive(language)%>">
								<span class="glyphicon glyphicon-plus"></span>
							</a> <a class="btn btn-defualt btn-lg" href="#"
								onclick="removeQuestion();" data-step="6"
								data-intro="<%=LanguagesSupporter.addAIQStepSix(language)%>">
								<span class="glyphicon glyphicon-minus"></span>
							</a>

						</div>
					</div>


					<div class="form-group">
						<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionPoints(language)%>:</label>

						<div class="col-md-9">
							<input class="form-control" id="points" type="text" name="points"
								size="5" value="<%=question.getPoints()%>" data-step="7"
								data-intro="<%=LanguagesSupporter.addAIQStepSeven(language)%>">
						</div>

					</div>

					<div class="form-group">
						<label class="col-md-3 control-label"><%=LanguagesSupporter.addQuestionTime(language)%>:</label>
						<div class="col-md-1">
							<input type="checkbox" onclick="yesnoCheck();" name="yesno"
								id="yesCheck" data-step="8"
								data-intro="<%=LanguagesSupporter.addAIQStepEight(language)%>">
						</div>
						<div class="col-md-8">
							<div id="ifYes" style="display: none" align="center">
								<input class="form-control" id="time" type="text" name="time" size="5"
									value="<%=question.getTime()%>">
							</div>
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
				</form>

			</div>

			<div class="col-md-3"></div>

		</div>

	</div>

</body>
</html>