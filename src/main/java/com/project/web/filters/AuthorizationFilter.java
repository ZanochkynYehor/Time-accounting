package com.project.web.filters;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.db.entity.User;

@WebFilter("/jsp/*")
public class AuthorizationFilter implements Filter {

	private static final Logger log = LogManager.getLogger(AuthorizationFilter.class);
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		log.info("AuthorizationFilter");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("user") == null) {
			log.warn("Access denied. Redirect to signin page");
			response.sendRedirect(request.getContextPath() + "/signin.jsp");
		} else {
			String[] arr = request.getRequestURI().split("/");
			String page = arr[arr.length - 2];
			User user = (User) session.getAttribute("user");
			if (page.equals("admin")) {
				if (user.getRole().equals("user")) {
					session.setAttribute("access", "denied");
					response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
				}
			}
			chain.doFilter(req, res);
		}
	}
}