package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.TreeMap;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PrintServiceTypeAction extends AbstractCyAction {

    @Inject private BundleContext bc;
    
    
    @Inject
    public PrintServiceTypeAction(CyApplicationManager applicationManager) {
        super("Print Services", applicationManager, null, null);
    }
    
 
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Map<String,Integer> counts = getServieCounts();
            printCounts(counts);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    private void printCounts(Map<String, Integer> counts) {
        counts.forEach((type,count) -> {
            System.out.println(type + " - " + count);
        });
    }


    private Map<String,Integer> getServieCounts() throws Exception {
        Map<String,Integer> counts = new TreeMap<>();
        
        ServiceReference[] serviceReferences = bc.getAllServiceReferences(null, null);
        for(var ref : serviceReferences) {
            for(var key : ref.getPropertyKeys()) {
                if("objectClass".equals(key)) {
                    Object value = ref.getProperty(key);
                    String[] types = (String[]) value;
                    for(String type : types) {
                        counts.merge(type, 1, Integer::sum);
                    }
                }
            }
        }
        return counts;
    }
    

}
