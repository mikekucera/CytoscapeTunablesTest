package org.baderlab.st.internal.tasks;

import org.cytoscape.model.CyEdge;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class EdgeViewDoubleClickTaskFactory implements EdgeViewTaskFactory {

    @Override
    public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView networkView) {
        return new TaskIterator(new EdgeViewDoubleClickTask(edgeView, networkView));
    }

    @Override
    public boolean isReady(View<CyEdge> edgeView, CyNetworkView networkView) {
        return true;
    }

}
