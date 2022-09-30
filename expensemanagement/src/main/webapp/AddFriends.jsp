<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="javax.servlet.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="Css/Style.css">
<title>Add new friends</title>
<script src="Script/Script.js"></script>
</head>
<body>
	<%
	if (session.getAttribute("userId") == null) {
		response.sendRedirect(request.getContextPath() + "/Login.jsp");
	}
	%>
	<jsp:include page="Menu.jsp"></jsp:include>
	<%
	String s = request.getParameter("message");
	%>
	<%
	if (s != null) {
		out.print("&ensp;<label style=color:black;><b>*" + s + "</b></label>");
	}
	%>

	<form action="LoginController" method="post">
		<h3>Enter the id to search</h3>
		<input type="text" id="myInput" onkeyup="myFunction()"
			placeholder="Type in the id.."> <input type="submit"
			name="page" id="menu" value="Add to friends list"> <input
			type="submit" name="removefriends" id="menu"
			value="Remove from friends list">

		<table id="customers" style="width: 100%">
			<tr>
				<th>Select</th>
				<th>User Id</th>
				<th>User Name</th>
				<th>Phone number</th>

				<c:forEach items="${LoginController}" var="current">
					<tr>
						<td><input type="checkbox" name="friendslist"
							value="${current.key}" />&nbsp;</td>
						<td><c:out value="${current.key}" /></td>
						<td><c:out value="${current.value.getName()}" /></td>
						<td><c:out value="${current.value.getPhonenumber()}" /></td>
					</tr>
				</c:forEach>
		</table>
	</form>
</body>
</html>