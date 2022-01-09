package generalTests;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.DiskWorker;
import edu.touro.mco152.bm.Invoker;
import edu.touro.mco152.bm.observers.Observer;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import edu.touro.mco152.bm.ui.UserPlatform;
import edu.touro.mco152.bm.workers.ReadWorker;
import edu.touro.mco152.bm.workers.WriteWorker;
import jakarta.persistence.EntityManager;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Properties;


public class TestObservers implements UserPlatform {

    private static MockObserver mock;


    @Test
    public void testReadBenchmark(){
        ReadWorker rw = new ReadWorker(25, 32, 128, DiskRun.BlockSequence.SEQUENTIAL, this);
        mock = new MockObserver();
        rw.registerObserver(mock);
        rw.doWork();
    }

    @Test
    public void testWriteBenchmark(){
        WriteWorker ww = new WriteWorker(25, 32, 128, DiskRun.BlockSequence.SEQUENTIAL, this);
        mock = new MockObserver();
        ww.registerObserver(mock);
        ww.doWork();
    }

    @AfterAll
    @Test
    public static void testObserverWasCalled(){
        Assertions.assertTrue(mock.getWasCalled());
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

    }

    @Override
    public void publishDiskMark(DiskMark dm) {

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
