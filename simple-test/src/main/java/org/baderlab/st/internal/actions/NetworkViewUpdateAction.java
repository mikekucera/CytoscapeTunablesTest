package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.view.model.CyNetworkView;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class NetworkViewUpdateAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    
    @Inject
    public NetworkViewUpdateAction(CyApplicationManager applicationManager) {
        super("Call CyNetworkView.updateView()", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetworkView networkView = applicationManager.getCurrentNetworkView();
        if(networkView != null)
            networkView.updateView();
    }

}
