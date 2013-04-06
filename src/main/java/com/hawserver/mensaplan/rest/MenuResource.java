package com.hawserver.mensaplan.rest;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;

import com.hawserver.basic.ContextListener;
import com.hawserver.mensaplan.model.Menu;
import com.hawserver.mensaplan.model.MenuItem;

@Path("menu")
public class MenuResource {
    
    private static Logger LOG=Logger.getLogger(MenuResource.class);

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public List<DateMidnight> menuList(){
        return Menu.getMenuList();
    }
    
    @GET
    @Path("{timestamp}")
    @Produces( MediaType.APPLICATION_JSON )
    public List<MenuItem> menu(@PathParam("date") long date){
        return Menu.getMenu(new DateMidnight(date));
    }
    
}
