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
<title>Add new Expense</title>
<link rel="stylesheet" href="Css/Style.css">
<style>
#add {
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

select {
	width: 100%;
}
</style>
<script src="Script/Script.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="Script/multiselect-dropdown.js"></script>
<script>
      </script>
<script>
      var val=0;
           var friends = new Array;
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
         			url : 'AddExpenseAction',
         			method : "POST",
         			data : JSON.stringify({
         				tripid:$('#trips').val(),
         				amount:$('#amount').val(),
         				description:$('#desc').val(),
         				friendslist:selected
         			}),
         			contentType: "application/json; charset=utf-8",
         			success : function(completeHtmlPage) {
             			

         				var index=completeHtmlPage.indexOf("message");
         				var message=completeHtmlPage.substring(index+8);
       			      	  $('#message').text(message);
       			     	  if(message.indexOf("successfully")!=-1)
   			    		  {
       			     		$("#cust").empty();
                 			$("#trips").val("");
                 			$("#amount").val("");
                 			$("#desc").val("");
                 		  	$("#cust").empty();
                 			$("#cust").append("");
                 			cust.loadOptions();
   			    	  	  $('#alert').css('background-color', 'limegreen');
   			     	  	  }else{
   			    	  	  $('#alert').css('background-color', '#e9989fad');
   			    	      }
       			   
         			      $('#alert').css('display', 'block');
         			      setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
         			},
         			error: function(XMLHttpRequest, textStatus, errorThrown) {
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
				page:"show all trips",
			}),
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				var trHTML='<option value=\"none\" selected disabled hidden> Select an Option </option>';
				 $.each(JSON.parse(data), function (i, items) {
				        trHTML += '<option value=' + items.tripId + '>' + items.tripName+"-"+items.tripId + '</option>';
				    });
				  $('#trips').append(trHTML);

			},error: function(XMLHttpRequest, textStatus, errorThrown) {
         		location.reload();
       	  }
		});
	});
</script>
<script>
         $(document).ready(function() {
         	$("#trips").change(function(){
         		$("#cust").prop("disabled",false);
         		val=$('#trips').val();	
         		$.ajax({
         			url : 'AddExpenseAction',
         			method : "POST",
         			data : JSON.stringify({
         				tripid:val,
         				page:"tripid for expense"
         			}),
         			contentType: "application/json; charset=utf-8",
         			success : function(data) {
         				var s="";
         				var index=data.indexOf("message");
         				if(index!=-1)
         				{
                 		  $("#cust").empty();
                 		  $("#cust").append(s);
                 		  cust.loadOptions();
         				  var message=data.substring(index+8);
       			      	  $('#message').text(message);
         			      $('#alert').css('display', 'block');
         			      setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
         				}
         			    $.each(JSON.parse(data), function (key, value) {
         		        s += '<option value="' + value.userId + '">' + value.name+"-"+value.userId + '</option>';  
         	          });
         			$("#cust").empty();
         			$("#cust").append(s);
         			cust.loadOptions();
         		    },error: function(XMLHttpRequest, textStatus, errorThrown) {
                 		location.reload();
               	  }
         		});
         		});
         		});
         $(document).ready(function() {
          	$("#trips").blur(function(){
          		
          		if(val!=0)
          		{
          		return;	
          		}
          		val=$('#trips').val();
          		$.ajax({
          			url : 'AddExpenseAction',
          			method : "POST",
          			data : JSON.stringify({
          				tripid:val,
          				page:"tripid for expense"
          			}),
          			contentType: "application/json; charset=utf-8",
          			success : function(data) {
          				var s="";
          				var index=data.indexOf("message");
          				if(index!=-1)
          				{
                  		  $("#cust").empty();
                  		  $("#cust").append(s);
                  		  cust.loadOptions();
          				  var message=data.substring(index+8);
        			      $('#message').text(message);
          			      $('#alert').css('display', 'block');
          			      setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
          				}
          			    $.each(JSON.parse(data), function (key, value) {
          		        s += '<option value="' + value.userId + '">' + value.name+"-"+value.userId + '</option>';  
          	          });
          		    }
          		});
          		});
          		});
</script>
<script type="text/javascript">
      $(document).ready(function () {
    	  if(localStorage.getItem('dark')=="dark") {
    			document.body.classList.toggle('dark-mode');
    	      }
    	});
    
      </script>
</head>
<body>
	<jsp:include page="Menu.jsp"></jsp:include>
	<%
	if (session.getAttribute("userId") == null) {
		response.sendRedirect(request.getContextPath() + "/Login.jsp");
		return;
	}
	%>
	<div class="alert" id="alert" style="display: none;">
		<span class="closebtn"
			onclick="this.parentElement.style.display='none';">&times;</span> <strong
			id="message"></strong>
	</div>

	<div id="form">
		<fieldset>
			<br /> <label>select trip name</label> <br /> <br /> <select
				class="friendoption" id="trips">
			</select> <br /> <br /> <label>Select the persons you shared</label> <br />
			<br /> <select id="cust" name="customer" multiple="multiple"
				multiselect-search="true" multiselect-select-all="true">
			</select> <br /> <br /> <label>Enter the amount </label> <br /> <input
				type="number" name="amount" id="amount" min="0"
				onKeyPress="if(this.value.length==7) return false;" id="amount"
				required="required"> <br /> <label>Description </label> <br />
			<input type="text" placeholder="description" name="desc"
				maxLength="30" id="desc" required="required"><br /> <input
				id="submit" type="submit" value="Submit"> <br />
		</fieldset>
	</div>
</body>
</html>