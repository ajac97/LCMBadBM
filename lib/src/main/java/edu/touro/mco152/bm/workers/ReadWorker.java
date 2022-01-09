package edu.touro.mco152.bm.workers;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.Util;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.UserPlatform;
import jakarta.persistence.EntityManager;

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

/**
 * This is a concrete implementation of the WorkerInterface that is responsible for being a receiver for the ReadCommand.
 * It actually runs the read benchmark in the doWork() method.
 */
public class ReadWorker implements WorkerInterface {

    private int numOfMarks;
    private int numOfBlocks;
    private int blockSizeKb;
    private DiskRun.BlockSequence sequence;
    private UserPlatform up;
    DiskMark rMark;
    int startFileNum = App.nextMarkNumber;

    public ReadWorker(int numOfMarks, int numOfBlocks, int blockSizeKb, DiskRun.BlockSequence sequence, UserPlatform up){
        this.numOfMarks = numOfMarks;
        this.numOfBlocks = numOfBlocks;
        this.blockSizeKb = blockSizeKb;
        this.sequence = sequence;
        this.up = up;

    }

    /**
     * overriden method from WorkerInterface that actually does the read benchmark.
     */
    public void doWork() {
        msg("Running readTest " + App.readTest);
        msg("num files: " + numOfMarks + ", num blks: " + numOfBlocks
                + ", blk size (kb): " + blockSizeKb + ", blockSequence: " + sequence);
        int unitsComplete = 0;
        int unitsTotal = numOfBlocks * numOfMarks;
        float percentComplete;
        int blockSize = blockSizeKb * KILOBYTE;
        byte[] blockArr = new byte[blockSize];

        for (int b = 0; b < blockArr.length; b++) {
            if (b % 2 == 0) {
                blockArr[b] = (byte) 0xFF;
            }
        }

        DiskRun run = new DiskRun(DiskRun.IOMode.READ, sequence);
        run.setNumMarks(numOfMarks);
        run.setNumBlocks(numOfBlocks);
        run.setBlockSize(blockSizeKb);
        run.setTxSize(App.targetTxSizeKb());
        run.setDiskInfo(Util.getDiskInfo(dataDir));

        msg("disk info: (" + run.getDiskInfo() + ")");

        Gui.chartPanel.getChart().getTitle().setVisible(true);
        Gui.chartPanel.getChart().getTitle().setText(run.getDiskInfo());

        for (int m = startFileNum; m < startFileNum + numOfMarks && !up.isTaskCancelled(); m++) {

            if (App.multiFile) {
                testFile = new File(dataDir.getAbsolutePath()
                        + File.separator + "testdata" + m + ".jdm");
            }
            rMark = new DiskMark(READ);
            rMark.setMarkNum(m);
            long startTime = System.nanoTime();
            long totalBytesReadInMark = 0;

            try {
                try (RandomAccessFile rAccFile = new RandomAccessFile(testFile, "r")) {
                    for (int b = 0; b < numOfBlocks; b++) {
                        if (sequence == DiskRun.BlockSequence.RANDOM) {
                            int rLoc = Util.randInt(0, numOfBlocks - 1);
                            rAccFile.seek((long) rLoc * blockSize);
                        } else {
                            rAccFile.seek((long) b * blockSize);
                        }
                        rAccFile.readFully(blockArr, 0, blockSize);
                        totalBytesReadInMark += blockSize;
                        unitsComplete++;
                        percentComplete = (float) unitsComplete / (float) unitsTotal * 100f;
                        up.setProgressPercentage((int) percentComplete);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            long endTime = System.nanoTime();
            long elapsedTimeNs = endTime - startTime;
            double sec = (double) elapsedTimeNs / (double) 1000000000;
            double mbRead = (double) totalBytesReadInMark / (double) MEGABYTE;
            rMark.setBwMbSec(mbRead / sec);
            msg("m:" + m + " READ IO is " + rMark.getBwMbSec() + " MB/s    "
                    + "(MBread " + mbRead + " in " + sec + " sec)");
            App.updateMetrics(rMark);
            up.publishDiskMark(rMark);

            run.setRunMax(rMark.getCumMax());
            run.setRunMin(rMark.getCumMin());
            run.setRunAvg(rMark.getCumAvg());
            run.setEndTime(new Date());
        }

        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();

        Gui.runPanel.addRun(run);
    }

}
