function validation() {
	var category = document.getElementById('category');
	var categoryNameMessage = document.getElementById('categoryNameMessage');
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	
	if (category.value != "") {
		category.style.backgroundColor = goodColor;
		categoryNameMessage.style.color = goodColor;
		categoryNameMessage.setAttribute("hidden", "hidden");
		return true;
	} else {
		category.style.backgroundColor = badColor;
		categoryNameMessage.style.color = badColor;
		categoryNameMessage.removeAttribute("hidden");
		return false;
	}
}