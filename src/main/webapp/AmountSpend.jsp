<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Css/Style.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="Script/Script.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		if (localStorage.getItem('dark') == "dark") {
			document.body.classList.toggle('dark-mode');
		}
	});
</script>
<title>Amount spend</title>
<script>
	$(document).ready(
			function() {
				$.ajax({
					url : 'LoginController',
					method : "POST",
					data : JSON.stringify({
						page : "get spend amount",
					}),
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						var trHTML = "";
						$.each(JSON.parse(data), function(i, items) {
							trHTML += '<tr><td>' + items.tripId + '</td><td>'
									+ items.description + '</td><td>'
									+ items.toUserName + '</td><td>'
									+ items.toUserId + '</td><td>'
									+ items.amount + '</td></tr>';
						});
						if (trHTML == "") {
							trHTML = '<tr><td>No data to display</td></tr>';
						}
						$('#customers').append(trHTML);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						location.reload();
					}
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
	<table id="customers" style="width: 100%">
		<tr>
			<th>Trip Id</th>
			<th>Description</th>
			<th>To User</th>
			<th>To User Id</th>
			<th>Amount</th>
		</tr>
	</table>
</body>
</html>