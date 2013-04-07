package com.hawserver.mensaplan.rest;


import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hawserver.mensaplan.model.MenuItem;
import com.hawserver.mensaplan.model.Menus;
//import org.apache.log4j.Logger;

/**
 * The rest resource to deal with all http request regarding the menus.
 * @author Oliver
 *
 */
@Path("menu")
public class MenuResource {
    
    //private static Logger LOG=Logger.getLogger(MenuResource.class);

    /**
     * Get a list of all menus as list of timestamps.
     * @return
     */
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public List<Date> menuList(){
        return Menus.getMenuList();
    }
    
    /**
     * Get a menu for a certain day
     * @param date of the menu
     * @return list of menu items converted to JSON
     */
    @GET
    @Path("{timestamp}")
    @Produces( MediaType.APPLICATION_JSON )
    public List<MenuItem> menu(@PathParam("timestamp") long date){
        return Menus.getMenu(new Date(date));
    }
    
    
    
}
