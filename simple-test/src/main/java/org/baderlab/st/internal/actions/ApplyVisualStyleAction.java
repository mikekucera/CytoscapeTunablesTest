package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ApplyVisualStyleAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    @Inject private VisualMappingManager visualMappingManager;
    
    @Inject
    public ApplyVisualStyleAction(CyApplicationManager applicationManager) {
        super("VisualStyle.apply()", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetworkView netView = applicationManager.getCurrentNetworkView();
        VisualStyle vs = visualMappingManager.getVisualStyle(netView);
        vs.apply(netView);
    }

}
