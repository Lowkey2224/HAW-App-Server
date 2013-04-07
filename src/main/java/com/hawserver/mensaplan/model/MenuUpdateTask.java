package com.hawserver.mensaplan.model;

import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * Task to perform an update on the menu items database.
 * @author Oliver
 *
 */
public class MenuUpdateTask extends TimerTask {

    private static final Logger LOG=Logger.getLogger(MenuUpdateTask.class);
    
    @Override
    public void run() {
        LOG.info("Performing scheduled update of menu items");
        Menus.performUpdate();
    }

}
