<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">	
	<link href="${appName}/css/signup.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/signup.js"></script>
	<title>Sign up</title>
</head>
<body class="text-center">
	<form class="form-signup" name="form1" action="${appName}/signup" method="post"
	onsubmit="return validation(document.form1.login, document.form1.password, document.form1.confirmPassword)">
		<h1 class="h3 mb-3 fw-normal">Sign up</h1>
		<div class="form-floating">
			<input name="login" class="form-control" id="inputLogin1" placeholder="Login">
			<label for="inputLogin1">Login</label>
			<span id="loginMessage" class="loginMessage"></span>
		</div>
		<div class="form-floating">
			<input name ="password" type="password" class="form-control" id="inputPassword1" placeholder="Password">
			<label for="inputPassword1">Password</label>
			<span id="passwordMessage" class="passwordMessage"></span>
		</div>
		<div class="form-floating">
			<input name ="confirmPassword" type="password" class="form-control" id="inputPassword2" placeholder="Confirm password">
			<label for="inputPassword2">Confirm password</label>
			<span id="confirmMessage" class="confirmMessage"></span>
		</div>
		<button class="w-100 btn btn-lg btn-dark" type="submit">Sign up</button>
		<hr>
		<a href="${appName}/signin.jsp" class="link-dark">Sign in</a>
		<c:if test="${sessionScope.signup == 'loginIsTaken'}">
			<h5 class="text-danger">Login is taken. Try another.</h5>
			<c:remove var="signup" scope="session"/>
		</c:if>
	</form>
</body>
</html>