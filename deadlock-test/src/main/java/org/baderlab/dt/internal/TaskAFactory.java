package org.baderlab.dt.internal;

import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

public class TaskAFactory implements TaskFactory {

    private final DialogTaskManager taskManager;
    
    public TaskAFactory(DialogTaskManager taskManager) {
        this.taskManager = taskManager;
    }
    
    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator(new TaskA(taskManager));
    }

    @Override
    public boolean isReady() {
        return true;
    }

}
