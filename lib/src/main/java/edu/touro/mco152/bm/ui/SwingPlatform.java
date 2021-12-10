package edu.touro.mco152.bm.ui;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.DiskWorker;
import edu.touro.mco152.bm.Util;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.List;

import static edu.touro.mco152.bm.App.dataDir;

/**
 * This class is a concrete implementation of the UserPlatform interface. This class serves as the Swing user interface.
 * It abides by the contract set forth in the DiskWorker class and the UserPlatform interface.
 */

public class SwingPlatform extends SwingWorker<Boolean, DiskMark> implements UserPlatform {
    private DiskWorker dw;

    public SwingPlatform() {

    }
    @Override
    public void setDiskWorker(DiskWorker dw){
        this.dw = dw;
    }


    @Override
    public boolean isTaskCancelled() {
        return isCancelled();
    }

    @Override
    public void setProgressPercentage(int percentage) {
        setProgress(percentage);
    }

    @Override
    public void publishDiskMark(DiskMark dm) {
        publish(dm);

    }
    @Override
    public void cancelBM(boolean b) {
        cancel(b);
    }

    @Override
    public void executeBM() {
        execute();
    }

    @Override
    public void addPropChange(PropertyChangeListener pce) {
        addPropertyChangeListener(pce);
    }


    @Override
    protected Boolean doInBackground() throws Exception {
        return dw.runTests();
    }

    /**
     * Process a list of 'chunks' that have been processed, ie that our thread has previously
     * published to Swing. For my info, watch Professor Cohen's video -
     * Module_6_RefactorBadBM Swing_DiskWorker_Tutorial.mp4
     *
     * @param markList a list of DiskMark objects reflecting some completed benchmarks
     */

    @Override
    protected void process(List<DiskMark> markList) {
        markList.stream().forEach((dm) -> {
            if (dm.getType() == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }

    @Override
    protected void done() {
        if (App.autoRemoveData) {
            Util.deleteDirectory(dataDir);
        }
        App.state = App.State.IDLE_STATE;
        Gui.mainFrame.adjustSensitivity();
    }
}
