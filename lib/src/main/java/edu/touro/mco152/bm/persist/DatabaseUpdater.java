package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.observers.Observer;
import jakarta.persistence.EntityManager;

public class DatabaseUpdater implements Observer {


    @Override
    public void update() {

    }

    @Override
    public void update(Object o) {
        if(o instanceof DiskRun) {
            EntityManager em = EM.getEntityManager();
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        }
    }
}
