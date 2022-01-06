package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.Util;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.UserPlatform;
import edu.touro.mco152.bm.workers.WriteWorker;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.App.msg;
import static edu.touro.mco152.bm.DiskMark.MarkType.READ;
import static edu.touro.mco152.bm.DiskMark.MarkType.WRITE;

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



