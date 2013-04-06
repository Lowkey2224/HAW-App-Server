package com.hawserver.basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

@WebListener
public class ContextListener implements ServletContextListener {

    private static EntityManagerFactory emf;
    private static Logger LOG;
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        System.setProperty("rootPath", context.getRealPath("/"));
        emf = Persistence.createEntityManagerFactory("HawServer");
        LOG=Logger.getLogger(ContextListener.class);
        LOG.info("Logging up and running");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        emf.close();
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        return emf.createEntityManager();
    }
    
    
}
