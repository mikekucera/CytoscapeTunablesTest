package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class CreateNetworkTableAction extends AbstractCyAction {

    @Inject private CyTableFactory tableFactory;
    @Inject private CyApplicationManager applicationManager;
    @Inject private CyNetworkTableManager networkTableManager;
    
    @Inject
    public CreateNetworkTableAction(CyApplicationManager applicationManager) {
        super("Create Network Table", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String namespace = JOptionPane.showInputDialog("Table namespace?");
        
        CyNetwork network = applicationManager.getCurrentNetwork();
        
        CyTable table = tableFactory.createTable("MyTable", "SUID", Long.class, true, true);
        
        networkTableManager.setTable(network, CyNode.class, namespace, table);
        
        
        CyTable table2 = networkTableManager.getTable(network, CyNode.class, namespace);
        System.out.println(table2);
        
        Map<String,CyTable> tables = networkTableManager.getTables(network, CyNode.class);
        System.out.println(tables);
    }

}
