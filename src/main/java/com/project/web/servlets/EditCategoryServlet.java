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
import com.project.db.entity.ActivityCategory;

@WebServlet("/editCategory")
public class EditCategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(EditCategoryServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("EditCategoryServlet");

		String[] arr = req.getHeader("referer").split("/");
		String str = arr[arr.length - 1];
		String[] arr2 = str.split("\\?");
		String page = arr2[0];

		HttpSession session = req.getSession();
		ActivityCategoryDAO activityCategoryDao = new ActivityCategoryDAO();
		
		try {
			if (page.equals("categories.jsp")) {
				String categoryId = req.getParameter("chk_category");
				ActivityCategory activityCategory = activityCategoryDao.get(Integer.parseInt(categoryId));
				session.setAttribute("categoryToEdit", activityCategory);
				resp.sendRedirect(req.getContextPath() + "/jsp/admin/editCategory.jsp");
			} else {
				String categoryName = req.getParameter("category");
				ActivityCategory category = (ActivityCategory) session.getAttribute("categoryToEdit");
				ActivityCategory activityCategoryCpy = new ActivityCategory(category);
				activityCategoryCpy.setName(categoryName);
				List<ActivityCategory> list = activityCategoryDao.getAll();
				for	(ActivityCategory cat : list) {
					if (cat.getName().equals(activityCategoryCpy.getName())) {
						if (cat.getId() != activityCategoryCpy.getId()) {
							session.setAttribute("existingCategory", true);
							session.setAttribute("categoryToEdit", category);
							resp.sendRedirect(req.getContextPath() + "/jsp/admin/editCategory.jsp");
							return;
						}
					}
				}
				activityCategoryDao.update(activityCategoryCpy);
				resp.sendRedirect(req.getContextPath() + "/getAllCategories");
			}
		} catch (DBException ex) {
			log.error("DBException in EditCategoryServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
}