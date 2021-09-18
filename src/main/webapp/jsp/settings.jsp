<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>Settings</title>
	<meta charset="utf-8">
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
		crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
</head>	
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark sticky-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<a href="#" class="navbar-brand">Time accounting</a>
			</div>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
			aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link" href="${appName}/jsp/profile.jsp">Profile</a></li>
					<c:if test="${sessionScope.user.role == 'admin'}">
						<li class="nav-item"><a class="nav-link" href="${appName}/jsp/admin.jsp">Admin's page</a></li>
					</c:if>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> Login: ${sessionScope.user.login}</a>
						<ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item active" aria-current="page" href="#">Settings</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="${appName}/signout">Sign Out</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container">
		<h2>Settings</h2>
		<div class="row">
			<div class="col-lg-6">
				<p>Language</p>
			</div>
			<div class="col-lg-6">
				<select class="selectpicker">
					<option>English</option>
					<option>Русский</option>
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<input type="submit" class="btn btn-dark" value="Apply">
			</div>
		</div>
	</div>

</body>
</html>