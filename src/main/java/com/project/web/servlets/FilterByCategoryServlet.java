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

@WebServlet("/filterByCategory")
public class FilterByCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(FilterByCategoryServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("FilterByCategoryServlet");

		String categoryOption = req.getParameter("category");
		log.info("category option ==> " + categoryOption);
		
		HttpSession session = req.getSession();
		session.setAttribute("categoryOption", categoryOption);
		ActivityDAO activityDao = new ActivityDAO();
		List<Activity> activitiesByCategory;
		
		try {
			if (categoryOption.equals("all")) {
				resp.sendRedirect(req.getContextPath() + "/getAllActivities");
				return;
			}
			activitiesByCategory = activityDao.getActivitiesByCategory(Integer.parseInt(categoryOption));
			session.setAttribute("activities", activitiesByCategory);
			resp.sendRedirect(req.getContextPath() + "/jsp/admin/activities.jsp");
		} catch (DBException ex) {
			log.error("DBException in FilterByCategoryServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in FilterByCategoryServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}