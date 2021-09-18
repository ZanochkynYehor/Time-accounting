package com.project.web.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

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

@WebServlet("/startActivities")
public class StartActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(StartActivitiesServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("StartActivitiesServlet");
		
		String[] arrayOfActivitiesIds = req.getParameterValues("chk_activity");
		log.info("count activities to start ==> " + arrayOfActivitiesIds.length);
		User user = (User) req.getSession().getAttribute("user");
		
		LocalDateTime locatDateTime = LocalDateTime.now();
		String s = locatDateTime.toString();
		String[] arr = s.split("T");
		String date = arr[0];
		String[] arr2 = arr[1].split("\\.");
		String time = arr2[0];
		String startDateTime = date + " " + time;
		log.info("startDateTime ==> " + startDateTime);
		
		try {
			UserActivityDAO userActivityDao = new UserActivityDAO();
			for (String id : arrayOfActivitiesIds) {
				userActivityDao.update(user.getId(), Integer.parseInt(id), "Yes",  startDateTime, "00:00:00");
			}
			resp.sendRedirect(req.getContextPath() + "/getUserActivities");
		} catch (DBException ex) {
			log.error("DBException in StartActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in StartActivitiesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}