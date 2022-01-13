package edu.touro.mco152.bm.workers;

/**
 * Acts as a contract for all classes that will act as a receiver of a command. It simply has one method called
 * doWork() that will do what needs to be done.
 */
public interface WorkerInterface {

    void doWork();
}
