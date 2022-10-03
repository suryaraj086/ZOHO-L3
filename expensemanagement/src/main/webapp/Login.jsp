<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="Css/Style.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
</script>
<script>
	$(document).ready(function() {
		$("#submit").on('click', function() {
				$.ajax({
				url : 'http://localhost:8080/expensemanagement/LoginController',
				method : "POST",
				data : JSON.stringify({
					id:$('#id').val(),
					password:$('#password').val()
				}),
				contentType: "application/json; charset=utf-8",
				success : function(completeHtmlPage) {
					alert(completeHtmlPage);
				    $("html").empty();
				    $("html").append(completeHtmlPage);
// 					window.location.replace(completeHtmlPage);
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
</style>
</head>

<body background="Images/background.jpg">
 <%String s=request.getParameter("message");%>
<% if(s!=null){out.print("&ensp;<label style=color:white;><b>*"+s+"</b></label>");}%>
	<form id="form_login" id="login" method="post">
		<fieldset>
			<legend>
				<img src="Images/login.png" id="login_img" /> <br /> <br />
			</legend>
			<label>User Id</label><input placeholder="Enter the user id"
				name="id" id="id" type="number" required><br /> <br /> <label>Password</label><input
				placeholder="Enter the password" id="password" name="password" type="password"
				required><br /> <input type="submit" name="login" id="submit"
				value="Login">
		</fieldset>
		<p>
			if you are new,signup here <a href="SignUp.jsp">Signup</a>
		</p>
	</form>
</body>
</html>