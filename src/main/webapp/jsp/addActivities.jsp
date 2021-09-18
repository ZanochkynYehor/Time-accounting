<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="p" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="page" uri="http://com.project.web.tags/pagination"%>

<html>
<head>
	<title>Add activities</title>
	<meta charset="utf-8">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
	<link href="${appName}/css/addActivities.css" rel="stylesheet" type="text/css">
	<script src="${appName}/js/addActivities.js"></script>
</head>
<body class="text-center">
	<c:set var="activitiesUserDoesNotHave" value="${sessionScope.activitiesUserDoesNotHave}" />
	<c:set var="activitiesUserDoesNotHaveSize" value="${activitiesUserDoesNotHave.size()}" />
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
	<form action="${appName}/addActivitiesToUser" method="post">
		<div class="container">
			<div class="panel panel-default">
				<c:choose>
					<c:when test="${activitiesUserDoesNotHave != null}">
						<div class="panel-body table-responsive">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th>#</th>
										<th>Activity name</th>
										<th>Category</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="activity" items="${activitiesUserDoesNotHave}"
										begin="${begin}" end="${end}">
										<tr>
											<td><input type="checkbox" name="chk_activity"
												value="${activity.id}" onclick="enableDisableButton()"></td>
											<td>${activity.name}</td>
											<td>${activity.category}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<page:numberOfPages rowsPerPage="${rowsPerPage}" contentSize="${activitiesUserDoesNotHaveSize}" />
							<p:pagination jsp="addActivities.jsp?pageSize=${rowsPerPage}" pageNumber="${pageNumber}" numberOfPages="${sessionScope.numberOfPages}"></p:pagination>
						</div>
						<div class="row justify-content-center">
							<div class="col-md-1 ">
								<select class="form-select" id="pageSize" name="pageSize"
									onchange="refreshPage('${appName}/jsp/addActivities.jsp', 'pageNumber=1')">
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
							<h5>You have started / finished all existing activities</h5>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<hr>
		<div class="text-center">
			<button id="addButton" class="btn btn-lg btn-dark" type="submit"
				disabled>Add</button>
		</div>
		<br>
		<div class="text-center">
			<a href="${appName}/jsp/profile.jsp" class="link-dark">Back</a>
		</div>
	</form>
</body>
</html>