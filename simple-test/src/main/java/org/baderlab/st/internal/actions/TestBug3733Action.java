package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.CyServiceRegistrar;

import com.google.inject.Inject;

public class TestBug3733Action extends AbstractCyAction {

    public TestBug3733Action() {
        super("Simple Test");
    }

    @Inject private CyServiceRegistrar serviceRegistrar;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CySwingApplication swingApplication = serviceRegistrar.getService(CySwingApplication.class);

    }

}
