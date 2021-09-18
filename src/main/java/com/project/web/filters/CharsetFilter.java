package com.project.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/*")
public class CharsetFilter implements Filter {

	private String encoding;

	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("requestEncoding");
		if (encoding == null) {
			encoding = "UTF-8";
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(encoding);
		}
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}