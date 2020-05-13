package org.baderlab.st.internal.tasks;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class NodeViewDoubleClickTaskFactory implements NodeViewTaskFactory {

    @Override
    public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
        return new TaskIterator(new NodeViewDoubleClickTask(nodeView, networkView));
    }

    @Override
    public boolean isReady(View<CyNode> nodeView, CyNetworkView networkView) {
        return true;
    }

}
