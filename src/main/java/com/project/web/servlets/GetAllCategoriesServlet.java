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

@WebServlet("/getAllCategories")
public class GetAllCategoriesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LogManager.getLogger(GetAllCategoriesServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info("GetAllCategoriesServlet");

		HttpSession session = req.getSession();
		try {
			setAllCategoriesToSession(session);
			resp.sendRedirect(req.getContextPath() + "/jsp/admin/categories.jsp");
		} catch (DBException ex) {
			log.error("DBException in GetAllCategoriesServlet");
			req.setAttribute("errorMessage", ex.getMessage());
			req.getRequestDispatcher("jsp/error.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	public static void setAllCategoriesToSession(HttpSession session) throws DBException {
		DAO<ActivityCategory> activityCategoryDao = new ActivityCategoryDAO();
		List<ActivityCategory> categories = activityCategoryDao.getAll();
		session.setAttribute("categories", categories);
	}
}