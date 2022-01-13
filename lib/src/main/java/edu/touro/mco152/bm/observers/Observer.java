package edu.touro.mco152.bm.observers;

/**
 * interface for all classes that will observe observables
 */

public interface Observer {

    /**
     * this will be called when an observable calls notifyObservers
     */
    public void update();

    /**
     * Overloaded for observables that need to pass in an artifact of data
     * @param o
     */
    public void update(Object o);

}
