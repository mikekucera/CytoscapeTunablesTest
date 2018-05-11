package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.service.util.CyServiceRegistrar;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class RowsSetListenAction extends AbstractCyAction {

    @Inject private CyServiceRegistrar serviceRegistrar;

    private RowsSetListener rowsSetListener = null;
    
    @Inject
    public RowsSetListenAction(CyApplicationManager applicationManager) {
        super("Listen for RowsSetEvent", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        registerListener();
        
    }
    
    class MyRowsSetListener implements RowsSetListener {
        @Override
        public void handleEvent(RowsSetEvent e) {
            CyTable source = e.getSource();
            Long suid = source.getSUID();
            int size = e.getPayloadCollection().size();
            System.out.printf("RowsSetEvent source:%s SUID:%s size:%s\n", source, suid, size);
        }
    }
    
    private synchronized void registerListener() {
        if(rowsSetListener == null) {
            rowsSetListener = new MyRowsSetListener();
            serviceRegistrar.registerAllServices(rowsSetListener, new Properties());
        }
    }

}
