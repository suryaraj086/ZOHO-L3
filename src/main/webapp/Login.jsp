<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet" href="Css/Style.css">
<script src="Script/Script.js"></script>
<link rel="icon" href="Images/login.png" type="image/x-icon">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$("#submit").on('click', function(e) {
			if (!emptyChecker()) {
				$('#message').text("field cannot be empty");
				$('#alert').css('display', 'block');
				setTimeout(function() {
					$('#alert').css('display', 'none')
				}, 2500);
				return false;
			}
			$.ajax({
				url : 'LoginController',
				method : "POST",
				data : JSON.stringify({
					id : $('#id').val(),
					password : $('#password').val(),
					page : "login"
				}),
				contentType : "application/json; charset=utf-8",
				success : function(completeHtmlPage) {
					var index = completeHtmlPage.indexOf("message");
					if (index != -1) {
						var message = completeHtmlPage.substring(index + 8);
						$('#message').text(message);
						$('#alert').css('display', 'block');
						setTimeout(function() {
							$('#alert').css('display', 'none')
						}, 2500);
					} else {
						window.location.replace(completeHtmlPage);
					}
				}
			});
		});
	});
</script>
<style type="text/css">
#login_img {
	height: 100px;
	width: 100px;
	position: relative;
}

fieldset {
	background: rgba(255, 255, 255, 0.30);
	border-radius: 16px;
	box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
	border: 1px solid rgba(255, 255, 255, 0.85);
}
</style>
</head>
<body
	style="background-image: linear-gradient(to right, #b6fbff, #83a4d4);">
	<div class="alert_login" id="alert" style="display: none;">
		<span class="closebtn"
			onclick="this.parentElement.style.display='none';">&times;</span> <strong
			id="message"></strong>
	</div>
	<div id="form_login">
		<fieldset>
			<legend>
				<img src="Images/login.png" alt="login" id="login_img" /> <br /> <br />
			</legend>
			<label>Email Id</label><input placeholder="Enter the email id"
				name="id" oninput="validity.valid||(value='');" id="id" type="text"
				required><br /> <br /> <label>Password</label> <input
				placeholder="Enter the password" id="password" name="password"
				type="password" required="required"><br /> <input
				type="submit" name="login" id="submit" value="Login">
		</fieldset>
		<p>
			If you are new,signup here <a href="SignUp.jsp">Signup</a>
		</p>
	</div>
</body>
</html>