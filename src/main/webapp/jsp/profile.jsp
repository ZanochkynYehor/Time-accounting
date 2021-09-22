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
	<title><fmt:message key="profile.title"/></title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
	<link href="${appName}/css/profile.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/profile.js"></script>
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
					<li class="nav-item"><a class="nav-link active" aria-current="page" href="#"><fmt:message key="navbar.profile"/></a></li>
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

	<c:if test="${sessionScope.access == 'denied'}">
		<div class="text-center">
			<h5 class="text-danger">Access denied!</h5>
		</div>
		<c:remove var="access" scope="session"/>
	</c:if>
	<form action="${appName}/addActivitiesToUser" method="post">
		<div class="container">
			<div class="btn-group" role="group">
				<button class="btn btn-lg btn-dark" type="submit">Add</button>
				<button id="removeButton" class="btn btn-lg btn-dark" type="submit"
					formaction="${appName}/removeUserActivities" disabled>Remove</button>
				<button id="startButton" class="btn btn-lg btn-dark" type="submit"
					formaction="${appName}/startActivities" disabled>Start</button>
				<button id="finishButton" class="btn btn-lg btn-dark" type="submit"
					formaction="${appName}/finishActivities" disabled>Finish</button>
				<button class="btn btn-lg btn-dark" type="submit"
					formaction="${appName}/getUserActivities">
					<i class="bi-arrow-repeat"></i>
				</button>
			</div>
			<hr>
			<c:set var="userActivities" value="${sessionScope.userActivities}" />
			<c:set var="userActivitiesSize" value="${userActivities.size()}" />
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
				<c:choose>
					<c:when test="${userActivities != null}">
						<div class="panel-body table-responsive">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>#</th>
										<th>Activity name</th>
										<th>Category</th>
										<th>Start date and time</th>
										<th>Finish time</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="obj" items="${userActivities}" begin="${begin}" end="${end}">
										<c:choose>
											<c:when test="${obj.approved == 'No'}">
												<tr class="table-secondary">
													<td><input type="checkbox" name="chk_activity"
														value="${obj.getActivity().id}" disabled></td>
													<td>${obj.getActivity().name}</td>
													<td>${obj.getActivity().category}</td>
													<td>${obj.startDateTime}</td>
													<td>${obj.finishTime}</td>
												</tr>
											</c:when>
											<c:when
												test="${obj.startDateTime != '0000-00-00 00:00:00' && obj.finishTime == '00:00:00'}">
												<tr class="table-warning">
													<td><input type="checkbox" name="chk_activity"
														value="${obj.getActivity().id}"
														onclick="enableDisableButtons()"></td>
													<td>${obj.getActivity().name}</td>
													<td>${obj.getActivity().category}</td>
													<td>${obj.startDateTime}</td>
													<td>${obj.finishTime}</td>
												</tr>
											</c:when>
											<c:when test="${obj.finishTime != '00:00:00'}">
												<tr class="table-success">
													<td><input type="checkbox" name="chk_activity"
														value="${obj.getActivity().id}"
														onclick="enableDisableButtons()"></td>
													<td>${obj.getActivity().name}</td>
													<td>${obj.getActivity().category}</td>
													<td>${obj.startDateTime}</td>
													<td>${obj.finishTime}</td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr>
													<td><input type="checkbox" name="chk_activity"
														value="${obj.getActivity().id}"
														onclick="enableDisableButtons()"></td>
													<td>${obj.getActivity().name}</td>
													<td>${obj.getActivity().category}</td>
													<td>${obj.startDateTime}</td>
													<td>${obj.finishTime}</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</tbody>
							</table>
							<page:numberOfPages rowsPerPage="${rowsPerPage}" contentSize="${userActivitiesSize}" />
							<p:pagination jsp="profile.jsp?pageSize=${rowsPerPage}" pageNumber="${pageNumber}" numberOfPages="${sessionScope.numberOfPages}"></p:pagination>
						</div>
						<div class="row justify-content-center">
							<div class="col-md-1 ">
								<select class="form-select" id="pageSize" name="pageSize"
									onchange="refreshPage('${appName}/jsp/profile.jsp', 'pageNumber=1')">
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
					</c:when>
					<c:otherwise>
						<div class="text-center">
							<h5>You don't have any activities</h5>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form>
</body>
</html>