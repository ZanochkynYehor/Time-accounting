function enableDisableButtons() {
	var list = document.getElementsByName("chk_user");

	var flag = false;
	var count = 0;
	list.forEach(
		function(checkBox) {
			if(checkBox.checked == true) {
				document.getElementById("statButton").disabled = false;
				document.getElementById("editButton").disabled = false;
				document.getElementById("removeButton").disabled = false;
				count++;
				flag = true;
			}
		}
	);
	if(count > 1) {
		document.getElementById("statButton").disabled = true;
		document.getElementById("editButton").disabled = true;
	}
	
	if(!flag) {
		document.getElementById("statButton").disabled = true;
		document.getElementById("editButton").disabled = true;
		document.getElementById("removeButton").disabled = true;
	}
}

function refreshPage(dest, pageNumber) {
	var pageSize = document.getElementById("pageSize").value;
	document.location.href = dest + "?pageSize=" + pageSize + "&" + pageNumber;
}