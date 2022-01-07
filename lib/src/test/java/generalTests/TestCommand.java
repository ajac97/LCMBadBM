package generalTests;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.DiskWorker;
import edu.touro.mco152.bm.Invoker;
import edu.touro.mco152.bm.commands.ReadCommand;
import edu.touro.mco152.bm.commands.WriteCommand;
import edu.touro.mco152.bm.persist.DiskRun;
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
 * This class is an implementation of the UserPlatform contract (interface).
 * It assures that the benchmark application is running as intended. It tests the percentages and the
 * DiskMarks to see that the values are acceptable.
 */

public class TestCommand implements UserPlatform {

    private final ArrayList<Integer> progressPercentages = new ArrayList<>();
    private final List<DiskMark> diskMarks = new ArrayList<>();
    private final Invoker testInvoker = new Invoker();
    private int latestProgressReport;


    @Test
    public void testReadCommand(){
        testInvoker.addCommand(new ReadCommand(25, 128, 512, DiskRun.BlockSequence.SEQUENTIAL, this));
        testInvoker.invoke();
        Assertions.assertEquals(100, latestProgressReport);
    }

    @Test
    public void testWriteCommand(){
        testInvoker.addCommand(new WriteCommand(25, 128, 512, DiskRun.BlockSequence.SEQUENTIAL, this));
        testInvoker.invoke();
        Assertions.assertEquals(100, latestProgressReport);
    }

    @Override
    public void setDiskWorker(DiskWorker dw) {

    }

    @Override
    public boolean isTaskCancelled() {
        return false;
    }



    @Override
    public void setProgressPercentage(int percentage) {
        progressPercentages.add(percentage);
        latestProgressReport = percentage;
    }

    @Override
    public void publishDiskMark(DiskMark dm) {
        diskMarks.add(dm);
    }

    @Override
    public void cancelBM(boolean b) {

    }

    @Override
    public void executeBM() throws Exception {

    }

    @Override
    public void addPropChange(PropertyChangeListener pce) {

    }

    @AfterEach
    public void testProgress(){
        for (Integer progress : progressPercentages) {
            Assertions.assertTrue(progress >= 0 && progress <= 100);
        }
        Assertions.assertEquals(100, latestProgressReport); //tests that last value is 100- meaning it completed
    }

    @AfterEach
    public void testDiskMarks(){
        try {
            executeBM();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (DiskMark dm : diskMarks) {
            Assertions.assertTrue(dm.getBwMbSec() > 0);
            Assertions.assertTrue(dm.getCumAvg() > 0);
            Assertions.assertTrue(dm.getCumMax() > 0);
            Assertions.assertTrue(dm.getMarkNum() > 0);
            Assertions.assertTrue(dm.getCumMin() > 0);
        }
    }

    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    @BeforeAll
    private static void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference
        App.dataDir = new File(App.locationDir.getAbsolutePath()+File.separator+App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        }
        else
        {
            App.dataDir.mkdirs(); // create data dir if not already present
        }
    }

}
