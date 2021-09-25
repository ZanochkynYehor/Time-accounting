<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
	<title><fmt:message key="newCategory.title"/></title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<link href="${appName}/css/newCategory.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/newCategory.js"></script>
</head>
<body class="text-center">
	<form class="form-newActivity" name="form1" action="${appName}/newCategory" method="post"
		onsubmit="return validation()">
		<c:if test="${sessionScope.existingCategory == true}">
			<h5 class="text-danger"><fmt:message key="newCategory.alreadyExist"/></h5>
			<c:remove var="existingCategory" scope="session"/>
		</c:if>
		<h1 class="h3 mb-3 fw-normal"><fmt:message key="newCategory.label"/></h1>
		<div class="form-floating">
			<input name="category" class="form-control" id="category" placeholder="Category name">
			<label for="category"><fmt:message key="newCategory.categoryName"/></label>
			<span hidden="hidden" id="categoryNameMessage" class="categoryNameMessage"><fmt:message key="newCategory.span.emptyCategoryNameField"/></span>
		</div>
		<button class="w-100 btn btn-lg btn-dark" type="submit"><fmt:message key="newCategory.submit"/></button>	
	</form>
</body>
</html>