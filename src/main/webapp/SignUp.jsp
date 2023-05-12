<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Signup</title>
      <script
         src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
      <script src="Script/Script.js"></script>
      <link rel="icon" href="Images/login.png" type="image/x-icon">
      <link rel="stylesheet" href="Css/Style.css">
      <script>
         $(document).ready(function() {
         	$("#submit").bind('click', function() {
         		if (!emptyCheckerSignup() || !validateemail() ||!validatePassword()) {
         			return false;
         		}
         		$.ajax({
         			url : 'http://localhost:8080/expensemanagement/SignUpAction',
         			method : "POST",
         			data : JSON.stringify({
         				name : $('#name').val(),
         				email : $('#email').val(),
         				phonenumber : $('#phone').val(),
         				password : $('#password').val(),
         			}),
         			contentType : "application/json; charset=utf-8",
         			success : function(completeHtmlPage) {
         				var index = completeHtmlPage.indexOf("message");
         				var message = completeHtmlPage.substring(index + 8);
         				if(index<0)
         				{
         					message="user created successfully";
             				$('#message').text(message);
             				$('#alert').css('display', 'block');
             			    setTimeout(function(){window.location.replace("Login.jsp");}, 1500);
         				}
         				else{
         				$('#message').text(message);
         				$('#alert').css('display', 'block');
         // 					$('#name').val("");
         // 					$('#email').val("");
         // 					$('#phone').val("");
         // 					$('#password').val("");
         // 					$('#confirmpassword').val("");
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
         }
         fieldset {
         background: rgba(255, 255, 255, 0.30);
         border-radius: 16px;
         box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
         border: 1px solid rgba(255, 255, 255, 0.85);
         }
      </style>
   </head>
   <body background="Images/background.jpg" id="test">
      <div class="alert" id="alert" style="display: none;">
         <span class="closebtn"
            onclick="this.parentElement.style.display='none';">&times;</span> <strong
            id="message"></strong>
      </div>
      <div id="form_login">
         <fieldset>
            <legend>
               <img src="Images/login.png" alt="signup" id="login_img" /> <br />
               <br />
            </legend>
            <label>User Name</label><input placeholder="Enter the name"
               name="name" id="name" type="text" required><br /> <br /> <label>Email
            id</label><input placeholder="Enter the email id" name="email" id="email"
               type="email" required><br /> <br /> <label>Phone
            number</label><input placeholder="Enter the number"
               oninput="validity.valid||(value='');" name="phonenumber"
               type="number" id="phone" required><br /> <br /> <label>Password</label><input
               placeholder="Enter the password" id="password" name="password"
               type="password" required><br /><label>Confirm Password</label><input
               placeholder="Enter the password" id="confirmpassword" name="password"
               type="password" required><br /> <input type="submit"
               id="submit" name="signup" value="Register"> <a
               href="Login.jsp">Login</a>
         </fieldset>
      </div>
   </body>
</html>