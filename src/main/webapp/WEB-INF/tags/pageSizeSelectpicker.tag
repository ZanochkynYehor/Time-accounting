<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="jsp" required="true" %>
<%@ attribute name="rowsPerPage" type="java.lang.Integer" required="true" %>

<div class="row justify-content-center">
	<div class="col-md-1">
		<select class="form-select" id="pageSize" name="pageSize" onchange="refreshPage('${jsp}', 'pageNumber=1')">
			<option value="3" ${rowsPerPage == 3 ? 'selected' : ''}>3</option>
			<option value="5" ${rowsPerPage == 5 ? 'selected' : ''}>5</option>
			<option value="10" ${rowsPerPage == 10 ? 'selected' : ''}>10</option>
		</select>
	</div>
</div>