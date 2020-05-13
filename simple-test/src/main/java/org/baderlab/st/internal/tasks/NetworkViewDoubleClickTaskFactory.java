package org.baderlab.st.internal.tasks;

import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;

public class NetworkViewDoubleClickTaskFactory implements NetworkViewTaskFactory {

    @Override
    public TaskIterator createTaskIterator(CyNetworkView networkView) {
        return new TaskIterator(new NetworkViewDoubleClickTask(networkView));
    }

    @Override
    public boolean isReady(CyNetworkView networkView) {
        return true;
    }

}
