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
import com.project.db.dao.ActivityDAO;
import com.project.db.dao.UserActivityDAO;
import com.project.db.entity.Activity;
import com.project.db.entity.User;

@WebServlet("/addActivitiesToUser")
public class AddActivitiesToUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(AddActivitiesToUserServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("AddActivitiesToUserServlet");

		String[] arr = req.getHeader("referer").split("/");
		String str = arr[arr.length - 1];
		String[] arr2 = str.split("\\?");
		String page = arr2[0];

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		try {
			if (page.equals("profile.jsp")) {
				ActivityDAO activityDao = new ActivityDAO();
				List<Activity> activities = activityDao.getActivitiesUserDoesNotHave(user.getId());
				session.setAttribute("activitiesUserDoesNotHave", activities.size() == 0 ? null : activities);
				resp.sendRedirect(req.getContextPath() + "/jsp/addActivities.jsp");
			} else {
				String[] arrayOfActivitiesIds = req.getParameterValues("chk_activity");
				log.info("count activities to add ==> " + arrayOfActivitiesIds.length);
				UserActivityDAO userActivityDao = new UserActivityDAO();
				if (user.getRole().equals("admin")) {
					userActivityDao.create(user, arrayOfActivitiesIds, "Yes");
				} else {
					userActivityDao.create(user, arrayOfActivitiesIds, "No");
				}
				resp.sendRedirect(req.getContextPath() + "/getUserActivities");
			}
		} catch (DBException ex) {
			log.error("DBException in AddActivitiesToUserServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}