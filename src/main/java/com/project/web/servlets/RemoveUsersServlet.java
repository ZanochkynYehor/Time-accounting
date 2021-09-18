package com.project.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.db.DBException;
import com.project.db.dao.DAO;
import com.project.db.dao.UserDAO;
import com.project.db.entity.User;

@WebServlet("/removeUsers")
public class RemoveUsersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(RemoveUsersServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("RemoveUsersServlet");
		
		String[] arrayOfUsersIds = req.getParameterValues("chk_user");
		log.info("count users to delete ==> " + arrayOfUsersIds.length);

		try {
			DAO<User> userDao = new UserDAO();
			for (String id : arrayOfUsersIds) {
				User user = new User();
				user.setId(Integer.parseInt(id));
				userDao.delete(user);
			}
			resp.sendRedirect(req.getContextPath() + "/getAllUsers");
		} catch (DBException ex) {
			log.error("DBException in RemoveUsersServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in RemoveUsersServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}