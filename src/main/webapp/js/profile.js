function enableDisableButtons() {
	var list = document.getElementsByName("chk_activity");

	var flag = false;
	list.forEach(
		function(checkBox) {
			if(checkBox.checked == true) {
				document.getElementById("removeButton").disabled = false;
				document.getElementById("startButton").disabled = false;
				document.getElementById("finishButton").disabled = false;
				flag = true;
			}
		}
	);
	if(!flag) {
		document.getElementById("removeButton").disabled = true;
		document.getElementById("startButton").disabled = true;
		document.getElementById("finishButton").disabled = true;
	}
}

function refreshPage(dest, pageNumber) {
	var pageSize = document.getElementById("pageSize").value;
	document.location.href = dest + "?pageSize=" + pageSize + "&" + pageNumber;
}