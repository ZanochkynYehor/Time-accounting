package com.project.web.servlets;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
import com.project.db.entity.UserActivity;

@WebServlet("/finishActivities")
public class FinishActivitiesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(FinishActivitiesServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("FinishActivitiesServlet");

		LocalDateTime locatDateTime = LocalDateTime.now();
		String s = locatDateTime.toString();
		String[] arr = s.split("T");
		String date = arr[0];
		String[] arr2 = arr[1].split("\\.");
		String time = arr2[0];
		String finishTime = date + " " + time;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime finishDateTime = LocalDateTime.parse(finishTime, formatter);
		log.info("finishDateTime == > " + finishDateTime);

		String[] arrayOfActivitiesIds = req.getParameterValues("chk_activity");
		log.info("count activities to finish ==> " + arrayOfActivitiesIds.length);
		User user = (User) req.getSession().getAttribute("user");

		try {
			UserActivityDAO userActivityDao = new UserActivityDAO();
			for (String id : arrayOfActivitiesIds) {
				UserActivity userActivity = userActivityDao.getUserActivity(user, Integer.parseInt(id));
				if (userActivity.getFinishTime().equals("00:00:00")) {
					String startTime = userActivity.getStartDateTime();
					if (!startTime.equals("0000-00-00 00:00:00")) {
						LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
						Duration duration = Duration.between(startDateTime, finishDateTime);
						finishTime = LocalTime.MIDNIGHT.plus(duration).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
						log.info("duration ==> " + finishTime);
						userActivityDao.update(user.getId(), Integer.parseInt(id), "Yes", startTime, finishTime);
					} else {
						log.warn("activity not started yet");
					}
				} else {
					log.warn("activity already finished");
				}
			}
			resp.sendRedirect(req.getContextPath() + "/getUserActivities");
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