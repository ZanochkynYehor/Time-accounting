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
import com.project.db.dao.ActivityDAO;
import com.project.db.entity.Activity;

@WebServlet("/removeActivities")
public class RemoveActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(RemoveActivitiesServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("RemoveActivitiesServlet");
		
		String[] arrayOfActivitiesIds = req.getParameterValues("chk_activity");
		log.info("count activities to delete ==> " + arrayOfActivitiesIds.length);

		try {
			ActivityDAO activityDao = new ActivityDAO();
			for (String id : arrayOfActivitiesIds) {
				Activity activity = new Activity();
				activity.setId(Integer.parseInt(id));
				activityDao.delete(activity);
			}
			resp.sendRedirect(req.getContextPath() + "/getAllActivities");
		} catch (DBException ex) {
			log.error("DBException in FinishActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in FinishActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}