package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.events.ViewChangedEvent;
import org.cytoscape.view.model.events.ViewChangedListener;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ViewChangedListenAction extends AbstractCyAction {

    @Inject private CyServiceRegistrar serviceRegistrar;

    private MyListener listener = null;
    
    @Inject
    public ViewChangedListenAction(CyApplicationManager applicationManager) {
        super("Listen for ViewChangedEvent", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        registerListener();
        
    }
    
    private class MyListener implements ViewChangedListener {
        @Override
        public void handleEvent(ViewChangedEvent<?> e) {
            Object source = e.getSource();
            int size = e.getPayloadCollection().size();
            System.out.printf("ViewChangedEvent source:%s size:%s\n", source, size);
        }
    }
    
    private synchronized void registerListener() {
        if(listener == null) {
            listener = new MyListener();
            serviceRegistrar.registerAllServices(listener, new Properties());
        }
    }

}
