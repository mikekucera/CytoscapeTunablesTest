package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.subnetwork.CyRootNetwork;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class FindNodeNamedAAction extends AbstractCyAction {

    @Inject private CyApplicationManager appManager;
    
    @Inject
    public FindNodeNamedAAction(CyApplicationManager applicationManager) {
        super("Find Nodes with shared name \"A\"", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = appManager.getCurrentNetwork();
        CyTable nodeTable = network.getDefaultNodeTable();
        
        Collection<CyRow> rows = nodeTable.getMatchingRows(CyRootNetwork.SHARED_NAME, "A");
        if(rows.isEmpty()) {
            System.out.println("No nodes named \"A\" :(");
        }
        for(CyRow row : rows) {
            System.out.println(row.get(CyNetwork.SUID, Long.class));
        }
    }

}
