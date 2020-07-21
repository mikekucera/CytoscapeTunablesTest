package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class CreateNetworkAndViewAction extends AbstractCyAction {

    @Inject private CyNetworkFactory networkFactory;
    @Inject private CyNetworkNaming networkNaming;
    @Inject private CyNetworkManager networkManager;
    @Inject private CyNetworkViewFactory networkViewFactory;
    @Inject private CyNetworkViewManager networkViewManager;
    @Inject private SynchronousTaskManager<?> taskManager;
    @Inject private VisualMappingManager visualMappingManager;
    @Inject private ApplyPreferredLayoutTaskFactory layoutTaskFactory;
    
    private int numNetworks = 1;
    
    @Inject
    public CreateNetworkAndViewAction(CyApplicationManager applicationManager) {
        super("Create New Network and View", applicationManager, null, null);
    }
    
    public CreateNetworkAndViewAction setNumNetworks(int networks) {
        this.numNetworks = networks;
        setName("Create " + numNetworks + " New Networks and Views");
        return this;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < numNetworks; i++) {
            createNetworkAndView();
        }
    }
    
    private void createNetworkAndView() {
        // create network
        CyNetwork network = networkFactory.createNetwork();
        
        // create nodes and edges
        CyNode node1 = network.addNode();
        CyNode node2 = network.addNode();
        network.addEdge(node1, node2, false);
        
        // set names
        network.getDefaultNodeTable().getRow(node1.getSUID()).set(CyNetwork.NAME, "Node-1");
        network.getDefaultNodeTable().getRow(node2.getSUID()).set(CyNetwork.NAME, "Node-2");

        String netName = networkNaming.getSuggestedNetworkTitle("My Network");
        network.getDefaultNetworkTable().getRow(network.getSUID()).set(CyNetwork.NAME, netName);
        
        // register the network
        networkManager.addNetwork(network);
        
        // create a view
        CyNetworkView networkView = networkViewFactory.createNetworkView(network);
        networkViewManager.addNetworkView(networkView);
        
        // apply a style
        VisualStyle style = visualMappingManager.getCurrentVisualStyle();
        if(style != null) {
            visualMappingManager.setVisualStyle(style, networkView);
            style.apply(networkView);
        }
        
        // apply the preferred layout
        TaskIterator layoutTasks = layoutTaskFactory.createTaskIterator(Arrays.asList(networkView));
        taskManager.execute(layoutTasks);
    }
    

}
