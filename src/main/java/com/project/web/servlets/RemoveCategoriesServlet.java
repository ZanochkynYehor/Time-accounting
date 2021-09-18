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
import com.project.db.dao.ActivityCategoryDAO;
import com.project.db.entity.ActivityCategory;

@WebServlet("/removeCategories")
public class RemoveCategoriesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(RemoveCategoriesServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("RemoveCategoriesServlet");
		
		String[] arrayOfCategoriesIds = req.getParameterValues("chk_category");
		log.info("count categories to delete ==> " + arrayOfCategoriesIds.length);
		
		try {
			ActivityCategoryDAO activityCategoryDao = new ActivityCategoryDAO();
			for (String id : arrayOfCategoriesIds) {
				ActivityCategory activityCategory = new ActivityCategory();
				activityCategory.setId(Integer.parseInt(id));
				activityCategoryDao.delete(activityCategory);
			}
			resp.sendRedirect(req.getContextPath() + "/getAllCategories");
		} catch (DBException ex) {
			log.error("DBException in RemoveCategoriesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		} catch (NumberFormatException ex) {
			log.error("NumberFormatException in RemoveCategoriesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}