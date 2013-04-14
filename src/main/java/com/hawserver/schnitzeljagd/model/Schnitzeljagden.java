package com.hawserver.schnitzeljagd.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.hawserver.basic.ContextListener;

public class Schnitzeljagden {
    
    private Schnitzeljagden(){}
    
    public static boolean schnitzeljagdExists(int id){
        return getSchnitzeljagd(id)!=null;
    }
    
    public static Schnitzeljagd getSchnitzeljagd(int id){
        EntityManager em = ContextListener.createEntityManager();
        TypedQuery<Schnitzeljagd> query = em.createNamedQuery(
                "Schnitzeljagd.findById", Schnitzeljagd.class);
        query.setParameter("id", id);
        List<Schnitzeljagd> result = query.getResultList();
        em.close();
        if(result.isEmpty())
            return null;
        else
            return result.get(0);
    }
    
    public static List<Schnitzeljagd> getSchnitzeljagden(){
        EntityManager em = ContextListener.createEntityManager();
        TypedQuery<Schnitzeljagd> query = em.createNamedQuery(
                "Schnitzeljagd.findAll", Schnitzeljagd.class);
        List<Schnitzeljagd> result=query.getResultList();
        em.close();
        return result;
    }
    
  
    public static void updateSchnitzeljagd(Schnitzeljagd sj) {
        EntityManager em = ContextListener.createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        Schnitzeljagd toUpdate=em.createNamedQuery(
                "Schnitzeljagd.findById", Schnitzeljagd.class).setParameter("id", sj.getId()).getResultList().get(0);
        toUpdate.setName(sj.getName());
        em.persist(toUpdate);
        transaction.commit();
        em.close();
    }

    public static void addSchnitzeljagd(Schnitzeljagd sj) {
        EntityManager em = ContextListener.createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        em.persist(sj);
        em.flush();
        transaction.commit();
        em.close();        
    }

    public static void deleteSchnitzeljagd(int id){
        EntityManager em = ContextListener.createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        transaction.begin();
        TypedQuery<Schnitzeljagd> query = em.createNamedQuery(
                "Schnitzeljagd.deleteById", Schnitzeljagd.class);
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
        em.close();
    }

}
