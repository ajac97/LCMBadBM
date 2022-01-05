package edu.touro.mco152.bm.ui;

import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.DiskWorker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This is an interface that will serve as a contract for any class that will serve as a means of displaying the
 * results of the BenchMark application. Whether it be run by the command line or by Swing or a web command, the class
 * should implement this interface. This way, the DiskWorker class will work with any concrete class that implements this interface.
 */

public interface UserPlatform {

    public void setDiskWorker(DiskWorker dw);

    public boolean isTaskCancelled();

    public void setProgressPercentage(int percentage);

    public void publishDiskMark(DiskMark dm);

    public void cancelBM(boolean b);

    public void executeBM() throws Exception;

    public void addPropChange(PropertyChangeListener pce);
}
