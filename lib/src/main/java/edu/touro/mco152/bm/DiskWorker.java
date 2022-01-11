package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.ReadCommand;
import edu.touro.mco152.bm.commands.WriteCommand;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.UserPlatform;

import javax.swing.*;

import static edu.touro.mco152.bm.App.*;

/**
 * Run the disk benchmarking as a Swing-compliant thread (only one of these threads can run at
 * once.) Cooperates with Swing to provide and make use of interim and final progress and
 * information, which is also recorded as needed to the persistence store, and log.
 * <p>
 * Depends on static values that describe the benchmark to be done having been set in App and Gui classes.
 * The DiskRun class is used to keep track of and persist info about each benchmark at a higher level (a run),
 * while the DiskMark class described each iteration's result, which is displayed by the UI as the benchmark run
 * progresses.
 * <p>
 * This class only knows how to do 'read' or 'write' disk benchmarks. It is instantiated by the
 * startBenchmark() method.
 * <p>
 * To be Swing compliant this class extends SwingWorker and declares that its final return (when
 * doInBackground() is finished) is of type Boolean, and declares that intermediate results are communicated to
 * Swing using an instance of the DiskMark class.
 */

/**
 * Changed DiskWorker to no longer extend SwingWorker. Instead, it takes in a concrete implementation of the User
 *Platform interface to decide how to display the results of the BenchMark.
 */
public class DiskWorker {

    private UserPlatform up;
    private final Invoker invoker = new Invoker();

    public DiskWorker(UserPlatform up){
        this.up = up;
    }


    /**
     * This method instantiates commands in compliance with the Command pattern, and hands them to an invoker
     */
    private void getCommands(){
        if(readTest)
            invoker.addCommand(new ReadCommand(numOfMarks, numOfBlocks, blockSizeKb, blockSequence, up));
        if(writeTest)
            invoker.addCommand(new WriteCommand(numOfMarks, numOfBlocks, blockSizeKb, blockSequence, up));
    }

    /**
     *
     * @return
     * @throws Exception
     * This method gets the commands, and calls the invokers invoke method to make sure the commands are executed
     */
    public Boolean runTests() throws Exception {

        /*
          We 'got here' because: a) End-user clicked 'Start' on the benchmark UI,
          which triggered the start-benchmark event associated with the App::startBenchmark()
          method.  b) startBenchmark() then instantiated a DiskWorker, and called
          its (super class's) execute() method, causing Swing to eventually
          call this doInBackground() method.
         */
        System.out.println("*** starting new worker thread");

        getCommands();
        invoker.invoke();
        Gui.updateLegend();  // init chart legend info

        if (App.autoReset) {
            App.resetTestData();
            Gui.resetTestData();
        }





        /*
          Most benchmarking systems will try to do some cleanup in between 2 benchmark operations to
          make it more 'fair'. For example a networking benchmark might close and re-open sockets,
          a memory benchmark might clear or invalidate the Op Systems TLB or other caches, etc.
         */

        // try renaming all files to clear catch
        if (App.readTest && App.writeTest && !up.isTaskCancelled()) {
            JOptionPane.showMessageDialog(Gui.mainFrame,
                    "For valid READ measurements please clear the disk cache by\n" +
                            "using the included RAMMap.exe or flushmem.exe utilities.\n" +
                            "Removable drives can be disconnected and reconnected.\n" +
                            "For system drives use the WRITE and READ operations \n" +
                            "independantly by doing a cold reboot after the WRITE",
                    "Clear Disk Cache Now", JOptionPane.PLAIN_MESSAGE);
        }

        // Same as above, just for Read operations instead of Writes.

        App.nextMarkNumber += App.numOfMarks;
        return true;
    }






}
