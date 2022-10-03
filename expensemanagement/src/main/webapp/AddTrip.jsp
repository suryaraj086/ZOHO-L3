<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add new trip</title>
<link rel="stylesheet" type="text/css" href="Css/Style.css">
<script src="Script/Script.js"></script>
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
					tripname:$('#tripname').val(),
					startdate:$('#startdate').val(),
					enddate:$('#enddate').val(),
					page:"added trip",
					checkbox: $('#checkbox').val()
				}),
				contentType: "application/json; charset=utf-8",
				success : function(completeHtmlPage) {
					alert(completeHtmlPage);
				    $("html").empty();
				    $("html").append(completeHtmlPage);
				}
			});
		});
	});
</script>
</head>
<body>
	<%
	if (session.getAttribute("userId") == null) {
		response.sendRedirect(request.getContextPath() + "/Login.jsp");
	}
	%>
	<jsp:include page="Menu.jsp"></jsp:include>
	<form id="form"  method="post">
		<fieldset>
			<label>Trip name</label><input placeholder="Enter the trip name"
				name="tripname" id="tripname" type="text" required><br /> <br /> <label>Start
				date</label> <input type="date" id="startdate" title="Type in a name"><br />
			<br /> <label>End date</label> <input type="date" id="enddate"
				title="Type in a name"> <br /> <br /> <label>select
				members in the trip</label> <input type="text" id="myInput"
				onkeyup="myFunction()" placeholder="Search for names.."
				title="Type in a name"> <br /> <br />
			<table id="customers" style="width: 100%">
				<tr>
					<th>Select</th>
					<th>User Id</th>
				</tr>

				<c:forEach items="${friendsList}" var="current">
					<tr>
						<td><input type="checkbox"  id= "checkbox" name="tripfriends"
							value="${current}" />&nbsp;</td>
						<td><c:out value="${current}" /></td>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" name="page" value="added trip"> <input
				type="submit" id="submit" value="Submit">
		</fieldset>
	</form>
</body>
</html>