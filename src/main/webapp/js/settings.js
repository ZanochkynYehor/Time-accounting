function changeLanguage(dest) {
	var language = document.getElementById("lang").value;
	document.location.href = dest + "?language=" + language;
}

function validateLogin() {
	var login = document.getElementById('inputLogin');
	var loginMessage = document.getElementById('loginMessage');
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	
	if (login.value != "") {
		login.style.backgroundColor = goodColor;
		loginMessage.style.color = goodColor;
		loginMessage.setAttribute("hidden", "hidden");
		return true;
	}
	login.style.backgroundColor = badColor;
	loginMessage.style.color = badColor;
	loginMessage.removeAttribute("hidden");
	return false;
}

function validatePassword() {
	var password = document.getElementById('inputPassword');
	var passwordMessage = document.getElementById('passwordMessage');
	var passRegex = /^(?=.*\d)(?=.*[a-z\u0430-\u044f])(?=.*[A-Z\u0410-\u042F])[a-zA-Z\u0430-\u044f\u0410-\u042F0-9]{6,20}$/;
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	
	if (password.value.match(passRegex)) {
		password.style.backgroundColor = goodColor;
		passwordMessage.style.color = goodColor;
		passwordMessage.setAttribute("hidden", "hidden");
		return true;
	}
	password.style.backgroundColor = badColor;
	passwordMessage.style.color = badColor;
	passwordMessage.removeAttribute("hidden");
	return false;
}