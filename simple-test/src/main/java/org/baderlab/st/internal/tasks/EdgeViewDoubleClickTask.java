package org.baderlab.st.internal.tasks;

import org.cytoscape.application.CyUserLog;
import org.cytoscape.model.CyEdge;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EdgeViewDoubleClickTask extends AbstractEdgeViewTask {

    private static final Logger logger = LoggerFactory.getLogger(CyUserLog.NAME);
    
    
    public EdgeViewDoubleClickTask(View<CyEdge> edgeView, CyNetworkView netView) {
        super(edgeView, netView);
    }

    @Override
    public void run(TaskMonitor tm) {
        logger.info("You double clicked on an edge! " + edgeView.getSUID());
    }

}
