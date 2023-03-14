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
				<option>light</option>
				<option>brown</option>
				<option>dark</option>
			</select>
		 </p>
		<input type="submit" value="Submit" />
	</form>
</body>
</html>