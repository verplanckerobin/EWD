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
	<form action="beerSelect" method="post">
		<h1><strong>Beer Selection Page</strong></h1>
		<p>Select beer characteristics Color:
			<select name="color">
				<c:forEach var="kleur" items="${beerColors}">
					<option>${kleur}</option>
				</c:forEach>
			</select>
		 </p>
		<input type="submit" value="Submit" />
	</form>
</body>
</html>