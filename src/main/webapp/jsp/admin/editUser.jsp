<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
	<title><fmt:message key="editUser.title"/></title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<link href="${appName}/css/newCategory.css" rel="stylesheet" type="text/css">
</head>
<body class="text-center">
	<form class="form-newActivity" name="form1" action="${appName}/editUser" method="post"
		onsubmit="return validation()">
		<h1 class="h3 mb-3 fw-normal"><fmt:message key="editUser.label"/></h1>

		<h5 class="text-warning"><fmt:message key="editUser.login"/> - ${sessionScope.userToEdit.login}</h5>
		<h5 class="text-warning"><fmt:message key="editUser.role"/> - ${sessionScope.userToEdit.role}</h5>
		
		<select class="form-select form-select-lg" id="role" name="role">
			<option value="2" selected><fmt:message key="editUser.user"/></option>
			<option value="1"><fmt:message key="editUser.admin"/></option>
		</select>

		<button class="w-100 btn btn-lg btn-dark" type="submit"><fmt:message key="editUser.submit"/></button>	
	</form>
</body>
</html>