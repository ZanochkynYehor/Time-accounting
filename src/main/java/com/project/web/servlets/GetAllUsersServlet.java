package com.project.web.servlets;

import java.io.IOException;
import java.util.List;

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

@WebServlet("/getAllUsers")
public class GetAllUsersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetAllUsersServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("GetAllUsersServlet");

		HttpSession session = req.getSession();
		DAO<User> userDao = new UserDAO();
		try {
			List<User> users = userDao.getAll();
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getRole().equals("admin")) {
					users.remove(i);
				}
			}
			log.info("users ==> " + users);
			session.setAttribute("users", users.size() == 0 ? null : users);
			resp.sendRedirect(req.getContextPath() + "/jsp/admin/users.jsp");
		} catch (DBException ex) {
			log.error("DBException in GetAllUsersServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}