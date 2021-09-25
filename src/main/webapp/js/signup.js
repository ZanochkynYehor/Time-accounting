function validation(login, pass1, pass2) {
	var loginMessage = document.getElementById('loginMessage');
	var passwordMessage = document.getElementById('passwordMessage');
	var confirmMessage = document.getElementById('confirmMessage');
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	var loginRegex = /^([a-zA-Z\u0430-\u044f\u0410-\u042F0-9]{6,20})$/;
	var passRegex = /^(?=.*\d)(?=.*[a-z\u0430-\u044f])(?=.*[A-Z\u0410-\u042F])[a-zA-Z\u0430-\u044f\u0410-\u042F0-9]{6,20}$/;
	var firstCheck = false;
	var secondCheck = false;
	var thirdCheck = false;
	
	if (login.value.match(loginRegex)) {
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
	if (pass1.value.match(passRegex)) {
		pass1.style.backgroundColor = goodColor;
		passwordMessage.style.color = goodColor;
		passwordMessage.setAttribute("hidden", "hidden");
		secondCheck = true;
	} else {
		pass1.style.backgroundColor = badColor;
		passwordMessage.style.color = badColor;
		passwordMessage.removeAttribute("hidden");
		secondCheck = false;
	}
	if (pass1.value == pass2.value) {
		pass2.style.backgroundColor = goodColor;
		confirmMessage.style.color = goodColor;
		confirmMessage.setAttribute("hidden", "hidden");
		thirdCheck = true;
	} else {
		pass2.style.backgroundColor = badColor;
		confirmMessage.style.color = badColor;
		confirmMessage.removeAttribute("hidden");
		thirdCheck = false;
	}
	if (firstCheck && secondCheck && thirdCheck) {
		return true;
	}
	return false;
}

function changeLanguage(dest) {
	var language = document.getElementById("lang").value;
	document.location.href = dest + "?language=" + language;
}