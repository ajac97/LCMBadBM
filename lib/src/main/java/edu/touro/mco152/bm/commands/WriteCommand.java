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
    private final WriteWorker worker;

    public WriteCommand(int numOfMarks, int numOfBlocks, int blockSizeKb, DiskRun.BlockSequence sequence, UserPlatform up){
       worker = new WriteWorker(numOfMarks, numOfBlocks, blockSizeKb, sequence, up);
    }
    @Override
    public void execute() {
        worker.doWork();
    }

    public WriteWorker getWorker() {
        return worker;
    }
}



