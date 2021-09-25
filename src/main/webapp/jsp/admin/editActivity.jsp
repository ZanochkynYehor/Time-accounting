<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
	<title><fmt:message key="editActivity.title"/></title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<link href="${appName}/css/newActivity.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/newActivity.js"></script>
</head>
<body class="text-center">
	<form class="form-newActivity" name="form1" action="${appName}/editActivity" method="post"
		onsubmit="return validation()">
		<c:if test="${sessionScope.existingActivity == true}">
			<h5 class="text-danger"><fmt:message key="editActivity.alreadyExist"/></h5>
			<c:remove var="existingActivity" scope="session"/>
		</c:if>
		<h1 class="h3 mb-3 fw-normal"><fmt:message key="editActivity.label"/></h1>
		<h5 class="text-warning"><fmt:message key="editActivity.name"/> - ${sessionScope.activityToEdit.name}</h5>
		<h5 class="text-warning"><fmt:message key="editActivity.categoryName"/> - ${sessionScope.activityToEdit.category}</h5>
		<div class="form-floating">
			<input name="activity" class="form-control" id="activityName" placeholder="Activity name">
			<label for="activityName"><fmt:message key="editActivity.activityName"/></label>
			<span hidden="hidden" id="activityNameMessage" class="activityNameMessage"><fmt:message key="editActivity.span.emptyActivityNameField"/></span>
		</div>
		<div>
			<select class="form-select form-select-lg" id="category" name="category" onfocus='this.size=4;' onblur='this.size=1;' onchange='this.size=1; this.blur();'>
				<option value="" selected><fmt:message key="editActivity.category"/></option>
				<c:forEach var="category" items="${sessionScope.categories}">
					<option value="${category.id}">${category.name}</option>
				</c:forEach>
			</select>
			<span hidden="hidden" id="selectMessage" class="selectMessage"><fmt:message key="editActivity.span.chooseCategory"/></span>
		</div>
		
		<a href="${appName}/jsp/admin/newCategory.jsp" class="link-dark"><fmt:message key="editActivity.newCategory"/></a>
		<button class="w-100 btn btn-lg btn-dark" type="submit"><fmt:message key="editActivity.submit"/></button>	
	</form>
</body>
</html>