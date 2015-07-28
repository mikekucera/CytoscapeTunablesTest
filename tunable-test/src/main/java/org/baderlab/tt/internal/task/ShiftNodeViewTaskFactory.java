package org.baderlab.tt.internal.task;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;

public class ShiftNodeViewTaskFactory extends AbstractNodeViewTaskFactory {

    @Override
    public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView networkView) {
        return new TaskIterator(new ShiftNodeViewTask(nodeView, networkView), new JustAnotherTask());
    }

}
