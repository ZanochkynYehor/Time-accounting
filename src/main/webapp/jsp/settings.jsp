<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
	<title><fmt:message key="settings.title"/></title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link href="${appName}/css/settings.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/settings.js"></script>
</head>	
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark sticky-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a href="#" class="navbar-brand"><fmt:message key="navbar.appName"/></a>
			</div>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
			aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link" href="${appName}/jsp/profile.jsp"><fmt:message key="navbar.profile"/></a></li>
					<c:if test="${sessionScope.user.role == 'admin'}">
						<li class="nav-item"><a class="nav-link" href="${appName}/getAllUsers"><fmt:message key="navbar.users"/></a></li>
						<li class="nav-item"><a class="nav-link" href="${appName}/getAllActivities"><fmt:message key="navbar.activities"/></a></li>
						<li class="nav-item"><a class="nav-link" href="${appName}/getAllCategories"><fmt:message key="navbar.categories"/></a></li>
						<li class="nav-item"><a class="nav-link" href="${appName}/getRequestedActivities"><fmt:message key="navbar.requests"/></a></li>
					</c:if>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> ${sessionScope.user.login}</a>
						<ul class="dropdown-menu dropdown-menu-end dropdown-menu-dark" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="#"><fmt:message key="navbar.settings"/></a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="${appName}/signout"><fmt:message key="navbar.signout"/></a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<p><fmt:message key="settings.language"/></p>
			</div>
			<div class="col-md-2">
				<select class="form-select" id="lang" name="lang" onchange="changeLanguage('${appName}/jsp/settings.jsp')">
					<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
					<option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<button class="btn btn-md btn-dark" type="button" data-bs-toggle="collapse" data-bs-target="#changeUserLogin"
					aria-expanded="false" aria-controls="changeUserLogin"><fmt:message key="settings.changeLogin"/>
				</button>
			</div>
			<div class="col-md-8">
				<div class="collapse" id="changeUserLogin">
					<div class="row">
						<div class="col-md-6">
							<form action="${appName}/changeUserLogin" method="post" id="formLogin" onsubmit="return validateLogin()">
								<div class="form-floating">
									<input name="login" class="form-control" id="inputLogin" placeholder="Login">
									<label for="inputLogin"><fmt:message key="settings.login"/></label>
									<span hidden="hidden" id="loginMessage" class="loginMessage"><fmt:message key="settings.span.login"/></span>	
								</div>
							</form>
						</div>
						<div class="col-md-2">
							<button class="btn btn-md btn-dark" type="submit" form="formLogin"><fmt:message key="settings.change"/></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<c:if test="${sessionScope.loginChanged == true}">
				<h5 class="text-success text-center"><fmt:message key="settings.loginChangedSuccessfully"/></h5>
				<c:remove var="loginChanged" scope="session"/>
			</c:if>
		</div>
		<div class="row">
			<div class="col-md-4">
				<button class="btn btn-md btn-dark" type="button" data-bs-toggle="collapse" data-bs-target="#changeUserPassword"
					aria-expanded="false" aria-controls="changeUserPassword"><fmt:message key="settings.changePassword"/>
				</button>
			</div>
			<div class="col-md-8">
				<div class="collapse" id="changeUserPassword">
					<div class="row">
						<div class="col-md-6">
							<form action="${appName}/changeUserPassword" method="post" id="formPassword" onsubmit="return validatePassword()">
								<div class="form-floating">
									<input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password">
									<label for="inputPassword"><fmt:message key="settings.password"/></label>
									<span hidden="hidden" id="passwordMessage" class="passwordMessage"><fmt:message key="settings.span.password"/></span>	
								</div>
							</form>
						</div>
						<div class="col-md-2">
							<button class="btn btn-md btn-dark" type="submit" form="formPassword"><fmt:message key="settings.change"/></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<c:if test="${sessionScope.passwordChanged == true}">
				<h5 class="text-success text-center"><fmt:message key="settings.passwordChangedSuccessfully"/></h5>
				<c:remove var="passwordChanged" scope="session"/>
			</c:if>
		</div>
	</div>
</body>
</html>