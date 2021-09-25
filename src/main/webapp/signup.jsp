<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">	
	<link href="${appName}/css/signup.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/signup.js"></script>
	<title><fmt:message key="signup.title"/></title>
</head>
<body class="text-center">
	<form class="form-signup" name="form1" action="${appName}/signup" method="post"
	onsubmit="return validation(document.form1.login, document.form1.password, document.form1.confirmPassword)">
		<c:if test="${sessionScope.signup == 'loginIsTaken'}">
			<h5 class="text-danger"><fmt:message key="signup.loginIsTaken"/></h5>
			<c:remove var="signup" scope="session"/>
		</c:if>
		<h1 class="h3 mb-3 fw-normal"><fmt:message key="signup.label"/></h1>
		<div class="form-floating">
			<input name="login" class="form-control" id="inputLogin1" placeholder="Login">
			<label for="inputLogin1"><fmt:message key="signup.login"/></label>
			<span hidden="hidden" id="loginMessage" class="loginMessage"><fmt:message key="signup.span.login"/></span>
		</div>
		<div class="form-floating">
			<input name ="password" type="password" class="form-control" id="inputPassword1" placeholder="Password">
			<label for="inputPassword1"><fmt:message key="signup.password"/></label>
			<span hidden="hidden" id="passwordMessage" class="passwordMessage"><fmt:message key="signup.span.password"/></span>
		</div>
		<div class="form-floating">
			<input name ="confirmPassword" type="password" class="form-control" id="inputPassword2" placeholder="Confirm password">
			<label for="inputPassword2"><fmt:message key="signup.confirmPassword"/></label>
			<span hidden="hidden" id="confirmMessage" class="confirmMessage"><fmt:message key="signup.span.confirmPassword"/></span>
		</div>
		<button class="w-100 btn btn-lg btn-dark" type="submit"><fmt:message key="signup.submit"/></button>
		<hr>
		<a href="${appName}/signin.jsp" class="link-dark"><fmt:message key="signup.signin"/></a>
		<br>
		<br>
		<div class="row justify-content-center">
			<div class="col-md-5">
				<select class="form-select" id="lang" name="lang" onchange="changeLanguage('${appName}/signup.jsp')">
					<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
					<option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
				</select>
			</div>
		</div>
	</form>
</body>
</html>