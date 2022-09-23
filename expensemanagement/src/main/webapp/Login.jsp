<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="Css/Style.css">
<style type="text/css">
#login_img {
	height: 100px;
	width: 100px;
	position: relative;
}
</style>
</head>
<body background="Images/background.jpg">
	<form id="form_login" action="LoginController" method="post">
		<fieldset>
			<legend>
				<img src="Images/login.png" id="login_img" /> <br /> <br />
			</legend>
			<label>User Id</label><input placeholder="Enter the user id"
				name="id" type="text" required><br /> <br /> <label>Password</label><input
				placeholder="Enter the password" name="password" type="password"
				required><br /> <input type="submit" name="login"
				value="Login">
		</fieldset>
		<p>
			if you are new,signup here <a href="SignUp.jsp">Signup</a>
		</p>
	</form>
</body>
</html>