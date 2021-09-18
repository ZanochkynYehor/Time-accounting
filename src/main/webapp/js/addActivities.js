function enableDisableButton() {
	var list = document.getElementsByName("chk_activity");

	var flag = false;
	list.forEach(
		function(checkBox) {
			if(checkBox.checked == true) {
				document.getElementById("addButton").disabled = false;
				flag = true;
			}
		}
	);
	if(!flag) {
		document.getElementById("addButton").disabled = true;
	}
}

function refreshPage(dest, pageNumber) {
	var pageSize = document.getElementById("pageSize").value;
	document.location.href = dest + "?pageSize=" + pageSize + "&" + pageNumber;
}