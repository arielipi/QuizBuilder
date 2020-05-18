<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="quiz_builder.site.LanguagesSupporter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String language = (String)session.getAttribute("lang");
	session.setAttribute("lang", language);
%>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="Quiz_Builder.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><%=LanguagesSupporter.about(language)%></title>	
</head>


<jsp:include page="user.jsp" />

<body class="first">
	<div class="user"><jsp:include page="user.jsp" /></div>
	<div class="main">
		<h1><%=LanguagesSupporter.about(language)%>:</h1>
		<font size="4">
		<%=LanguagesSupporter.getAbout(language)%>
		<br>
		<br>
		<br>
		<a class="btn btn-success" href="gamesList.jsp"><%=LanguagesSupporter.gameStatsBackToMainPage(language)%></a>
		</font>
	</div>
</body>
</html>