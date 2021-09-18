function refreshPage(dest, pageNumber) {
	var pageSize = document.getElementById("pageSize").value;
	document.location.href = dest + "?pageSize=" + pageSize + "&" + pageNumber;
}