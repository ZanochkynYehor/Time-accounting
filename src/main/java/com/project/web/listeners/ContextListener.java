package com.project.web.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.*;

@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		String contextPath = context.getContextPath();
		context.setAttribute("appName", contextPath);
		
		String path = context.getRealPath("/WEB-INF/log4j2.log");
		System.setProperty("logFile", path);
		
		final Logger log = LogManager.getLogger(ContextListener.class);
		log.debug("path = " + path);
	}
}