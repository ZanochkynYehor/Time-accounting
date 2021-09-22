<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
	<meta charset="utf-8">
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
		crossorigin="anonymous">
	<link href="${appName}/css/signin.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/signin.js"></script>
	<title>Sign in</title>
</head>
<body class="text-center">
	<form class="form-signin" name="form1" action="${appName}/signin" method="post"
	onsubmit="return validation(document.form1.login, document.form1.password)">
		<c:if test="${sessionScope.signup == 'signupSuccessfully'}">
			<h5 class="text-success">Sign up completed successfully</h5>
			<c:remove var="signup" scope="session"/>
		</c:if>
		<h1 class="h3 mb-3 fw-normal"><fmt:message key="msg.signin" /></h1>
		<div class="form-floating">
			<input name="login" class="form-control" id="inputLogin1" placeholder="Login">
			<label for="inputLogin1">Login</label>
			<span id="loginMessage" class="loginMessage"></span>
		</div>
		<c:if test="${sessionScope.signin == 'wrongLogin'}">
			<p class="text-danger">There is no such user</p>
			<c:remove var="signin" scope="session"/>
		</c:if>
		<div class="form-floating">
			<input name ="password" type="password" class="form-control" id="inputPassword1" placeholder="Password">
			<label for="inputPassword1">Password</label>
			<span id="passwordMessage" class="passwordMessage"></span>
		</div>
		<c:if test="${sessionScope.signin == 'wrongPassword'}">
			<p class="text-danger">Wrong password</p>
			<c:remove var="signin" scope="session"/>
		</c:if>
		<button class="w-100 btn btn-lg btn-dark" type="submit">Sign in</button>
		<hr>
		<a href="${appName}/signup.jsp" class="link-dark">Sign up</a>
	</form>
</body>
</html>