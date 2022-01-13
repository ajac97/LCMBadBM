package edu.touro.mco152.bm.workers.notifications;

import edu.touro.mco152.bm.SlackManager;
import edu.touro.mco152.bm.observers.Observer;
import edu.touro.mco152.bm.persist.DiskRun;

/**
 * This class is a concrete implementation of Observer interface that uses the slack manager class to send a message
 * based on certain criteria.
 */
public class NotificationRulesObserver implements Observer {
    @Override
    public void update() {

    }

    @Override
    public void update(Object o) {
        if(o instanceof DiskRun){
            DiskRun dr = (DiskRun) o;
            if(getPercentOfMaxOverAvg(dr) > 3) {
               sendMessage();
            }
        }
    }

    private void sendMessage() {
        SlackManager slackManager = new SlackManager("Benchmark");
        slackManager.postMsg2OurChannel("The Read Benchmark has a 'max time' that exceeds 3 percent of the benchmarks's average time.");
    }

    private double getPercentOfMaxOverAvg(DiskRun dr) {
        return 100 - ((dr.getRunAvg()/ dr.getRunAvg()) * 100);
    }
}
