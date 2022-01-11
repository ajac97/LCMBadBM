package edu.touro.mco152.bm.commands;

/**
 * This is an interface that is a contract for all classes that will be used as Command objects.
 * It simply has one method called execute that all concrete implementations will have to implement.
 */
public interface CommandInterface {

    /**
     * This method will execute the Concrete implementation's command
     */
    public void execute();

}
