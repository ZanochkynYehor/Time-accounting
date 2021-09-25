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

@WebServlet("/changeUserPassword")
public class ChangeUserPasswordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(ChangeUserPasswordServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("ChangeUserPasswordServlet");

		String password = req.getParameter("password");
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		try {
			DAO<User> userDao = new UserDAO();
			String securePassword = PasswordUtil.hashThePlainTextPassword(password, user.getSalt());
			user.setPassword(securePassword);
			userDao.update(user);
			session.setAttribute("passwordChanged", true);
			resp.sendRedirect(req.getContextPath() + "/jsp/settings.jsp");
		} catch (DBException ex) {
			log.error("DBException in ChangeUserPasswordServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}