package org.baderlab.st.internal;

import org.cytoscape.model.events.RemovedEdgesEvent;
import org.cytoscape.model.events.RemovedEdgesListener;
import org.cytoscape.model.events.RemovedNodesEvent;
import org.cytoscape.model.events.RemovedNodesListener;

public class NodeEdgeListener implements RemovedNodesListener, RemovedEdgesListener {

    @Override
    public void handleEvent(RemovedEdgesEvent e) {
        System.out.println("RemovedEdgesEvent: " + e.getSource());
    }

    @Override
    public void handleEvent(RemovedNodesEvent e) {
        System.out.println("RemovedNodesEvent: " + e.getSource());
    }

}
