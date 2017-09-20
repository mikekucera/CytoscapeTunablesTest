package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;


@SuppressWarnings("serial")
public class TestRestoreEdgeAction extends AbstractCyAction {

    @Inject private DialogTaskManager taskManager;
    @Inject private CyNetworkViewManager networkViewManager;
    @Inject private CyNetworkManager networkManager;
    @Inject private CyRootNetworkManager rootNetworkManager;
    @Inject private CyApplicationManager applicationManager;
    @Inject private CyNetworkFactory networkFactory;
    @Inject private CyNetworkViewFactory networkViewFactory;
    @Inject private CyLayoutAlgorithmManager layoutAlgorithmManager;
    
    private int step = 0;
    
    private CyNetwork network;
    private CyRootNetwork rootNetwork;
    private CyNetworkView networkView;
    private Map<String,CyNode> nodes = new HashMap<>();
    private Map<String,CyEdge> edges = new HashMap<>();
    
    
    public TestRestoreEdgeAction() {
        super("Test Restore Edge");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if(step == 0) {
                System.out.println("createNetwork()");
                createNetwork();
            }
            else if(step == 1) {
                System.out.println("problem1()");
                problem1();
            }
            else if(step == 2) {
                System.out.println("problem1()");
                problem2();
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            step++;
        }
    }
    
    /**
     * The first problem is that if you delete nodes and edges and you restoreEdge() without restoring
     * the source/target first you run into problems
     */
    public void problem1() {
        CyNode c = nodes.get("c");
        CyNode d = nodes.get("d");
        CyEdge cd = edges.get("cd");
        network.removeNodes(Arrays.asList(c,d));
        
        rootNetwork.restoreEdge(cd);
        
        networkView.updateView();
    }
    
    /**
     * Second problem is if you delete 
     */
    public void problem2() {
        CyNode c = nodes.get("c");
        CyNode d = nodes.get("d");
        CyEdge cd = edges.get("cd");
        network.removeNodes(Arrays.asList(c,d));
        
        rootNetwork.restoreEdge(cd);
        
        networkView.updateView();
    }
    
    
    private CyNetwork createNetwork() {
        network = networkFactory.createNetwork();
        
        CyNode a = addNode(network, "a");
        CyNode b = addNode(network, "b");
        CyNode c = addNode(network, "c");
        CyNode d = addNode(network, "d");
        CyEdge ab = network.addEdge(a, b, false);
        CyEdge cd = network.addEdge(c, d, false);
        
        nodes.put("a", a);
        nodes.put("b", b);
        nodes.put("c", c);
        nodes.put("d", d);
        edges.put("ab", ab);
        edges.put("cd", cd);
        
        networkManager.addNetwork(network);
        rootNetwork = rootNetworkManager.getRootNetwork(network);
        
        networkView = networkViewFactory.createNetworkView(network);
        networkViewManager.addNetworkView(networkView);
        
        layout(networkView);
        networkView.updateView();
        return network;
    }
    
    private void layout(CyNetworkView networkView) {
        CyLayoutAlgorithm layout = layoutAlgorithmManager.getLayout("force-directed");
        if (layout == null)
            layout = layoutAlgorithmManager.getDefaultLayout();
        
        Object context = layout.createLayoutContext();
        TaskIterator layoutTasks = layout.createTaskIterator(networkView, context, CyLayoutAlgorithm.ALL_NODE_VIEWS, null);
        taskManager.execute(layoutTasks);
    }
    
    
    private CyNode addNode(CyNetwork network, String name) {
        CyNode node = network.addNode();
        network.getRow(node).set(CyNetwork.NAME, name);
        return node;
    }

}
