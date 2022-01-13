package generalTests;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.DiskWorker;
import edu.touro.mco152.bm.Invoker;
import edu.touro.mco152.bm.commands.ReadCommand;
import edu.touro.mco152.bm.commands.WriteCommand;
import edu.touro.mco152.bm.persist.DiskRun.BlockSequence;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import edu.touro.mco152.bm.ui.UserPlatform;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This is a test class that tests compliance with the Command pattern.
 */

public class TestCommand implements UserPlatform {
    private final ArrayList<Integer> progressPercentages = new ArrayList();
    private final List<DiskMark> diskMarks = new ArrayList();
    private final Invoker testInvoker = new Invoker();
    private int latestProgressReport;
    private final int MARKS = 25;
    private final int BLOCKS = 128;
    private final int BLOCKSIZEKB = 2048;
    private final BlockSequence SEQUENCE = BlockSequence.SEQUENTIAL;

    /**
     * this method executes a read command and checks to make sure it runs to completion
     */

    @Test
    public void testReadCommand() {
        this.testInvoker.addCommand(new ReadCommand(MARKS, BLOCKS, BLOCKSIZEKB, SEQUENCE, this));
        this.testInvoker.invoke();
        Assertions.assertEquals(100, this.latestProgressReport);
    }

    /**
     * This method executes a write command and checks that it runs to completion
     */
    @Test
    public void testWriteCommand() {
        this.testInvoker.addCommand(new WriteCommand(MARKS, BLOCKS, BLOCKSIZEKB, SEQUENCE, this));
        this.testInvoker.invoke();
        Assertions.assertEquals(100, this.latestProgressReport);
    }

    public void setDiskWorker(DiskWorker dw) {
    }

    public boolean isTaskCancelled() {
        return false;
    }


    public void setProgressPercentage(int percentage) {
        this.progressPercentages.add(percentage);
        this.latestProgressReport = percentage;
    }

    public void publishDiskMark(DiskMark dm) {
        this.diskMarks.add(dm);
    }

    public void cancelBM(boolean b) {
    }

    public void executeBM() throws Exception {
    }

    public void addPropChange(PropertyChangeListener pce) {
    }

    /**
     * This method tests that the progress reports are within bounds along the way
     */
    @AfterEach
    public void testProgress() {
        for (int progress : progressPercentages){
            Assertions.assertTrue(progress >= 0 && progress <= 100);
        }
        Assertions.assertEquals(100, this.latestProgressReport);
    }

    /**
     * This makes sure that every Diskmark made some progress
     */

    @AfterEach
    public void testDiskMarks() {

        for (DiskMark dm: diskMarks){
            Assertions.assertTrue(dm.getBwMbSec() > 0);
            Assertions.assertTrue(dm.getCumAvg() > 0);
            Assertions.assertTrue(dm.getCumMax() > 0);
            Assertions.assertTrue(dm.getMarkNum() > 0);
            Assertions.assertTrue(dm.getCumMin() > 0);
        }

    }

    @BeforeAll
    private static void setupDefaultAsPerProperties() {
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar();
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);
        String var10002 = App.locationDir.getAbsolutePath();
        App.dataDir = new File(var10002 + File.separator + "jDiskMarkData");
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        } else {
            App.dataDir.mkdirs();
        }

    }
}

