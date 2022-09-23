<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Return</title>
<link rel="stylesheet" href="Css/Style.css">
</head>
<body>
	<jsp:include page="Menu.jsp"></jsp:include>
	<form action="ReturnAmount" id="form" method="post">
		<fieldset>
			<br> <label>Enter the user id</label> <br> <input
				type="number" placeholder="Enter the user id"> <br> <label>Enter
				the amount</label> <br> <input type="number"
				placeholder="Enter the amount"> <br> <input
				type="submit" value="Submit">
		</fieldset>
	</form>
</body>
</html>