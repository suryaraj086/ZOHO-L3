<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="Css/Style.css">
<meta charset="UTF-8">
<title>Amount spend</title>
</head>
<body>
<% if (session.getAttribute("userId") == null) {
	 response.sendRedirect(request.getContextPath() + "/Login.jsp");		 
} %>
	<jsp:include page="Menu.jsp"></jsp:include>
	<table id="customers" style="width: 100%">
		<tr>
			<th>Trip Id</th>
			<th>Description</th>
			<th>Amount</th>
		</tr>
		<tr>
			<td>1</td>
			<td>Example1</td>
			<td>100</td>
		</tr>
		<tr>
			<td>2</td>
			<td>Example2</td>
			<td>200</td>
		</tr>
	</table>
</body>
</html>