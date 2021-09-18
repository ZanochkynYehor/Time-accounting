package com.project.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.db.DBException;
import com.project.db.dao.UserActivityDAO;
import com.project.db.entity.UserActivity;

@WebServlet("/getRequestedActivities")
public class GetRequestedActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetRequestedActivitiesServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("GetRequestedActivitiesServlet");

		UserActivityDAO userActivityDao = new UserActivityDAO();
		try {
			List<UserActivity> requestedActivities = userActivityDao.getNotApprovedActivities();
			log.info("requestedActivities ==> " + requestedActivities);
			req.getSession().setAttribute("requestedActivities", requestedActivities.size() == 0 ? null : requestedActivities);
			resp.sendRedirect(req.getContextPath() + "/jsp/admin/requests.jsp");
		} catch (DBException ex) {
			log.error("DBException in GetRequestedActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}