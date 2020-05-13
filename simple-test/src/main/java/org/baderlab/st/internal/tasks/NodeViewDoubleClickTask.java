package org.baderlab.st.internal.tasks;

import org.cytoscape.application.CyUserLog;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeViewDoubleClickTask extends AbstractNodeViewTask {

    private static final Logger logger = LoggerFactory.getLogger(CyUserLog.NAME);
    
    
    public NodeViewDoubleClickTask(View<CyNode> nodeView, CyNetworkView netView) {
        super(nodeView, netView);
    }

    @Override
    public void run(TaskMonitor tm) {
        logger.info("You double clicked on a node! " + nodeView.getSUID());
    }

}
