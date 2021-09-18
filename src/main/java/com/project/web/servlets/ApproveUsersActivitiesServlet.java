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

@WebServlet("/approveUsersActivities")
public class ApproveUsersActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(ApproveUsersActivitiesServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("ApproveUsersActivitiesServlet");
		
		UserActivityDAO userActivityDao = new UserActivityDAO();
		try {
			String[] arrayOfUsersActivitiesIds = req.getParameterValues("chk_userActivity");
			for (String s : arrayOfUsersActivitiesIds) {
				String[] ids = s.split(" ");
				int userId = Integer.parseInt(ids[0]);
				int activityId = Integer.parseInt(ids[1]);
				userActivityDao.update(userId, activityId, "Yes", "0000-00-00 00:00:00", "00:00:00");
			}
			resp.sendRedirect(req.getContextPath() + "/getRequestedActivities");
		} catch (DBException ex) {
			log.error("DBException in ApproveUsersActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in ApproveUsersActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}