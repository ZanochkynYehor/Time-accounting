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
import com.project.db.dao.UserActivityDAO;
import com.project.db.entity.User;

@WebServlet("/removeUserActivities")
public class RemoveUserActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(RemoveUserActivitiesServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("RemoveUserActivitiesServlet");

		String[] arr = req.getHeader("referer").split("/");
		String str = arr[arr.length - 1];
		String[] arr2 = str.split("\\?");
		String page = arr2[0];

		UserActivityDAO userActivityDao = new UserActivityDAO();
		try {
			if (page.equals("requests.jsp")) {
				String[] arrayOfUsersActivitiesIds = req.getParameterValues("chk_userActivity");
				for (String s : arrayOfUsersActivitiesIds) {
					String[] ids = s.split(" ");
					int userId = Integer.parseInt(ids[0]);
					int activityId = Integer.parseInt(ids[1]);
					userActivityDao.delete(userId, activityId);
				}
				resp.sendRedirect(req.getContextPath() + "/getRequestedActivities");
			} else {
				String[] arrayOfActivitiesIds = req.getParameterValues("chk_activity");
				log.info("count activities to delete ==> " + arrayOfActivitiesIds.length);
				User user = (User) req.getSession().getAttribute("user");
				for (String id : arrayOfActivitiesIds) {
					userActivityDao.delete(user.getId(), Integer.parseInt(id));
				}
				resp.sendRedirect(req.getContextPath() + "/getUserActivities");
			}
		} catch (DBException ex) {
			log.error("DBException in RemoveUserActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in RemoveUserActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}