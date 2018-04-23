package org.baderlab.st.internal.commands;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class WriteToLogTaskFactory extends AbstractTaskFactory {

    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator(new WriteToLogTask1(), new WriteToLogTask2());
    }

}
