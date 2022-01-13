package edu.touro.mco152.bm.observers;

/**
 * Interface for all classes that will be observed
 */
public interface Observable {
    /**
     * adds an observer to the observables list of observers. This leverages DIP
     * @param observer
     */
    public void registerObserver(Observer observer);

    /**
     * This removes an observer
     * @param observer
     */
    public void removeObserver(Observer observer);

    /**
     * This loops through all observers and call the update method
     */
    public void notifyObservers();

}
