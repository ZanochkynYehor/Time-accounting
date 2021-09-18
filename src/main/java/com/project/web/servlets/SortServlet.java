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
import com.project.db.entity.Activity;

@WebServlet("/sort")
public class SortServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(SortServlet.class);

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("SortServlet");

		String sortOption = req.getParameter("sort");
		log.info("sort option ==> " + sortOption);
		HttpSession session = req.getSession();
		session.setAttribute("sortOption", sortOption);
		List<Activity> activities = (List<Activity>) session.getAttribute("activities");

		try {
			switch (sortOption) {
			case "name asc":
				activities.sort(Activity.NameAscComparator);
				break;
			case "name desc":
				activities.sort(Activity.NameDescComparator);
				break;
			case "category asc":
				activities.sort(Activity.CategoryAscComparator);
				break;
			case "category desc":
				activities.sort(Activity.CategoryDescComparator);
				break;
			case "users count asc":
				ActivityDAO activityDao = new ActivityDAO();
				List<Activity> sortedActivities = activityDao.getAllSortedByUsersCount("ASC");
				session.setAttribute("activities", sortedActivities);
				break;
			case "users count desc":
				activityDao = new ActivityDAO();
				sortedActivities = activityDao.getAllSortedByUsersCount("DESC");
				session.setAttribute("activities", sortedActivities);
				break;
			case "default":
				activities.sort(Activity.IdComparator);
			}
			resp.sendRedirect(req.getContextPath() + "/jsp/admin/activities.jsp");
		} catch (DBException ex) {
			log.error("DBException in SortServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}