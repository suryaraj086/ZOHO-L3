<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.expensemanagement.*"%>
<%@ page import="com.expensemanagement.datastore.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add new trip</title>
<link rel="stylesheet" type="text/css" href="Css/Style.css">
<script src="Script/multiselect-dropdown.js"></script>
<script src="Script/Script.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript">
         $(document).ready(function () {
          if(localStorage.getItem('dark')=="dark") {
         	document.body.classList.toggle('dark-mode');
              }
         });
         
      </script>
<script>
         var trip = new Array;
      </script>
<script>
         $(document).ready(function() {
         	$("#submit").on('click', function() {
         		if(!emptyChecker())
         	{
          	$('#message').text("field cannot be empty");
         $('#alert').css('display', 'block');
           	setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
         	 	return false;
         	}
         			var selected = $("#cust :selected").map((_, e) => e.value).get();
         			$.ajax({
         			url : 'AddTripAction',
         			method : "POST",
         			data : JSON.stringify({
         				tripname:$('#tripname').val(),
         				startdate:$('#startdate').val(),
         				enddate:$('#enddate').val(),
         				page:"added trip",
         				friendslist: selected
         			}),
         			contentType: "application/json; charset=utf-8",
         			success : function(completeHtmlPage) {
         				var index=completeHtmlPage.indexOf("message");
         				var message=completeHtmlPage.substring(index+8);
         	      	  $('#message').text(message);
         		     if(message.indexOf("successfully")!=-1)
             	  {
         		      $("#tripname").val("");
         		      $("#startdate").val("");
         		      $("#enddate").val("");
               		      $("#cust").val("");
               		      cust.loadOptions();
             	  $('#alert').css('background-color', 'limegreen');
              	  }else{
             	  $('#alert').css('background-color', '#e9989fad');
             	  }
         			      $('#alert').css('display', 'block');
         			      setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
         			},error: function(XMLHttpRequest, textStatus, errorThrown) {
                 		location.reload();
               	  }
         		});
         	});
         });
      </script>
<script>
         $(document).ready(function() {
         	$.ajax({
         		url : 'LoginController',
         		method : "POST",
         		data : JSON.stringify({
         			page:"get friends data",
         		}),
         		contentType: "application/json; charset=utf-8",
         		success : function(data) {
         			var s="";
         			$.each(JSON.parse(data), function (key, value) {
                            s += '<option value="' + value.userId + '">' + value.name + '</option>';  
                        });
         			$("#cust").prepend(s); 
         			cust.loadOptions();
         	    },error: function(XMLHttpRequest, textStatus, errorThrown) {
             		location.reload();
           	  }
         	});
         });
      </script>
<style type="text/css">
select {
	width: 100%;
}
</style>
</head>
<body>
	<%
	if (session.getAttribute("userId") == null) {
		response.sendRedirect(request.getContextPath() + "/Login.jsp");
		return;
	}
	%>
	<jsp:include page="Menu.jsp"></jsp:include>
	<div class="alert" id="alert" style="display: none;">
		<span class="closebtn"
			onclick="this.parentElement.style.display='none';">&times;</span> <strong
			id="message"></strong>
	</div>
	<div id="form">
		<fieldset>
			<label>Trip name</label> <input placeholder="Enter the trip name"
				name="tripname" id="tripname" type="text" maxLength="30" required><br />
			<br /> <label>Start date</label> <input type="date" min="2018-01-01"
				id="startdate" title="Type in a start date" required="required"><br />
			<br /> <label>End date</label> <input type="date" min="2018-01-01"
				id="enddate" title="Type in the end date" required="required">
			<br /> <br /> <label>select members in the trip</label> <br /> <br />
			<select id="cust" name="customer" multiple="multiple"
				multiselect-search="true" multiselect-select-all="true">
			</select> <br /> <br /> <input type="submit" id="submit" value="Submit">
		</fieldset>
	</div>
</body>
</html>