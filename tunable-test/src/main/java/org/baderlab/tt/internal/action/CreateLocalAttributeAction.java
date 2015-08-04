package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;

import com.google.inject.Inject;

/**
 * Creates an attribute (table column) in the local node table for the current network.
 * Needed this because there doesn't seem to be a way to do this through the Cytoscape UI.
 */
@SuppressWarnings("serial")
public class CreateLocalAttributeAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    
    @Inject
    public CreateLocalAttributeAction(CyApplicationManager applicationManager) {
        super("Create Local Attribute", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = applicationManager.getCurrentNetwork();
        if(network == null)
            return;
        
        String name = JOptionPane.showInputDialog("Local Attribute Name");
        
        if(name != null && !name.isEmpty()) {
            CyTable localNodeTable = network.getTable(CyNode.class, CyNetwork.LOCAL_ATTRS);
            localNodeTable.createColumn(name, Integer.class, false);
        }

    }

}
