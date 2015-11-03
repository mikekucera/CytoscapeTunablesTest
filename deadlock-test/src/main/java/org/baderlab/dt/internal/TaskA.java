package org.baderlab.dt.internal;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.swing.DialogTaskManager;

public class TaskA extends AbstractTask {

    private final DialogTaskManager taskManager;
    
    public TaskA(DialogTaskManager taskManager) {
        this.taskManager = taskManager;
    }
    
    @Override
    public void run(TaskMonitor taskMonitor) {
        taskManager.execute(new TaskIterator(new TaskB()));
    }

}
