<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="pageNumber" type="java.lang.Integer" required="true" %>
<%@ attribute name="numberOfPages" type="java.lang.Integer" required="true" %>
<%@ attribute name="jsp" required="true" %>

<nav>
	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${pageNumber gt 1}">
				<li class="page-item">
					<a class="page-link link-dark" href="${jsp}&pageNumber=${pageNumber - 1}">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="page-item disabled">
					<a class="page-link link-dark" href="#" tabindex="-1" aria-disabled="true">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
			</c:otherwise>
		</c:choose>
		<c:forEach begin="1" end="${numberOfPages}" var="page">
			<c:choose>
				<c:when test="${page != pageNumber}">
					<li class="page-item">
						<a class="page-link link-dark" href="${jsp}&pageNumber=${page}">${page}</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item active" aria-current="page">
						<a class="page-link link-dark" href="#" tabindex="-1" aria-disabled="true">${page}</a>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:choose>
			<c:when test="${pageNumber lt numberOfPages}">
				<li class="page-item">
					<a class="page-link link-dark" href="${jsp}&pageNumber=${pageNumber + 1}">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="page-item disabled">
					<a class="page-link link-dark" href="#" tabindex="-1" aria-disabled="true">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li>
			</c:otherwise>
		</c:choose>
	</ul>
</nav>