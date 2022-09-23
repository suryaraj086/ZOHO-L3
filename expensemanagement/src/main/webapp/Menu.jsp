
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Menu</title>
<style>
#menu {
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	background: #9999ff;
	color: white;
	cursor: pointer;
	transition-duration: 0.4s;
	text-decoration: none;
	font-family: sans-serif;
	font-size: 100%;
}
</style>

</head>

<body>
	<form action="LoginController" method="post">
		<input type="submit" name="page"
			class="btn btn-primary btn-lg btn-radius" id="menu"
			value="Add Friends"> <input type="submit" name="page"
			class="btn btn-primary btn-lg btn-radius" id="menu" value="Add Trip">
		<input type="submit" name="page"
			class="btn btn-primary btn-lg btn-radius" id="menu"
			value="Add Expenses"> <input type="submit" id="menu"
			name="page" class="btn btn-primary btn-lg btn-radius" id="menu"
			value="Amount spent"> <input type="submit" name="page"
			class="btn btn-primary btn-lg btn-radius" id="menu"
			value="Amount owed"> <input type="submit" name="page"
			class="btn btn-primary btn-lg btn-radius" id="menu"
			value="Logout">
	</form>
</body>
</html>
