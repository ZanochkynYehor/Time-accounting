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
import com.project.web.password.PasswordUtil;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(SignUpServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("SignUpServlet");
		
		String login = req.getParameter("login");
		String password = req.getParameter("password");

		log.info("login ==> " + login);

		DAO<User> userDao = new UserDAO();
		HttpSession session = req.getSession();
		
		try {
			User user = userDao.get(login);
			log.info("user ==> " + user);
			if (user != null) {
				log.warn("login is taken");
				session.setAttribute("signup", "loginIsTaken");
				resp.sendRedirect(req.getContextPath() + "/signup.jsp");
			} else {
				String salt = PasswordUtil.generateSalt();
				String securePassword = PasswordUtil.hashThePlainTextPassword(password, salt);
				user = userDao.create(User.createUser(login, securePassword, salt));
				if (user.getId() != 0) {
					session.setAttribute("signup", "signupSuccessfully");
					resp.sendRedirect(req.getContextPath() + "/signin.jsp");
				} else {
					req.setAttribute("errorMessage", "Cannot sign up user");
					req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
				}
			}
		} catch (DBException ex) {
			log.error("DBException in SignUpServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}