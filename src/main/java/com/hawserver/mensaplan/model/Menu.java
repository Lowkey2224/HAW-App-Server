package com.hawserver.mensaplan.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateMidnight;

public class Menu {
    
    private Menu(){}
    
    public static List<MenuItem> getMenu(DateMidnight date){
        List<MenuItem> result=new ArrayList<MenuItem>();
        result.add(new MenuItem(1, new java.util.Date(), 29, 39, "description1", "category1", "attributes1"));
        return result;
    }
    
    public static List<DateMidnight> getMenuList(){
        List<DateMidnight> result=new ArrayList<DateMidnight>();
        result.add(new DateMidnight());
        return result;
    }

}
