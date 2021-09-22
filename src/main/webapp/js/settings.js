function changeLanguage(dest) {
	var language = document.getElementById("lang").value;
	document.location.href = dest + "?language=" + language;
}