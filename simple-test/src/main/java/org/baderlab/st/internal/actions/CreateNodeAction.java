package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.Visualizable;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;

import com.google.inject.Inject;

/**
 * Purpose of this action is to create a node and see how the network view reacts.
 * 
 * @author mkucera
 *
 */
@SuppressWarnings("serial")
public class CreateNodeAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    @Inject private CyEventHelper eventHelper;
    
    @Inject
    public CreateNodeAction(CyApplicationManager applicationManager) {
        super("Create a Node", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetworkView netView = applicationManager.getCurrentNetworkView();
        CyNetwork network = netView.getModel();
        CyNode node = network.addNode();
        
        eventHelper.flushPayloadEvents();
        
        View<CyNode> nodeView = netView.getNodeView(node);
        nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 100d);
        
        System.out.println("herehrehre");
        nodeView.setLockedValue(BasicVisualLexicon.NODE, new Visualizable() {
        });
    }

}
