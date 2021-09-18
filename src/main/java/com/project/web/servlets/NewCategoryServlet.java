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
import com.project.db.dao.ActivityCategoryDAO;
import com.project.db.dao.DAO;
import com.project.db.entity.ActivityCategory;

@WebServlet("/newCategory")
public class NewCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(NewCategoryServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("NewCategoryServlet");

		HttpSession session = req.getSession();

		try {
			String category = req.getParameter("category");
			DAO<ActivityCategory> activityCategoryDao = new ActivityCategoryDAO();
			List<ActivityCategory> list = activityCategoryDao.getAll();
			for (ActivityCategory cat : list) {
				if (cat.getName().equals(category)) {
					session.setAttribute("existingCategory", true);
					resp.sendRedirect(req.getContextPath() + "/jsp/admin/newCategory.jsp");
					return;
				}
			}
			activityCategoryDao.create(ActivityCategory.createCategory(category));
			resp.sendRedirect(req.getContextPath() + "/getAllCategories");
		} catch (DBException ex) {
			log.error("DBException in NewCategoryServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}