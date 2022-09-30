<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Signup</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script src="Script/Script.js"></script>
<script>
$(document).ready(function() {
	$("#submit").on('click', function() {
		var form = $('#form_login');
		$.ajax({
			url : 'http://localhost:8080/expensemanagement/LoginController',
			method : "POST",
			data : JSON.stringify({
				first:1,
				second:2				
			}),
			success : function(completeHtmlPage) {
				alert("Success");
				$("html").empty();
				$("html").append(completeHtmlPage);
			}
		});
	});
});
</script>
<style type="text/css">
#form_login {
	left: 50%;
	top: 50%;
	position: absolute;
	transform: translate(-50%, -50%);
}

#login_img {
	height: 100px;
	width: 100px;;
}

input[type=text], input[type=password], input[type=number] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type=submit] {
	width: 100%;
	padding: 12px 20px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	background: #4E9CAF;
	color: white;
}
</style>
</head>
<body background="Images/background.jpg" id="test">
	<form id="form_login" >
		<fieldset>
			<legend>
				<img src="Images/login.png" id="login_img" /> <br /> <br />
			</legend>
			<label>User Name</label><input placeholder="Enter the name"
				name="name" id="name" type="text" required><br /> <br /> <label>Email
				id</label><input placeholder="Enter the email id" name="email" id="email"
				type="text" required><br /> <br /> <label>Phone
				number</label><input placeholder="Enter the number" name="phonenumber"
				type="number" required><br /> <br /> <label>Password</label><input
				placeholder="Enter the password" name="password" type="password"
				required><br /> <input type="submit" id="submit"
				name="signup" value="Register"> <input type="hidden"
				name="page" value="signup">
		</fieldset>
	</form>
</body>
</html>