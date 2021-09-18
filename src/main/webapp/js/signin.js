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
		loginMessage.innerHTML = "All good!";
		firstCheck = true;
	} else {
		login.style.backgroundColor = badColor;
		loginMessage.style.color = badColor;
		loginMessage.innerHTML = "Login field cannot be empty!";
		firstCheck = false;
	}
	if (pass.value != "") {
		pass.style.backgroundColor = goodColor;
		passwordMessage.style.color = goodColor;
		passwordMessage.innerHTML = "All good!";
		secondCheck = true;
	} else {
		pass.style.backgroundColor = badColor;
		passwordMessage.style.color = badColor;
		passwordMessage.innerHTML = "Password field cannot be empty!";
		secondCheck = false;
	}
	if (firstCheck && secondCheck) {
		return true;
	} else {
		return false;
	}
}