function validation(login, pass1, pass2) {
	var loginMessage = document.getElementById('loginMessage');
	var passwordMessage = document.getElementById('passwordMessage');
	var confirmMessage = document.getElementById('confirmMessage');
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	var loginRegex = /^([a-zA-Z0-9]{6,20})$/;
	var passRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{6,20}$/;
	var firstCheck = false;
	var secondCheck = false;
	var thirdCheck = false;
	
	if (login.value.match(loginRegex)) {
		login.style.backgroundColor = goodColor;
		loginMessage.style.color = goodColor;
		loginMessage.innerHTML = "All good!";
		firstCheck = true;
	} else {
		login.style.backgroundColor = badColor;
		loginMessage.style.color = badColor;
		loginMessage.innerHTML = "From 6 to 20 characters required!";
		firstCheck = false;
	}
	if (pass1.value.match(passRegex)) {
		pass1.style.backgroundColor = goodColor;
		passwordMessage.style.color = goodColor;
		passwordMessage.innerHTML = "All good!";
		secondCheck = true;
	} else {
		pass1.style.backgroundColor = badColor;
		passwordMessage.style.color = badColor;
		passwordMessage.innerHTML = "From 6 to 20 characters, at least one uppercase letter, one lowercase letter and one number required!";
		secondCheck = false;
	}
	if (pass1.value == pass2.value) {
		pass2.style.backgroundColor = goodColor;
		confirmMessage.style.color = goodColor;
		confirmMessage.innerHTML = "Passwords match!";
		thirdCheck = true;
	} else {
		pass2.style.backgroundColor = badColor;
		confirmMessage.style.color = badColor;
		confirmMessage.innerHTML = "Passwords do not match!";
		thirdCheck = false;
	}
	if (firstCheck && secondCheck && thirdCheck) {
		return true;
	} else {
		return false;
	}
}