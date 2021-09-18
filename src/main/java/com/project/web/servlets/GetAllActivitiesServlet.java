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
import com.project.db.dao.DAO;
import com.project.db.entity.Activity;

@WebServlet("/getAllActivities")
public class GetAllActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetAllActivitiesServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("GetAllActivitiesServlet");

		HttpSession session = req.getSession();
		DAO<Activity> activityDao = new ActivityDAO();
		
		try {
			List<Activity> activities = activityDao.getAll();
			log.info("activities ==> " + activities);
			session.setAttribute("activities", activities);
			session.setAttribute("sortOption", "default");
			session.setAttribute("categoryOption", "all");
			GetAllCategoriesServlet.setAllCategoriesToSession(session);
			resp.sendRedirect(req.getContextPath() + "/jsp/admin/activities.jsp");
		} catch (DBException ex) {
			log.error("DBException in GetAllActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}