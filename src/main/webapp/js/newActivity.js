function validation() {
	var activityName = document.getElementById('activityName');
	var activityNameMessage = document.getElementById('activityNameMessage');
	var category = document.getElementById('category');
	var selectMessage = document.getElementById('selectMessage');
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	var firstCheck = false;
	var secondCheck = false;
	
	if (activityName.value != "") {
		activityName.style.backgroundColor = goodColor;
		activityNameMessage.style.color = goodColor;
		activityNameMessage.setAttribute("hidden", "hidden");
		firstCheck = true;
	} else {
		activityName.style.backgroundColor = badColor;
		activityNameMessage.style.color = badColor;
		activityNameMessage.removeAttribute("hidden");
		firstCheck = false;
	}
	if (category.value == "") {
		category.style.backgroundColor = badColor;
		selectMessage.style.color = badColor;
		selectMessage.removeAttribute("hidden");
		secondCheck = false;
	} else {
		category.style.backgroundColor = goodColor;
		selectMessage.style.color = goodColor;
		selectMessage.setAttribute("hidden", "hidden");
		secondCheck = true;
	}
	
	if(secondCheck && firstCheck) {
		return true;
	}
	return false;
}