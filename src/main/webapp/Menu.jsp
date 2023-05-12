
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Menu</title>
<link rel="stylesheet" href="Css/Style.css">

<style>
ul {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #424248;
	border-radius: 20px;
}

li {
	float: left;
}

li button,.sidebar_button  {
	display: block;
	color: white !important;
	background-color: #424248;
	text-align: center;
	padding: 22px 16px;
	text-decoration: none;
	border: 1px thin gray;
	cursor: pointer;
	border: none;
	width: 100%;
	transition: background-color 0.9s ease-out 50ms
}
.sidebar_button:hover{
  color: #f1f1f1;
  background-color: #111;
}

li button:hover {
	background-color: #111;
}

h3 {
	text-align: center;
	background-color: lavender;
}

.menu {
	border: none;
}

.profile {
	float: right;
}

.sidebar{
   
  width: 0;
  position: fixed;
  z-index: 1;
  top: 0;
  right: 0;
  height:100%;
  background-color: #424248;
  overflow-x: hidden;
  transition: all 0.5s ease;
  padding-top: 10px;
    
}


</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
</script>

<script>
	$(document).ready(function() {
		$("button[name = 'page']").click(function(){
				$.ajax({
				url : 'http://localhost:8080/expensemanagement/LoginController',
				method : "POST",
				data : JSON.stringify({
					page: $(this).attr('value')
				}),
				contentType: "application/json; charset=utf-8",
				success : function(completeHtmlPage) {
					window.location.replace(completeHtmlPage);
				}
			});
		});
	});
</script>
<script>

function openRightMenu() {
  document.getElementById("rightMenu").style.width = "250px";

}

function closeRightMenu() {
  document.getElementById("rightMenu").style.width = "0";

}
</script>
<script type="text/javascript">
window.addEventListener('mouseup', function(event){
	var box = document.getElementById('rightMenu');
	if (event.target != box && event.target.parentNode != box){
		closeRightMenu();
    }
});

</script>

<script>
$(document).ready(function() {
	var pathname = window.location.pathname;
	if(pathname.indexOf("AddFriends") != -1){
	      $('#addfriends').css("background-color", "#111");
	      $('#addfriends').prop('disabled', true);
	      $("#addfriends").css("border-bottom-color", "white");
	}else if(pathname.indexOf("AddTrip") != -1){
	      $('#addtrip').css("background-color", "#111");
	      $('#addtrip').prop('disabled', true);
	}else if(pathname.indexOf("TripList") != -1){
	      $('#listtrip').css("background-color", "#111");
	      $('#listtrip').prop('disabled', true);
	}else if(pathname.indexOf("AddExpense") != -1){
	      $('#addexpense').css("background-color", "#111");
	      $('#addexpense').prop('disabled', true);
	}else if(pathname.indexOf("AmountSpend") != -1){
	      $('#amountspend').css("background-color", "#111");
	      $('#amountspend').prop('disabled', true);
	}else if(pathname.indexOf("OwedAmount") != -1){
	      $('#owedamount').css("background-color", "#111");
	      $('#owedamount').prop('disabled', true);
	}else if(pathname.indexOf("ReturnOweAmount") != -1){
	      $('#returnowedamount').css("background-color", "#111");
	      $('#returnowedamount').prop('disabled', true);
	}
});

</script>

</head>

<body>
<% if (session.getAttribute("userId") == null) {
	 response.sendRedirect(request.getContextPath() + "/Login.jsp");		 
} %>

  
  <ul>
  <li> <p style="color: white !important; font-size: 20px !important; padding-right: 46px; padding-left: 50px; font-weight: bold; font-style:italic;">Splitwise</p></li>
  <li><button name="page" id="addfriends" class="menu"  value="Add Friends">Add Friends</button></li>
  <li><button name="page" id="addtrip" class="menu"  value="Add Trip">Add Trip</button></li>
  <li><button name="page" id="listtrip" class="menu"  value="List Trip">List Trip</button></li>
  <li><button name="page" id="addexpense" class="menu"  value="Add Expenses">Add Expenses</button></li>
  <li><button name="page" id="amountspend" class="menu"  value="Amount spent">Amount spent</button> </li>
  <li><button name="page" id="owedamount" class="menu"  value="Amount Owed">Amount Owed</button> </li>
  <li><button name="page" id="returnowedamount" class="menu"  value="Return owed amount">Return owed amount</button></li>
  <li style="float:right;"><img alt="profile" class="profile" style="cursor: pointer;" onclick="openRightMenu()" src="Images/profile1.png"></li> 
  </ul>
	
  <div style="width:0px;" class="sidebar" id="rightMenu">
  <button class="sidebar_button" onclick="closeRightMenu()" style="width: 100%;" >Close &times;</button>
  <div style="padding: 20px;">
  <br>
    <label style="color: white; text-align: center; padding-left: 20%;">hi,<% out.print(session.getAttribute("userName"));%></label>
  <br>
        <img  style="float: right; height: 35px;" alt="logout" src="Images/profile_2.png">
  
    <label style="color: white; text-align: center; padding-left: 20%;"><% out.print(session.getAttribute("emailId"));%></label>
  <br>
  <label style="color: white; text-align: center; padding-left: 20%;">your user id: <% out.print(session.getAttribute("userId"));%></label>
  <br>
  </div>
  <br>
        <button onclick="myFunction()" class="sidebar_button">Toggle dark mode<img  style="float: right; height: 35px;" alt="nightmode" src="Images/nightmode.png"></button>
  <br>  
  <button class="sidebar_button"  name="page" class="menu"  style="float: right; text-align:justify; padding: 9px 61px 9px 89px; top:10%;" value="Logout"><span>Logout</span><img  style="float: right; height: 35px;" alt="logout" src="Images/logout.png"></button>
 </div>
</body>
</html>