package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Properties;

import org.baderlab.st.internal.NodeEdgeListener;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.service.util.CyServiceRegistrar;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ReportNodeEdgeRemovalAction extends AbstractCyAction {

    @Inject private CyServiceRegistrar serviceRegistrar;
    
    private boolean registered = false;
    private NodeEdgeListener listener = new NodeEdgeListener();

    
    public ReportNodeEdgeRemovalAction() {
        super("Toggle Reporting Node/Edge removal events");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(registered)
            serviceRegistrar.unregisterAllServices(listener);
        else
            serviceRegistrar.registerAllServices(listener, new Properties());
    }

}
