function myFunction() {
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



function ValidateEmail() {
	var inputText = document.getElementById("email"); 
	var mailformat = /^w+([.-]?w+)*@w+([.-]?w+)*(.w{2,3})+$/;
	if (inputText.value.match(mailformat)) {
		alert("You have entered a valid email address!");    //The pop up alert for a valid email address
		return true;
	}
	else {
		alert("You have entered an invalid email address!");    //The pop up alert for an invalid email address
		return false;
	}
}