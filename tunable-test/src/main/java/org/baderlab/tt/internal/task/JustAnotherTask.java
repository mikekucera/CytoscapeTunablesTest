package org.baderlab.tt.internal.task;

import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class JustAnotherTask implements Task {

    
    @Tunable(description="Are you happy to get multiple dialogs?")
    public boolean meh;
    
    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {

    }

    @Override
    public void cancel() {
    }

}
