package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class RowsSetFacadeTestAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    @Inject private CyEventHelper eventHelper;
    
    
    @Inject
    public RowsSetFacadeTestAction(CyApplicationManager applicationManager) {
        super("Row Set Facade Test", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("RowsSetFacadeTestAction");
        
        CyNetwork network = applicationManager.getCurrentNetwork();
        CyNode node = network.getNodeList().get(0);
        
        CyTable localTableFacade = network.getDefaultNodeTable();
        
        localTableFacade.getRow(node.getSUID()).set("selected", true);
        
        eventHelper.flushPayloadEvents();
    }

}
