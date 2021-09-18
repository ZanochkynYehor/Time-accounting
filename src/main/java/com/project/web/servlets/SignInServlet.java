package com.project.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.db.DBException;
import com.project.db.dao.DAO;
import com.project.db.dao.UserDAO;
import com.project.db.entity.User;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(SignInServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("SignInServlet");
		
		String login = req.getParameter("login");
		String password = req.getParameter("password");

		log.info("login ==> " + login);
		
		DAO<User> userDao = new UserDAO();
		HttpSession session = req.getSession();
		
		try {
			User user = userDao.get(login);
			if (user.getId() != 0) {
				if (user.getPassword().equals(password)) {
					session.setAttribute("user", user);
					resp.sendRedirect(req.getContextPath() + "/getUserActivities");
				} else {
					log.warn("Wrong password");
					session.setAttribute("signin", "wrongPassword");
					resp.sendRedirect(req.getContextPath() + "/jsp/signin.jsp");
				}
			} else {
				log.warn("Wrong login");
				session.setAttribute("signin", "wrongLogin");
				resp.sendRedirect(req.getContextPath() + "/jsp/signin.jsp");
			}
		} catch (DBException ex) {
			log.error("DBException in SignInServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}