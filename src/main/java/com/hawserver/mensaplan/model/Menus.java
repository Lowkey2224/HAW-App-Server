package com.hawserver.mensaplan.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.hawserver.basic.ContextListener;

/**
 * Menus Utility class.
 * 
 * @author Oliver
 * 
 */
public class Menus {

    private Menus() {
    }

    /**
     * Get a menu for a certain date
     * 
     * @param date
     *            of menu
     * @return menu as list of menu items
     */
    public static List<MenuItem> getMenu(Date date) {
        EntityManager em = ContextListener.createEntityManager();
        TypedQuery<MenuItem> query = em.createNamedQuery(
                "MenuItem.findByDateOfItem", MenuItem.class);
        query.setParameter("dateOfItem", date);
        List<MenuItem> result = query.getResultList();
        em.close();
        return result;
    }

    /**
     * Get a list of all available menus
     * 
     * @return a menu is represented by it's date.
     */
    public static List<Date> getMenuList() {
        EntityManager em = ContextListener.createEntityManager();
        SortedSet<Date> result = new TreeSet<Date>();
        List<MenuItem> menuItems = em.createNamedQuery("MenuItem.findAll",
                MenuItem.class).getResultList();
        for (MenuItem menuItem : menuItems) {
            result.add(menuItem.getDateOfItem());
        }
        em.close();
        return new ArrayList<Date>(result);
    }

    /**
     * Execute an update on MenuItem database. If the parsing is successful
     * (non-empty list is retrieved), delete the old table content and replace
     * it with the new content.
     */
    public static void performUpdate() {
        List<MenuItem> newItems = new MenuParser().getCurrentMenuItems();
        if (!newItems.isEmpty()) {
            EntityManager em = ContextListener.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.createNamedQuery("MenuItem.deleteAll", MenuItem.class)
                    .executeUpdate();
            for (MenuItem item : newItems) {
                em.persist(item);
            }
            transaction.commit();
            em.close();
        }
    }

}
