package org.baderlab.st.internal.tuneables;

import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class BoundedIntegerTaskFactory extends AbstractNetworkViewTaskFactory implements NetworkViewTaskFactory {

    @Override
    public TaskIterator createTaskIterator(CyNetworkView networkView) {
        return new TaskIterator(new BoundedIntegerTask());
    }
    
    @Override
    public boolean isReady(CyNetworkView networkView) {
        return true;
    }

}
