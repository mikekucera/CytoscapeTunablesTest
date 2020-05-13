package org.baderlab.st.internal.tasks;

import org.cytoscape.application.CyUserLog;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkViewDoubleClickTask extends AbstractNetworkViewTask {

    private static final Logger logger = LoggerFactory.getLogger(CyUserLog.NAME);
    
    public NetworkViewDoubleClickTask(CyNetworkView view) {
        super(view);
    }

    @Override
    public void run(TaskMonitor tm) {
        logger.info("You double clicked on the network! " + view.getSUID());
    }

}
