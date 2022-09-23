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
	<jsp:include page="Menu.jsp"></jsp:include>
	<h3>Enter the id to search</h3>
	<input type="text" id="myInput" onkeyup="myFunction()"
		placeholder="Type in the id..">
	<input type="submit" name="addfriends" id="menu"
		value="Add to friends list">
	<input type="submit" name="removefriends" id="menu"
		value="Remove from friends list">

	<table id="customers" style="width: 100%">
		<tr>
			<th>Select</th>
			<th>User Id</th>
			<th>User Name</th>
		</tr>
		<tr>
			<td><input type="checkbox" name="name" value="new" />&nbsp;</td>
			<td>1</td>
			<td>Example1</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="name" value="new" />&nbsp;</td>
			<td>2</td>
			<td>Example2</td>
		</tr>

		<%-- 		<c:forEach items="${LoginController}" var="current"> --%>
		<%-- 			<c:forEach items="${current.value}" var="current1"> --%>
		<!-- 				<tr> -->
		<!-- 					<td><input type="checkbox" name="name" />&nbsp;</td> -->
		<%-- 					<td><c:out value="${current.key}" /></td> --%>
		<%-- 					<td><c:out value="${current1.key}" /></td> --%>
		<%-- 					<td><c:out value="${current1.value.getName()}" /></td> --%>
		<%-- 					<td><c:out value="${current1.value.getBranch()}" /></td> --%>
		<%-- 					<td><c:out value="${current1.value.getBalance()}" /></td> --%>
		<%-- 					<td><c:out value="${current1.value.isStatus()}" /></td> --%>
		<!-- 				</tr> -->
		<%-- 			</c:forEach> --%>
		<%-- 		</c:forEach> --%>
	</table>
</body>
</html>