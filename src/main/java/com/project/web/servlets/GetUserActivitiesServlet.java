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
import com.project.db.dao.UserActivityDAO;
import com.project.db.dao.UserDAO;
import com.project.db.entity.User;
import com.project.db.entity.UserActivity;

@WebServlet("/getUserActivities")
public class GetUserActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetUserActivitiesServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("GetUserActivitiesServlet");
		
		String[] arr = req.getHeader("referer").split("/");
		String str = arr[arr.length - 1];
		String[] arr2 = str.split("\\?");
		String page = arr2[0];
		
		HttpSession session = req.getSession();
		UserActivityDAO userActivityDao = new UserActivityDAO();
		
		try {
			if (page.equals("users.jsp")) {
				String id = req.getParameter("chk_user");
				UserDAO userDao = new UserDAO();
				User user = userDao.get(Integer.parseInt(id));
				session.setAttribute("specificUser", user);
				List<UserActivity> userActivities = userActivityDao.getAllUserActivities(user);
				session.setAttribute("specificUserActivities", userActivities.size() == 0 ? null : userActivities);
				resp.sendRedirect(req.getContextPath() + "/jsp/admin/userStat.jsp");
			} else {
				User user = (User) session.getAttribute("user");
				List<UserActivity> userActivities = userActivityDao.getAllUserActivities(user);
				session.setAttribute("userActivities", userActivities.size() == 0 ? null : userActivities);
				resp.sendRedirect(req.getContextPath() + "/jsp/profile.jsp");
			}
		} catch (DBException ex) {
			log.error("DBException in GetUserActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in GetUserActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}