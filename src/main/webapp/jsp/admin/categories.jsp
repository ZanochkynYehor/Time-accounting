<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="page" uri="http://com.project.web.tags/pagination"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="messages" />

<html>
<head>
	<title><fmt:message key="categories.title"/></title>
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
				<a href="#" class="navbar-brand"><fmt:message key="navbar.appName"/></a>
			</div>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive"
			aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav me-auto">
					<li class="nav-item"><a class="nav-link" href="${appName}/jsp/profile.jsp"><fmt:message key="navbar.profile"/></a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getAllUsers"><fmt:message key="navbar.users"/></a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getAllActivities"><fmt:message key="navbar.activities"/></a></li>
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="#"><fmt:message key="navbar.categories"/></a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getRequestedActivities"><fmt:message key="navbar.requests"/></a></li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> ${sessionScope.user.login}</a>
						<ul class="dropdown-menu dropdown-menu-end dropdown-menu-dark" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="${appName}/jsp/settings.jsp"><fmt:message key="navbar.settings"/></a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="${appName}/signout"><fmt:message key="navbar.signout"/></a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<form action="${appName}/jsp/admin/newCategory.jsp" method="post">
		<div class="container">
			<div class="btn-group" role="group">
				<button class="btn btn-lg btn-dark" type="submit"><fmt:message key="categories.new"/></button>
				<button id="editButton" class="btn btn-lg btn-dark" type="submit" formaction="${appName}/editCategory" disabled><fmt:message key="categories.edit"/></button>
				<button id="removeButton" class="btn btn-lg btn-dark" type="submit" formaction="${appName}/removeCategories" disabled><fmt:message key="categories.remove"/></button>
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
								<th style="width: 50%"><fmt:message key="categories.category"/></th>
								<th><fmt:message key="categories.countOfActivities"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="category" items="${categories}" begin="${begin}" end="${end}">
								<tr>
									<td><input type="checkbox" name="chk_category" value="${category.id}" onclick="enableDisableButtons()"></td>
									<td>${category.name}</td>
									<td>${category.countOfActivities}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<page:numberOfPages rowsPerPage="${rowsPerPage}" contentSize="${categoriesSize}" />
					<p:pagination jsp="categories.jsp?pageSize=${rowsPerPage}" pageNumber="${pageNumber}" numberOfPages="${sessionScope.numberOfPages}"></p:pagination>
				</div>
				<p:pageSizeSelectpicker jsp="${appName}/jsp/admin/categories.jsp" rowsPerPage="${rowsPerPage}"></p:pageSizeSelectpicker>
			</div>
		</div>
	</form>
</body>
</html>