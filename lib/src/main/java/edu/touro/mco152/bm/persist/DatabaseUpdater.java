package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.observers.Observer;
import jakarta.persistence.EntityManager;

/**
 * Concrete implementation of the Observer interface. Will be used to update the database after a read or write
 */
public class DatabaseUpdater implements Observer {


    @Override
    public void update() {

    }

    /**
     * this is called by notifyObservers in an Observable class in which this class is registered.
     * @param o
     */
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
