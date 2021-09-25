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
	<title><fmt:message key="activities.title"/></title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<link href="${appName}/css/activities.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/activities.js"></script>
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
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="#"><fmt:message key="navbar.activities"/></a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getAllCategories"><fmt:message key="navbar.categories"/></a></li>
					<li class="nav-item"><a class="nav-link" href="${appName}/getRequestedActivities"><fmt:message key="navbar.requests"/></a></li>
				</ul>
				<ul class="navbar-nav ms-auto">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> ${sessionScope.user.login}</a>
						<ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
							<li><a class="dropdown-item" href="${appName}/jsp/settings.jsp"><fmt:message key="navbar.settings"/></a></li>
							<li><hr class="dropdown-divider"></li>
							<li><a class="dropdown-item" href="${appName}/signout"><fmt:message key="navbar.signout"/></a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<form action="${appName}/newActivity" method="post">
		<div class="container">
			<div class="row">
				<div class="col-md-4">
					<div class="btn-group" role="group">
						<button class="btn btn-lg btn-dark" type="submit"><fmt:message key="activities.new"/></button>
						<button id="editButton" class="btn btn-lg btn-dark" type="submit"
							formaction="${appName}/editActivity" disabled><fmt:message key="activities.edit"/></button>
						<button id="removeButton" class="btn btn-lg btn-dark"
							type="submit" formaction="${appName}/removeActivities" disabled><fmt:message key="activities.remove"/></button>
						<button class="btn btn-lg btn-dark" type="submit"
							formaction="${appName}/getAllActivities">
							<i class="bi-arrow-repeat"></i>
						</button>
					</div>
				</div>
				
				<div class="col-md-2 offset-md-4">
					<label for="category"><fmt:message key="activities.category"/></label>
					<select class="form-select"
						id="category" name="category" onfocus='this.size=4;'
						onblur='this.size=1;'
						onchange="this.size=1; this.blur(); goToFilterByCategoryServlet('${appName}/filterByCategory')">
						<c:choose>
							<c:when test="${sessionScope.categoryOption == 'all'}">
								<option value="all" selected><fmt:message key="activities.categories.all"/></option>
								<c:forEach var="category" items="${sessionScope.categories}">
									<option value="${category.id}">${category.name}</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="all"><fmt:message key="activities.categories.all"/></option>
								<c:forEach var="category" items="${sessionScope.categories}">
									<c:choose>
										<c:when test="${sessionScope.categoryOption == category.id}">
											<option value="${category.id}" selected>${category.name}</option>
										</c:when>
										<c:otherwise>
											<option value="${category.id}">${category.name}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</select>
				</div>
				<div class="col-md-2">
					<label for="sort"><fmt:message key="activities.sortBy"/></label>
					<select id="sort" class="form-select" onchange="goToSortServlet('${appName}/sort')">
						<c:set var="sortOption" value="${sessionScope.sortOption}" />
						<option value="default" ${sortOption == 'default' ? 'selected' : ''}><fmt:message key="activities.sortBy.default"/></option>
						<option value="name asc" ${sortOption == 'name asc' ? 'selected' : ''}><fmt:message key="activities.sortBy.name.asc"/></option>
						<option value="name desc" ${sortOption == 'name desc' ? 'selected' : ''}><fmt:message key="activities.sortBy.name.desc"/></option>
						<option value="category asc" ${sortOption == 'category asc' ? 'selected' : ''}><fmt:message key="activities.sortBy.category.asc"/></option>
						<option value="category desc" ${sortOption == 'category desc' ? 'selected' : ''}><fmt:message key="activities.sortBy.category.desc"/></option>
						<option value="users count asc" ${sortOption == 'users count asc' ? 'selected' : ''}><fmt:message key="activities.sortBy.usersCount.asc"/></option>
						<option value="users count desc" ${sortOption == 'users count desc' ? 'selected' : ''}><fmt:message key="activities.sortBy.usersCount.desc"/></option>
					</select>
				</div>
			</div>
			<hr>
			<c:set var="activities" value="${sessionScope.activities}" />
			<c:set var="activitiesSize" value="${activities.size()}" />
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
								<th><fmt:message key="activities.activity"/></th>
								<th><fmt:message key="activities.category"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="activity" items="${activities}" begin="${begin}" end="${end}">
								<tr>
									<td><input type="checkbox" name="chk_activity" value="${activity.id}" onclick="enableDisableButtons()"></td>
									<td>${activity.name}</td>
									<td>${activity.category}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<page:numberOfPages rowsPerPage="${rowsPerPage}" contentSize="${activitiesSize}" />
					<p:pagination jsp="activities.jsp?pageSize=${rowsPerPage}" pageNumber="${pageNumber}" numberOfPages="${sessionScope.numberOfPages}"></p:pagination>
				</div>
				<div class="row justify-content-center">
					<div class="col-md-1 ">
						<select class="form-select" id="pageSize" name="pageSize"
							onchange="refreshPage('${appName}/jsp/admin/activities.jsp', 'pageNumber=1')">
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