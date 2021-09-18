function validation() {
	var category = document.getElementById('category');
	var categoryNameMessage = document.getElementById('categoryNameMessage');
	var goodColor = "#66cc66";
	var badColor = "#ff6666";
	
	if (category.value != "") {
		category.style.backgroundColor = goodColor;
		categoryNameMessage.style.color = goodColor;
		categoryNameMessage.innerHTML = "All good!";
		return true;
	} else {
		category.style.backgroundColor = badColor;
		categoryNameMessage.style.color = badColor;
		categoryNameMessage.innerHTML = "Category name cannot be empty!";
		return false;
	}
}