package edu.touro.mco152.bm.observers;

public interface Observable {

    public void registerObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers();

}
