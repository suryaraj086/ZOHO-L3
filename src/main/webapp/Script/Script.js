function myFunction() {
	$(document).ready(function() {
		$("#myInput").on("keyup", function() {
			var value = $(this).val().toLowerCase();
			$("#customers tr").filter(function() {
				$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
			});
		});
	});
}

function userIdFilter() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("myInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("customers");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[1];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function userNameFilter() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("myInput1");
	filter = input.value.toUpperCase();
	table = document.getElementById("customers");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[2];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}


function userPhoneFilter() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("myInput2");
	filter = input.value.toUpperCase();
	table = document.getElementById("customers");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[3];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}


function close() {
	document.getElementById('alert').style.display = 'none';
}

function validateemail() {
	var x = document.getElementById('email').value;
	var atposition = x.indexOf("@");
	var dotposition = x.lastIndexOf(".");
	if (atposition < 1 || dotposition < atposition + 2 || dotposition + 2 >= x.length) {
		$('#message').text('Please enter valid email address');
		$('#alert').css('display', 'block');
		setTimeout(function() { $('#alert').css('display', 'none') }, 2500);
		return false;
	}
	return true;
}


function validatePassword() {
	var password = document.getElementById('password').value;
	var confirmPassword = document.getElementById('confirmpassword').value;
	var upperCaseCount = 0;
	var specialCount = 0;
	for (var i = 0; i < password.length; i++) {
		if (isNaN(password.charAt(i)) && (password.charCodeAt(i) >= 65 && password.charCodeAt(i) <= 90)) {
			upperCaseCount++;
		} else if (((password.charCodeAt(i)) >= 32 && (password.charCodeAt(i)) <= 47) || ((password.charCodeAt(i) >= 58) && (password.charCodeAt(i)) <= 64) || ((password.charCodeAt(i) >= 91) && (password.charCodeAt(i)) <= 96)) {
			specialCount++;
		}
	}
	if (upperCaseCount == 0) {
		$('#message').text('password should have one uppercase character,one special character and must be greater than 8 characters');
		$('#alert').css('display', 'block');
		setTimeout(function() { $('#alert').css('display', 'none') }, 2500);
		return false;
	}
	if (specialCount == 0) {
		$('#message').text('password should have one uppercase character,one special character and must be greater than 8 characters');
		$('#alert').css('display', 'block');
		setTimeout(function() { $('#alert').css('display', 'none') }, 2500);

		return false;
	}
	if (password.length <= 8) {
		$('#message').text('password should have one uppercase character,one special character and must be greater than 8 characters');
		$('#alert').css('display', 'block');
		setTimeout(function() { $('#alert').css('display', 'none') }, 2500);
		return false;
	}
	if (confirmPassword != password) {
		$('#message').text('Passwords did not match');
		$('#alert').css('display', 'block');
		setTimeout(function() { $('#alert').css('display', 'none') }, 2500);
		return false;
	}
	return true;
}


function emptyChecker() {

	var fieldStatus = true;
	$('input[type="text"]').each(function() {
		if ($(this).val() == "") {
			fieldStatus = false;
		}
	});

	$('input[type="password"]').each(function() {
		if ($(this).val() == "") {
			fieldStatus = false;
		}
	});


	$('select').each(function() {
		if ($(this).val() == "") {
			fieldStatus = false;
		}
	});


	$('input[type="number"]').each(function() {
		if ($(this).val() == "") {
			fieldStatus = false;
		}
	});

	if (!fieldStatus) {
		return fieldStatus;
	}

	return true;
}





function emptyCheckerSignup() {
	var userName = document.getElementById('name').value;
	var phone = document.getElementById('phone').value;
	var email = document.getElementById('email').value;
	var password = document.getElementById('password').value;

	if (userName.length == 0 ||
		password.length == 0 || phone.length == 0 || email.length == 0) {
		$('#message').text('Fill all the fields');
		$('#alert').css('display', 'block');
		setTimeout(function() { $('#alert').css('display', 'none') }, 2500);
		return false;
	}
	return true;
}





function myFunction() {
	var element = document.body;
	if (localStorage.getItem('dark') === "dark") {
		localStorage.setItem('dark', "white");
		element.classList.toggle("dark-mode");
	} else {
		localStorage.setItem('dark', "dark");
		element.classList.toggle("dark-mode");
	}
}
