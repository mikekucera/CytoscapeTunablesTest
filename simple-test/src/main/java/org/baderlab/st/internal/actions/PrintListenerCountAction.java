package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.event.CyListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PrintListenerCountAction extends AbstractCyAction {

    @Inject private BundleContext bc;
    
    private List<Map<Class<?>,Integer>> countMaps = new ArrayList<>();
    
    
    @Inject
    public PrintListenerCountAction(CyApplicationManager applicationManager) {
        super("Print Listeners", applicationManager, null, null);
    }
    
    public AbstractCyAction getResetAction() {
        return new AbstractCyAction("Print Listeners - Reset") {
            @Override
            public void actionPerformed(ActionEvent e) {
                countMaps.clear();
            }
        };
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            var counts = getListenerCounts();
            countMaps.add(counts);
            var mergedCounts = merge(countMaps);
            printListeners(mergedCounts);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static boolean isListener(Class<?> klass) {
        return CyListener.class.isAssignableFrom(klass);
    }
    
    private List<String> getListenerInterfaces(Class<?> klass) {
        List<String> interfaces = new ArrayList<>();
        for(var inter : klass.getInterfaces()) {
            if(isListener(inter)) {
                interfaces.add(inter.getSimpleName());
            }
        }
        interfaces.sort(Comparator.naturalOrder());
        return interfaces;
    }
    
    private Map<Class<?>,Integer> getListenerCounts() throws InvalidSyntaxException {
        Map<Class<?>,Integer> listenerCounts = new HashMap<>();
        ServiceReference[] allServiceReferences = bc.getAllServiceReferences(null, null);
        for(ServiceReference ref : allServiceReferences) {
            Object service = bc.getService(ref);
            var klass = service.getClass();
            if(isListener(klass)) {
                listenerCounts.merge(klass, 1, Integer::sum);
            }
        }
        return listenerCounts;
    }
    
    private Map<Class<?>,List<Integer>> merge(List<Map<Class<?>,Integer>> countMaps) {
        Map<Class<?>,List<Integer>> merged = new TreeMap<>(Comparator.comparing(Class::getSimpleName));
        for(var map : countMaps) {
            map.forEach((klass,count) -> {
                merged.computeIfAbsent(klass, k -> new ArrayList<>()).add(count);
            });
        }
        return merged;
    }
    
    private static boolean isIncreasing(List<Integer> values) {
        if(values.isEmpty())
            return false;
        var iter = values.iterator();
        int x = iter.next();
        while(iter.hasNext()) {
            int y = iter.next();
            if(y > x)
                return true;
            x = y;
        }
        return false;
    }
    
    private void printListeners(Map<Class<?>,List<Integer>> mergedCounts)  {
        System.out.println("Listeners...");
        mergedCounts.forEach((klass,counts) -> {
            if(isIncreasing(counts)) {
                System.out.print("*** GOING UP *** ");
            }
            List<String> interfaces = getListenerInterfaces(klass);
            System.out.print(klass.getSimpleName());
            System.out.print(" (");
            System.out.print(String.join(",", interfaces));
            System.out.print(") - ");
            System.out.print(counts);
            System.out.println();
        });
        System.out.println();
        
    }

}
