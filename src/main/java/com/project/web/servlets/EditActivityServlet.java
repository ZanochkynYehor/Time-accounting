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

@WebServlet("/editActivity")
public class EditActivityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(EditActivityServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("EditActivityServlet");
		
		String[] arr = req.getHeader("referer").split("/");
		String str = arr[arr.length - 1];
		String[] arr2 = str.split("\\?");
		String page = arr2[0];

		HttpSession session = req.getSession();
		ActivityDAO activityDao = new ActivityDAO();
		
		try {
			if (page.equals("activities.jsp")) {
				String activityId = req.getParameter("chk_activity");
				Activity activity = activityDao.get(Integer.parseInt(activityId));
				session.setAttribute("activityToEdit", activity);
				GetAllCategoriesServlet.setAllCategoriesToSession(session);
				resp.sendRedirect(req.getContextPath() + "/jsp/admin/editActivity.jsp");
			} else {
				String activityName = req.getParameter("activity");
				String categoryId = req.getParameter("category");
				Activity activity = (Activity) session.getAttribute("activityToEdit");
				Activity activityCpy = new Activity(activity);
				activityCpy.setName(activityName);
				activityCpy.setCategoryId(Integer.parseInt(categoryId));
				List<Activity> list = activityDao.getAll();
				for	(Activity act : list) {
					if (act.getName().equals(activityCpy.getName())) {
						if (act.getId() != activityCpy.getId()) {
							session.setAttribute("existingActivity", true);
							session.setAttribute("activityToEdit", activity);
							resp.sendRedirect(req.getContextPath() + "/jsp/admin/editActivity.jsp");
							return;
						}
					}
				}
				activityDao.update(activityCpy);
				resp.sendRedirect(req.getContextPath() + "/getAllActivities");
			}
		} catch (DBException ex) {
			log.error("DBException in EditActivityServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}