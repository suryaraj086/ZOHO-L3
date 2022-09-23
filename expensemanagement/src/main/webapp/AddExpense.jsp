<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add new Expense</title>
<link rel="stylesheet" href="Css/Style.css">
<script src="Script/Script.js"></script>
</head>
<body>
	<jsp:include page="Menu.jsp"></jsp:include>
	<form action="AddExpense" id="form" method="post">
		<fieldset>
			<br /> <label>Enter the trip id</label> <br /> <input type="number"
				name="tridId"> <br /> <label>Select the persons you
				shared</label> <br /> <input type="text" id="myInput"
				onkeyup="myFunction()" name="members">
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
			<br /> <label>Enter the amount </label> <br /> <input type="number"
				name="amount"> <br /> <input type="submit" value="Submit">
			<br />
		</fieldset>
	</form>
</body>
</html>