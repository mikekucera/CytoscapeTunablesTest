package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Collections;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class FitContentTest extends AbstractCyAction {

    @Inject private CyNetworkFactory networkFactory;
    @Inject private CyNetworkManager networkManager;
    @Inject private CyNetworkViewFactory networkViewFactory;
    @Inject private CyNetworkViewManager networkViewManager;
    @Inject private CyLayoutAlgorithmManager layoutManager;
    @Inject private SynchronousTaskManager<?> taskManager;
    
    @Inject
    public FitContentTest(CyApplicationManager applicationManager) {
        super("Test fitContent() before addNetworkView()", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = networkFactory.createNetwork();
        CyNode n1 = network.addNode();
        CyNode n2 = network.addNode();
        CyNode n3 = network.addNode();
        CyNode n4 = network.addNode();
        network.addEdge(n1, n2, false);
        network.addEdge(n2, n3, false);
        network.addEdge(n3, n4, false);
        network.addEdge(n4, n1, false);
        networkManager.addNetwork(network);
        
        CyNetworkView netView = networkViewFactory.createNetworkView(network);
        
        CyLayoutAlgorithm layout = layoutManager.getDefaultLayout();
        TaskIterator iter = layout.createTaskIterator(netView, layout.getDefaultLayoutContext(), Collections.emptySet(), null);
        taskManager.execute(iter);
        
        netView.fitContent();
        
        networkViewManager.addNetworkView(netView);
    }

}
