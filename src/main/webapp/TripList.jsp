<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.expensemanagement.*"%>
<%@ page import="com.expensemanagement.datastore.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of trips</title>
<link rel="stylesheet" href="Css/Style.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	
</script>
<script src="Script/Script.js"></script>
<style type="text/css">
.button {
	display: block;
	color: white !important;
	background-color: #424248;
	text-align: center;
	padding: 20px 10px;
	text-decoration: none;
	border: 1px thin gray;
	cursor: pointer;
	border: none;
	border-radius: 25px;
	opacity: 0.7;
	transition: background-color 0.9s ease-out 50ms;
}

.table_div {
	position: absolute;
	border-radius: 5px;
	opacity: 0.9;
	background-color: #424248;
	color: white;
	display: none;
	top: 30%;
	left: 40%;
	z-index: 1;
	width: 40%;
	height: 40%;
	overflow: scroll;
	
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		if (localStorage.getItem('dark') == "dark") {
			document.body.classList.toggle('dark-mode');
		}
	});
</script>
<script>
	var paginate = 0;
	var paginationValue = 0;
	var paginatePrevious = 0;
	$(document)
			.ready(
					function() {
						$
								.ajax({
									url : 'LoginController',
									method : "POST",
									data : JSON.stringify({
										page : "show trips",
									}),
									contentType : "application/json; charset=utf-8",
									success : function(data) {
										var trHTML = "";
										$
												.each(
														JSON.parse(data),
														function(i, items) {
															var fromTime = items.fromDate;
															var fromDate = new Date(
																	fromTime);
															var toTime = items.toDate;
															var toDate = new Date(
																	toTime);
															var fDate = fromDate
																	.toLocaleDateString();
															var tDate = toDate
																	.toLocaleDateString();

															trHTML += '<tr><td><button class=\"button\" onclick=\"getTripMembers(this.value)\"; value='
																	+ items.tripId
																	+ '>'
																	+ items.tripId
																	+ '</button></td><td>'
																	+ items.tripName
																	+ '</td><td>'
																	+ fDate
																	+ '</td><td>'
																	+ tDate
																	+ '</td></tr>';
														});
										if (trHTML == "") {
											trHTML = '<tr><td>No data to display</td></tr>';
										}
										$('#customers').append(trHTML);
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										location.reload();
									}
								});
					});

	$(document)
			.ready(
					function() {
						$
								.ajax({
									url : 'LoginController',
									method : "POST",
									data : JSON.stringify({
										page : "get_total_pages",
									}),
									contentType : "application/json; charset=utf-8",
									success : function(data) {
										var trHTML = "";
										$
												.each(
														JSON.parse(data),
														function(key, val) {
															paginationValue = val;
															if (val > 0) {
																trHTML = '<li><button name=\"pagination_button\" class=\"pagination_buttonPrevious\" style=\"display:none;\" onclick=\"paginationPrevious(this.value)\"; value='
																		+ paginatePrevious
																		+ '><<</button></li>';
																trHTML += '<li><button>'
																		+ (paginate + 1)
																		+ "-"
																		+ (paginationValue + 1)
																		+ '</button></li>';
																trHTML += '<li><button name=\"pagination_button\" class=\"pagination_button\"  onclick=\"pagination(this.value)\"; value='
																		+ paginate
																		+ '>>></button></li>';
															}
														});
										friends = [];
										$('#myPager').append(trHTML);
									},
									error : function(XMLHttpRequest,
											textStatus, errorThrown) {
										location.reload();
									}
								});
					});
</script>
<script type="text/javascript">
	function pagination(value) {
		$(".pagination_buttonPrevious").show();

		paginate++;
		$
				.ajax({
					url : 'LoginController',
					method : "POST",
					data : JSON.stringify({
						page : "pagination",
						pageNo : paginate + "",
						filter : $("#friendoption").val(),
					}),
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						var trHTML = "";
						$
								.each(
										JSON.parse(data),
										function(key, items) {
											var fromTime = items.fromDate; // get your number
											var fromDate = new Date(fromTime);
											var toTime = items.toDate; // get your number
											var toDate = new Date(toTime);
											var fDate = fromDate
													.toLocaleDateString();
											var tDate = toDate
													.toLocaleDateString();

											trHTML += '<tr><td><button class=\"button\" onclick=\"getTripMembers(this.value)\"; value='
													+ items.tripId
													+ '>'
													+ items.tripId
													+ '</button></td><td>'
													+ items.tripName
													+ '</td><td>'
													+ fDate
													+ '</td><td>'
													+ tDate
													+ '</td></tr>';

										});
						if (trHTML == "") {
							trHTML = '<tr><td>No data to display</td></tr>';
						}
						$("#customers").find("tr:gt(0)").remove();
						$('#customers').append(trHTML);
						$('#myPager').empty();
						trHTML = "";
						trHTML = '<li><button name=\"pagination_button\" class=\"pagination_buttonPrevious\" style=\"display:block;\" onclick=\"paginationPrevious(this.value)\"; value='
								+ paginatePrevious + '><<</button></li>';
						trHTML += '<li><button>' + (paginate + 1) + "-"
								+ (paginationValue + 1) + '</button></li>';
						trHTML += '<li><button name=\"pagination_button\" class=\"pagination_button\"  onclick=\"pagination(this.value)\"; value='
								+ paginate + '>>></button></li>';
						$('#myPager').append(trHTML);
						if (paginationValue == paginate) {
							$(".pagination_button").hide();
							return false;
						}
						$(".pagination_button").show();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						location.reload();
					}
				});
	}

	function paginationPrevious(value) {
		$(".pagination_button").show();

		paginate--;
		$('input[type=text]').each(function() {
			$(this).val('');
		});
		$
				.ajax({
					url : 'LoginController',
					method : "POST",
					data : JSON.stringify({
						page : "pagination",
						pageNo : paginate + "",
						filter : $("#friendoption").val(),
					}),
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						var trHTML = "";
						$
								.each(
										JSON.parse(data),
										function(key, items) {
											var fromTime = items.fromDate; // get your number
											var fromDate = new Date(fromTime);
											var toTime = items.toDate; // get your number
											var toDate = new Date(toTime);
											var fDate = fromDate
													.toLocaleDateString();
											var tDate = toDate
													.toLocaleDateString();

											trHTML += '<tr><td><button class=\"button\" onclick=\"getTripMembers(this.value)\"; value='
													+ items.tripId
													+ '>'
													+ items.tripId
													+ '</button></td><td>'
													+ items.tripName
													+ '</td><td>'
													+ fDate
													+ '</td><td>'
													+ tDate
													+ '</td></tr>';

										});
						if (trHTML == "") {
							trHTML = '<tr><td>No data to display</td></tr>';
						}
						$("#customers").find("tr:gt(0)").remove();
						$('#customers').append(trHTML);
						$('#myPager').empty();
						trHTML = "";
						trHTML = '<li><button name=\"pagination_button\" class=\"pagination_buttonPrevious\" style=\"display:block;\" onclick=\"paginationPrevious(this.value)\"; value='
								+ paginatePrevious + '><<</button></li>';
						trHTML += '<li><button>' + (paginate + 1) + "-"
								+ (paginationValue + 1) + '</button></li>';
						trHTML += '<li><button name=\"pagination_button\" class=\"pagination_button\"  onclick=\"pagination(this.value)\"; value='
								+ paginate + '>>></button></li>';
						$('#myPager').append(trHTML);
						if (paginate == 0) {
							$(".pagination_buttonPrevious").hide();
							return false;
						}
						$(".pagination_buttonPrevious").show();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						location.reload();
					}
				});
	}
</script>
<script>
	function getTripMembers(tripId) {

		$.ajax({
			url : 'AddExpenseAction',
			method : "POST",
			data : JSON.stringify({
				tripid : tripId,
				page : "tripid for expense"
			}),
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				var trHTML = "";
				$.each(JSON.parse(data), function(key, items) {

					trHTML += '<tr><td>' + items.userId + ' </td><td>'
							+ items.name + '</td></tr>';
				});
				$("#members").find("tr:gt(0)").remove();
				$('#members').append(trHTML);
				$("#table").show();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				location.reload();
			}
		});
	}
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

	<div id="table" class="table_div">
		<span class="closebtn" style="padding-right: 5px; height: 7px;width: 7px;"
			onclick="this.parentElement.style.display='none';">&times;</span>
			<br/>
		<table id="members" style="width: 100%; height: 100%; " class="table">
			<tr>
				<th>User Id</th>
				<th>User Name</th>
			<tr>
		</table>
	</div>

	<div id="userform">
		<h1>Trip List</h1>
		<table id="customers" style="width: 100%">
			<tr>
				<th>Trip Id</th>
				<th>Trip Name</th>
				<th>From Date</th>
				<th>To Date</th>
			</tr>
			<%-- 				<c:forEach items="${tripList}" var="current"> --%>
			<!-- 					<tr> -->
			<%-- 						<td><c:out value="${current.getTripId()}" /></td> --%>
			<%-- 						<td><c:out value="${current.getTripName()}" /></td> --%>
			<!-- 					</tr> -->
			<%-- 				</c:forEach> --%>
		</table>
		<br />
		<ul id="myPager"></ul>
	</div>
</body>
</html>