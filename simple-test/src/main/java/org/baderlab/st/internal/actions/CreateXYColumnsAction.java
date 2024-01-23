package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class CreateXYColumnsAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    
    @Inject
    public CreateXYColumnsAction(CyApplicationManager applicationManager) {
        super("Create Node X/Y Columns", applicationManager, null, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var networkView = applicationManager.getCurrentNetworkView();
        if(networkView == null)
            return;
        
        var network = networkView.getModel();
        
        var nodeTable = network.getDefaultNodeTable();
        nodeTable.createColumn("X_coord", Double.class, false);
        nodeTable.createColumn("Y_coord", Double.class, false);
        
        for(var nodeView : networkView.getNodeViewsIterable()) {
            var x = nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
            var y = nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
            
            var row = network.getRow(nodeView.getModel());
            row.set("X_coord", x);
            row.set("Y_coord", y);
        }
    }

}
