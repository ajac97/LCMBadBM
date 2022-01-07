package edu.touro.mco152.bm.commands;


import edu.touro.mco152.bm.persist.DiskRun;

import edu.touro.mco152.bm.ui.UserPlatform;
import edu.touro.mco152.bm.workers.WriteWorker;

public class WriteCommand implements CommandInterface {
    private int numOfMarks;
    private int numOfBlocks;
    private int blockSizeKb;
    private DiskRun.BlockSequence sequence;
    private UserPlatform up;

    public WriteCommand(int numOfMarks, int numOfBlocks, int blockSizeKb, DiskRun.BlockSequence sequence, UserPlatform up){
        this.numOfMarks = numOfMarks;
        this.numOfBlocks = numOfBlocks;
        this.blockSizeKb = blockSizeKb;
        this.sequence = sequence;
        this.up = up;
    }
    @Override
    public void execute() {
        WriteWorker worker = new WriteWorker(numOfMarks, numOfBlocks, blockSizeKb, sequence, up);
        worker.doWork();
    }
}



