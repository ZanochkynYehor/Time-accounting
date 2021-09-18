function enableDisableButtons() {
	var list = document.getElementsByName("chk_userActivity");

	var flag = false;
	list.forEach(
		function(checkBox) {
			if(checkBox.checked == true) {
				document.getElementById("approveButton").disabled = false;
				document.getElementById("rejectButton").disabled = false;
				flag = true;
			}
		}
	);
	
	if(!flag) {
		document.getElementById("approveButton").disabled = true;
		document.getElementById("rejectButton").disabled = true;
	}
}

function refreshPage(dest, pageNumber) {
	var pageSize = document.getElementById("pageSize").value;
	document.location.href = dest + "?pageSize=" + pageSize + "&" + pageNumber;
}