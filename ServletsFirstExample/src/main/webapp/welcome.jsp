<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>JSP Page</title>
	</head>
<body>
	<!-- SLECHT -->
	<% String name = (String) request.getAttribute("firstName"); %>
	<h1>
		Welcome to Servlets!
		<%=name%>
	</h1>
	
	<!-- GOED -->
	<h1>
		Welcome to Servlets! ${firstName}!
	</h1>
</body>
</html>