package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.UserPlatform;
import edu.touro.mco152.bm.workers.ReadWorker;

public class ReadCommand implements CommandInterface{

    private final ReadWorker worker;

    public ReadCommand(int numOfMarks, int numOfBlocks, int blockSizeKb, DiskRun.BlockSequence blockSequence, UserPlatform up) {

        this.worker = new ReadWorker(numOfMarks, numOfBlocks, blockSizeKb, blockSequence, up);

    }

    @Override
    public void execute() {
        worker.doWork();
    }

    public ReadWorker getWorker(){
        return worker;
    }
}
