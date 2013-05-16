package com.hawserver.basic;

import java.util.Timer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.hawserver.mensaplan.model.MenuUpdateTask;
import com.hawserver.veranstaltungsplan.DownloadScheduleTask;

/**
 * The context listener deals with all issues regarding start and shutdown of the application.
 * @author Oliver
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private static EntityManagerFactory emf;
    private static Logger LOG;
    private Timer timer;
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        System.setProperty("rootPath", context.getRealPath("/"));
        emf = Persistence.createEntityManagerFactory("HawServer");
        LOG=Logger.getLogger(ContextListener.class);
        LOG.info("Logging up and running");
        timer=new Timer();
        //@TODO: move to properties file for configuration
        timer.schedule(new MenuUpdateTask(), 0, 3600000);
        timer.schedule(new DownloadScheduleTask(context.getRealPath("/")), 0, 3600000);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        emf.close();
        timer.cancel();
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }

        return emf.createEntityManager();
    }
    
    
}
