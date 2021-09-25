function validation(login, pass) {
	var loginMessage = document.getElementById('loginMessage');
	var passwordMessage = document.getElementById('passwordMessage');
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	var firstCheck = false;
	var secondCheck = false;
	
	if (login.value != "") {
		login.style.backgroundColor = goodColor;
		loginMessage.style.color = goodColor;
		loginMessage.setAttribute("hidden", "hidden");
		firstCheck = true;
	} else {
		login.style.backgroundColor = badColor;
		loginMessage.style.color = badColor;
		loginMessage.removeAttribute("hidden");
		firstCheck = false;
	}
	if (pass.value != "") {
		pass.style.backgroundColor = goodColor;
		passwordMessage.style.color = goodColor;
		passwordMessage.setAttribute("hidden", "hidden");
		secondCheck = true;
	} else {
		pass.style.backgroundColor = badColor;
		passwordMessage.style.color = badColor;
		passwordMessage.removeAttribute("hidden");
		secondCheck = false;
	}
	if (firstCheck && secondCheck) {
		return true;
	}
	return false;
}

function changeLanguage(dest) {
	var language = document.getElementById("lang").value;
	document.location.href = dest + "?language=" + language;
}