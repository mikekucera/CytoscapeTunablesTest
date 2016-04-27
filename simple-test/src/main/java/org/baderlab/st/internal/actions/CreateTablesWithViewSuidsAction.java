package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.LongStream;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class CreateTablesWithViewSuidsAction extends AbstractCyAction {

    @Inject private CyNetworkManager networkManager;
    @Inject private CyNetworkViewManager networkViewManager;
    @Inject private CyNetworkTableManager networkTableManager;
    
    @Inject private CyTableManager tableManager;
    @Inject private CyTableFactory tableFactory;

    
    public CreateTablesWithViewSuidsAction() {
        super("Create Tables with View SUIDs");
    }
    
    private static final String NET_SUID = "networkView.SUID";
    private static final String NODE_SUID = "nodeView.SUID";
    private static final String EDGE_SUID = "edgeView.SUID";
    private static final String OLD_SUID = "old_suid";
    private static final String TYPE = "type";

    @Override
    public void actionPerformed(ActionEvent e) {
        Set<CyNetwork> networkSet = networkManager.getNetworkSet();
        for(CyNetwork network : networkSet) {
            CyTable table = createTable(network, "View SUIDs");
            table.createColumn(NET_SUID, Long.class, true);
            table.createColumn(NODE_SUID, Long.class, true);
            table.createColumn(EDGE_SUID, Long.class, true);
            table.createColumn(OLD_SUID, Long.class, true);
            table.createColumn(TYPE, String.class, true);
            
            Iterator<Long> ids = LongStream.iterate(0, i -> i + 1).iterator();
            
            for(CyNetworkView networkView : networkViewManager.getNetworkViews(network)) {
                CyRow networkViewRow = table.getRow(ids.next());
                networkViewRow.set(NET_SUID, networkView.getSUID());
                networkViewRow.set(TYPE, "Network");
                networkViewRow.set(OLD_SUID, networkView.getSUID());
                
                for(View<CyNode> nodeView : networkView.getNodeViews()) {
                    CyRow nodeViewRow = table.getRow(ids.next());
                    nodeViewRow.set(NODE_SUID, nodeView.getSUID());
                    nodeViewRow.set(TYPE, "Node");
                    nodeViewRow.set(OLD_SUID, nodeView.getSUID());
                }
                
                for(View<CyEdge> edgeView : networkView.getEdgeViews()) {
                    CyRow edgeViewRow = table.getRow(ids.next());
                    edgeViewRow.set(EDGE_SUID, edgeView.getSUID());
                    edgeViewRow.set(TYPE, "Edge");
                    edgeViewRow.set(OLD_SUID, edgeView.getSUID());
                }
            }
        }
    }
    

    private CyTable createTable(CyNetwork network, String namespace) {
        CyTable table = networkTableManager.getTable(network, CyNetwork.class, namespace);
        if(table != null) {
            tableManager.deleteTable(table.getSUID());
        }
        table = tableFactory.createTable(namespace, "ID", Long.class, true, true);
        networkTableManager.setTable(network, CyNetwork.class, namespace, table);
        tableManager.addTable(table);
        
        return table;
    }
    
    
}
