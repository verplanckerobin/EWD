<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
</head>
<body>
	<p>Beer Selection Advice</p>
	<p>Got beer color ${color}</p>
	<c:forEach var="beer" items="${listBeer}">
		<br>${beer}
	</c:forEach>
</body>
</html>