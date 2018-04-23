package org.baderlab.st.internal.commands;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class CountTask extends AbstractTask {

    public final int seconds;
    
    public CountTask(int seconds) {
        this.seconds = seconds;
    }
    
    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {
        taskMonitor.setTitle("Counting " + seconds + " seconds");
        taskMonitor.setProgress(0.0);
        for(int i = 0; i < seconds; i++) {
            taskMonitor.setStatusMessage("Tic: " + i);
            Thread.sleep(1000);
            taskMonitor.setProgress((double)i/(double)seconds);
        }
    }

}
