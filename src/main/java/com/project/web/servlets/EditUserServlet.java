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
import com.project.db.dao.UserDAO;
import com.project.db.entity.User;

@WebServlet("/editUser")
public class EditUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(EditUserServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("EditUserServlet");

		String[] arr = req.getHeader("referer").split("/");
		String str = arr[arr.length - 1];
		String[] arr2 = str.split("\\?");
		String page = arr2[0];
		System.out.println(page);

		HttpSession session = req.getSession();
		UserDAO userDao = new UserDAO();
		
		try {
			if (page.equals("users.jsp")) {
				String userId = req.getParameter("chk_user");
				User user = userDao.get(Integer.parseInt(userId));
				session.setAttribute("userToEdit", user);
				resp.sendRedirect(req.getContextPath() + "/jsp/admin/editUser.jsp");
			} else {
				String roleId = req.getParameter("role");
				User user = (User) session.getAttribute("userToEdit");
				user.setRoleId(Integer.parseInt(roleId));
				userDao.update(user);
				resp.sendRedirect(req.getContextPath() + "/getAllUsers");
			}
		} catch (DBException ex) {
			log.error("DBException in EditUserServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in EditUserServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}