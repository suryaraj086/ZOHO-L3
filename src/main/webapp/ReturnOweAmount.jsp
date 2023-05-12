<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Return</title>
<link rel="stylesheet" href="Css/Style.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
</script>
      <script src="Script/Script.js"></script>

<script>
	$(document).ready(function() {
		$("#repay").on('click', function() {
			if(!emptyChecker())
   			{
			    $('#message').text("field cannot be empty");
				$('#alert').css('display', 'block');
			    setTimeout(function(){ $('#alert').css('display', 'none')}, 2500);
   			 	return false;
   			}
			$.ajax({
				url : 'http://localhost:8080/expensemanagement/RepayAction',
				method : "POST",
				data : JSON.stringify({
					page : "repay individual owe amount",
					expenseid : $('#expense').val(),
					userid : $('#customers').val()
				}),
				contentType : "application/json; charset=utf-8",
				success : function(completeHtmlPage) {
					var index = completeHtmlPage.indexOf("message");
					var message = completeHtmlPage.substring(index + 8);
					$('#message').text(message);
					if (message.indexOf("successful") != -1) {
						$("#expense").val("");
						$("#customers").val("");
						$('#alert').css('background-color', 'limegreen');
	             		setTimeout(function() {
	             			location.reload();
						}, 2500);

					} else {
						$('#alert').css('background-color', '#e9989fad');
					}
					$('#alert').css('display', 'block');
					setTimeout(function() {
						$('#alert').css('display', 'none')
					}, 2500);
				},error: function(XMLHttpRequest, textStatus, errorThrown) {
             		location.reload();
           	  }
			});
		});
	});
</script>

<script>
	$(document).ready(function() {
		$("#repay_all").on('click', function() {
			$.ajax({
				url : 'http://localhost:8080/expensemanagement/RepayAction',
				method : "POST",
				data : JSON.stringify({
					page : "repay all"
				}),
				contentType : "application/json; charset=utf-8",
				success : function(completeHtmlPage, data) {
					var index = completeHtmlPage.indexOf("message");
					var message = completeHtmlPage.substring(index + 8);
					$('#message').text(message);
					if (message.indexOf("successful") != -1) {
						$("#userid").val("");
						$("#expenseid").val("");
						$('#alert').css('background-color', 'limegreen');
					} else {
						$('#alert').css('background-color', '#e9989fad');
					}
					$('#alert').css('display', 'block');
					setTimeout(function() {
						$('#alert').css('display', 'none')
					}, 2500);
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
         		url : 'RepayAction',
         		method : "POST",
         		data : JSON.stringify({
         			page:"get owed user id",
         		}),
         		contentType: "application/json; charset=utf-8",
         		success : function(data) {
    				var trHTML='<option value=\"none\" selected disabled hidden> Select an Option </option>';
            			 $.each(JSON.parse(data), function (i, items) {	
            				 trHTML+='<option value='+items.fromUserId+'>'+items.fromUserId+'-'+items.toUserName+'</option>';
            			    });
            			 $('#customers').append(trHTML);
            		},error: function(XMLHttpRequest, textStatus, errorThrown) {
                 		location.reload();
               	  }
         	});
         });
      </script>
<script type="text/javascript">

function getExpenseId() {
 	$.ajax({
 		url : 'RepayAction',
 		method : "POST",
 		data : JSON.stringify({
 			page : "get owed expense id",
 			userid : $('#customers').val()
 		}),
 		contentType: "application/json; charset=utf-8",
 		success : function(data) {
			var trHTML='<option value=\"none\" selected disabled hidden> Select an Option </option>';
    			 $.each(JSON.parse(data), function (i, items) {
    				 trHTML+='<option value='+items+'>'+items+'</option>';
    			    });
      			$("#expense").empty();
    			 $('#expense').append(trHTML);
    		},error: function(XMLHttpRequest, textStatus, errorThrown) {
         		location.reload();
       	  }
 	});

}

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

	<br />
	<br />
	<div id="form">
		<fieldset>
			<br>
			
			<label>Enter the user id</label> <br> 
			<select id="customers" class="friendoption" onchange="getExpenseId()"></select>
			
			<br> <label>Enter the
				expense id</label> <br> 
			<select id="expense" class="friendoption" > <option value="none" selected disabled hidden> Select an Option </option></select>
				
				<br> <input type="submit"
				id="repay" value="Submit"> <input type="submit"
				id="repay_all" value="Pay all owe amount">
		</fieldset>
	</div>
</body>
</html>