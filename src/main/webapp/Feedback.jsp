<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="quiz_builder.site.LanguagesSupporter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String language = (String)session.getAttribute("lang");
	session.setAttribute("lang", language);
%>

<html>
<head>
<link href="introjs.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=LanguagesSupporter.feedback(language)%></title>

<script language="Javascript" type="text/javascript"> 
		var fillName = "<%=LanguagesSupporter.feedbackFillName(language)%>";
		var fillFeedback = "<%=LanguagesSupporter.feedbackFillFeedback(language)%>";
		function checkInput() {
			if(document.getElementById("name").value.length == 0) {
				setAlert(fillName);
				return false;
			}
			if(document.getElementById("description").value.length == 0) {
				setAlert(fillFeedback);
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
				<a class="btn btn-large btn-success" href="javascript:void(0);"	onclick="javascript:introJs().start();">
					<font size=4><%=LanguagesSupporter.gamesListGuide(language)%></font>
				</a>
				<script type="text/javascript" src="intro.js"></script>
			</div>
			<div class="col-md-6">
				<h1><%=LanguagesSupporter.feedback(language)%>:</h1>
			</div>
			<div class="col-md-3"></div>
		</div>
		<div class="row">
			<div class="col-xs-1 col-md-2"></div>
			<div class="col-xs-10 col-md-8">
				<form class="form-horizontal" role="form" action="/FeedbackServlet" onsubmit="return checkInput();" method="post">
					<label for="feedbackSource"></label>
					<input type="hidden" id="feedbackSource" name="feedbackSource" value="quizBuilder" />
					<div class="form-group">
						<label for="name" class="h3 col-sm-2 control-label"><%=LanguagesSupporter.feedbackName(language)%></label>
						<div class="col-sm-9">
							<input data-step="1" data-intro="<%=LanguagesSupporter.feedbackStepOne(language)%>"	class="form-control" type="text" id="name" name="name" />
						</div>
					</div>
					<div class="form-group">
						<label for="description" class="h3 col-sm-2 control-label"><%=LanguagesSupporter.feedbackDesc(language)%></label>
						<div class="col-sm-9">
							<textarea data-step="2"	data-intro="<%=LanguagesSupporter.feedbackStepTwo(language)%>" class="form-control" id="description" name="description"	cols="50" rows="5"></textarea>
						</div>
					</div>
			</div>
			<div class="col-xs-1 col-md-2"></div>
		</div>
		<div id="alertDiv"></div>
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6" align = center>
				<input class="btn btn-primary btn-large" type="submit" name="submit" value="<%=LanguagesSupporter.feedbackSend(language)%>" />
				</form>
					<a data-step="3" data-intro="<%=LanguagesSupporter.feedbackStepThree(language)%>" class="btn btn-success" href="/"><%=LanguagesSupporter.gameStatsBackToMainPage(language)%></a>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
</html>