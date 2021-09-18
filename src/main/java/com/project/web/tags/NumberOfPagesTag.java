package com.project.web.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class NumberOfPagesTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	private int contentSize;
	private int rowsPerPage;

	public void setContentSize(int contentSize) {
		this.contentSize = contentSize;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        HttpSession session = request.getSession();
        int result = (int) Math.ceil((double) contentSize / rowsPerPage);
        session.setAttribute("numberOfPages", result);
		return SKIP_BODY;
	}
}