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

@WebServlet("/newActivity")
public class NewActivityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(NewActivityServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("NewActivityServlet");

		String[] arr = req.getHeader("referer").split("/");
		String str = arr[arr.length - 1];
		String[] arr2 = str.split("\\?");
		String page = arr2[0];

		HttpSession session = req.getSession();

		try {
			if (page.equals("activities.jsp")) {
				GetAllCategoriesServlet.setAllCategoriesToSession(session);
				resp.sendRedirect(req.getContextPath() + "/jsp/admin/newActivity.jsp");
			} else {
				String activityName = req.getParameter("activity");
				String categoryId = req.getParameter("category");
				DAO<Activity> activityDao = new ActivityDAO();
				List<Activity> list = activityDao.getAll();
				for	(Activity act : list) {
					if (act.getName().equals(activityName)) {
						session.setAttribute("existingActivity", true);
						resp.sendRedirect(req.getContextPath() + "/jsp/admin/newActivity.jsp");
						return;
					}
				}
				activityDao.create(Activity.createActivity(activityName, Integer.parseInt(categoryId)));
				resp.sendRedirect(req.getContextPath() + "/getAllActivities");
			}
		} catch (DBException ex) {
			log.error("DBException in NewActivityServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}