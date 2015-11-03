package org.baderlab.dt.internal;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.ListSingleSelection;

public class TaskB extends AbstractTask {

    @Tunable(description="What value?")
    public ListSingleSelection<String> val = new ListSingleSelection<>("A","B","C");
    
    @Override
    public void run(TaskMonitor taskMonitor) {
        System.out.println("TaskB: " + val.getSelectedValue());
    }

}
