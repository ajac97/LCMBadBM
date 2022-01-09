package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.UserPlatform;
import edu.touro.mco152.bm.workers.ReadWorker;

/**
 * This is a concrete implementation of the CommandInterface interface that will act as a Read benchmark
 */

public class ReadCommand implements CommandInterface{
    private int numOfMarks;
    private int numOfBlocks;
    private int blockSizeKb;
    private DiskRun.BlockSequence sequence;
    private UserPlatform up;
    public ReadCommand(int numOfMarks, int numOfBlocks, int blockSizeKb, DiskRun.BlockSequence blockSequence, UserPlatform up) {
        this.numOfMarks = numOfMarks;
        this.numOfBlocks = numOfBlocks;
        this.blockSizeKb = blockSizeKb;
        this.sequence = sequence;
        this.up = up;
    }

    /**
     * This method overrides the execute method from the CommandInterface and executes a ReadCommand by instantiating
     * a ReadWorker class that will actually do the Read benchmark.
     */

    @Override
    public void execute() {
        ReadWorker worker = new ReadWorker(numOfMarks, numOfBlocks, blockSizeKb, sequence, up);
        worker.doWork();
    }
}
