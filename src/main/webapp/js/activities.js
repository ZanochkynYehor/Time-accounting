function enableDisableButtons() {
	var list = document.getElementsByName("chk_activity");

	var flag = false;
	var count = 0;
	list.forEach(
		function(checkBox) {
			if(checkBox.checked == true) {
				document.getElementById("editButton").disabled = false;
				document.getElementById("removeButton").disabled = false;
				count++;
				flag = true;
			}
		}
	);
	if(count > 1) {
		document.getElementById("editButton").disabled = true;
	}
	
	if(!flag) {
		document.getElementById("editButton").disabled = true;
		document.getElementById("removeButton").disabled = true;
	}
}

function goToSortServlet(dest) {
	var option = document.getElementById("sort").value;
	document.location.href = dest + "?sort=" + option;
}

function goToFilterByCategoryServlet(dest) {
	var category = document.getElementById("category").value;
	document.location.href = dest + "?category=" + category;
}

function refreshPage(dest, pageNumber) {
	var pageSize = document.getElementById("pageSize").value;
	document.location.href = dest + "?pageSize=" + pageSize + "&" + pageNumber;
}