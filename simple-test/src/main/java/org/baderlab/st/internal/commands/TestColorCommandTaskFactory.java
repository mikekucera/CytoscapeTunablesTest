package org.baderlab.st.internal.commands;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class TestColorCommandTaskFactory extends AbstractTaskFactory {

    @Override
    public TaskIterator createTaskIterator() {
        return new TaskIterator(new TestColorCommandTask());
    }

}
