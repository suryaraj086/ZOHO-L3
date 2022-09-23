<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add new trip</title>
<link rel="stylesheet" type="text/css" href="Css/Style.css">
<script src="Script/Script.js"></script>
</head>
<body>
	<jsp:include page="Menu.jsp"></jsp:include>
	<form id="form" action="LoginController" method="post">
		<fieldset>
			<label>Trip name</label><input placeholder="Enter the trip name"
				name="id" type="text" required><br /> <br /> <label>Start
				date</label> <input type="date" id="startdate" title="Type in a name"><br />
			<br /> <label>End date</label> <input type="date" id="enddate"
				title="Type in a name"> <br />
			<br /> <label>select members in the trip</label> <input type="text"
				id="myInput" onkeyup="myFunction()" placeholder="Search for names.."
				title="Type in a name"> <br />
			<br />
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
			</table>
			<input type="submit" value="Submit">
		</fieldset>
	</form>
</body>
</html>