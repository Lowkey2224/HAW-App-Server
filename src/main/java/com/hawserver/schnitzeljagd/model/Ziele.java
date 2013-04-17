package com.hawserver.schnitzeljagd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.hawserver.basic.ContextListener;

public class Ziele {

    private Ziele() {
    }

    public static boolean zielExists(int id) {
        return getZiel(id) != null;
    }

    public static void addZiel(Ziel z) {
        EntityManager em = ContextListener.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        z.setCode(UUID.randomUUID().toString());
        em.persist(z);
        em.flush();
        transaction.commit();
        em.close();
    }

    public static void updateZiel(Ziel z) {
        EntityManager em = ContextListener.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Ziel toUpdate = em.createNamedQuery("Ziel.findById", Ziel.class)
                .setParameter("id", z.getId()).getResultList().get(0);
        toUpdate.setName(z.getName());
        toUpdate.setLatitude(z.getLatitude());
        toUpdate.setLongitude(z.getLongitude());
        toUpdate.setSchnitzeljagdId(z.getSchnitzeljagdId());
        toUpdate.setCode(z.getCode());
        em.persist(toUpdate);
        transaction.commit();
        em.close();
    }

    public static void deleteZiel(int id) {
        EntityManager em = ContextListener.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        TypedQuery<Ziel> query = em.createNamedQuery("Ziel.deleteById",
                Ziel.class);
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
        em.close();
    }

    public static List<Ziel> getZieleForSchnitzeljagdId(int id) {
        Schnitzeljagd sj = Schnitzeljagden.getSchnitzeljagd(id);
        if (sj != null) {
            EntityManager em = ContextListener.createEntityManager();
            TypedQuery<Ziel> query = em.createNamedQuery(
                    "Ziel.findBySchnitzeljagdId", Ziel.class);
            query.setParameter("schnitzeljagdId", sj);
            List<Ziel> result = query.getResultList();
            em.close();
            return result;
        } else
            return new ArrayList<Ziel>();

    }

    public static Ziel getZiel(int id) {
        EntityManager em = ContextListener.createEntityManager();
        TypedQuery<Ziel> query = em.createNamedQuery("Ziel.findById",
                Ziel.class);
        query.setParameter("id", id);
        List<Ziel> result = query.getResultList();
        em.close();
        if (result.isEmpty())
            return null;
        else
            return result.get(0);
    }

}
