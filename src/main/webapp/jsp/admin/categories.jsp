<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="page" uri="http://com.project.web.tags/pagination"%>

<html>
<head>
	<title>Categories</title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<link href="${appName}/css/categories.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/categories.js"></script>
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
					<li class="nav-item"><a class="nav-link" href="#">Admin: </a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getAllUsers">Users</a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getAllActivities">Activities</a></li>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="#">Categories</a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getRequestedActivities">Requests</a></li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> ${sessionScope.user.login}</a>
						<ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="${appName}/jsp/settings.jsp">Settings</a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="${appName}/signout">Sign Out</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<form action="${appName}/jsp/admin/newCategory.jsp" method="post">
		<div class="container">
			<div class="btn-group" role="group">
				<button class="btn btn-lg btn-dark" type="submit">New</button>
				<button id="editButton" class="btn btn-lg btn-dark" type="submit" formaction="${appName}/editCategory" disabled>Edit</button>
				<button id="removeButton" class="btn btn-lg btn-dark" type="submit" formaction="${appName}/removeCategories" disabled>Remove</button>
				<button class="btn btn-lg btn-dark" type="submit" formaction="${appName}/getAllCategories"><i class="bi-arrow-repeat"></i></button>
			</div>
			<hr>
			<c:set var="categories" value="${sessionScope.categories}" />
			<c:set var="categoriesSize" value="${categories.size()}" />
			<c:choose>
				<c:when test="${param.pageSize == null}">
					<c:set var="rowsPerPage" value="10" />
				</c:when>
				<c:otherwise>
					<c:set var="rowsPerPage" value="${param.pageSize}" />
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${param.pageNumber == null}">
					<c:set var="pageNumber" value="1" />
				</c:when>
				<c:otherwise>
					<c:set var="pageNumber" value="${param.pageNumber}" />
				</c:otherwise>
			</c:choose>
			<c:set var="begin" value="${pageNumber*rowsPerPage-rowsPerPage}" />
			<c:set var="end" value="${pageNumber*rowsPerPage-1}" />
			<div class="panel panel-default">
				<div class="panel-body table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>#</th>
								<th>Category</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="category" items="${categories}" begin="${begin}" end="${end}">
								<tr>
									<td><input type="checkbox" name="chk_category" value="${category.id}" onclick="enableDisableButtons()"></td>
									<td>${category.name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<page:numberOfPages rowsPerPage="${rowsPerPage}" contentSize="${categoriesSize}" />
					<p:pagination jsp="categories.jsp?pageSize=${rowsPerPage}" pageNumber="${pageNumber}" numberOfPages="${sessionScope.numberOfPages}"></p:pagination>
				</div>
				<div class="row justify-content-center">
					<div class="col-md-1 ">
						<select class="form-select" id="pageSize" name="pageSize"
							onchange="refreshPage('${appName}/jsp/admin/categories.jsp', 'pageNumber=1')">
							<c:choose>
								<c:when test="${rowsPerPage == 3}">
									<option value="3" selected>3</option>
								</c:when>
								<c:otherwise>
									<option value="3">3</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${rowsPerPage == 5}">
									<option value="5" selected>5</option>
								</c:when>
								<c:otherwise>
									<option value="5">5</option>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${rowsPerPage == 10}">
									<option value="10" selected>10</option>
								</c:when>
								<c:otherwise>
									<option value="10">10</option>
								</c:otherwise>
							</c:choose>
						</select>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>