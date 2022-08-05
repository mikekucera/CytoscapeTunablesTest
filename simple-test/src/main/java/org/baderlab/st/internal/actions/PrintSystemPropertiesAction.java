package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.TreeMap;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.service.util.CyServiceRegistrar;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PrintSystemPropertiesAction extends AbstractCyAction {

    @Inject private CyServiceRegistrar registrar;
    
    @Inject
    public PrintSystemPropertiesAction(CyApplicationManager applicationManager) {
        super("Print System Properties", applicationManager, null, null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        printProps();
    }
    
    private static void printProps() {
        var properties = new TreeMap<>(System.getProperties());
        properties.forEach((k, v) -> System.out.println(k + ":" + v));
    }
    
    public static void main(String[] args) {
        printProps();
    }

}
