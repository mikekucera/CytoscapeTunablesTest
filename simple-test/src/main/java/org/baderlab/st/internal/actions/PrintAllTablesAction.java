package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.HashSet;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CySubNetwork;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PrintAllTablesAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    @Inject private CyNetworkTableManager networkTableManager;
    
    @Inject
    public PrintAllTablesAction(CyApplicationManager applicationManager) {
        super("Print All Tables", applicationManager, null, null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = applicationManager.getCurrentNetwork();
        
        CySubNetwork subNetwork = (CySubNetwork) network;
        CyRootNetwork rootNetwork = subNetwork.getRootNetwork();
        
        System.out.println("SubNetwork: " + subNetwork);
        printTables(subNetwork, CyNode.class);
        printTables(subNetwork, CyEdge.class);
        
        System.out.println("Root Network: " + rootNetwork);
        printTables(rootNetwork, CyNode.class);
        printTables(rootNetwork, CyEdge.class);
        
        System.out.println();
    }

    
    private void printTables(CyNetwork network, Class<? extends CyIdentifiable> type) {
        for(CyTable table : new HashSet<>(networkTableManager.getTables(network, type).values())) {
//            Collection<CyColumn> cols = table.getColumns();
//            System.out.append(cols.stream().map(CyColumn::getName).collect(joining(", "))).append('\n');
            System.out.println("Table: " + table  + " rows: " + table.getRowCount());
            table.getAllRows().stream().limit(10).forEach(row -> printRow(row));
            System.out.println();
        }
    }
    
    private void printRow(CyRow row) {
        String rs = row.get("SUID", Long.class) + ", " + row.get("Shared Name", String.class);
        
//        String rs = cols.stream()
//                .map(col -> row.get(col.getName(),col.getType()))
//                .map(String::valueOf)
//                .collect(joining(", "));
        System.out.append(rs).append('\n');
    }
    
}
