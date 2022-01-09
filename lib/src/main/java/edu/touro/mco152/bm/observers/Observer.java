package edu.touro.mco152.bm.observers;

import edu.touro.mco152.bm.persist.DiskRun;

public interface Observer {


    public void update();

    public void update(Object o);

}
