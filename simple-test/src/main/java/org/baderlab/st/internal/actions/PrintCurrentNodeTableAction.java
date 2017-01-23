package org.baderlab.st.internal.actions;

import static java.util.stream.Collectors.joining;

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.event.MenuEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.service.util.CyServiceRegistrar;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PrintCurrentNodeTableAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    @Inject private CyServiceRegistrar serviceRegistrar;
    
    @Inject
    public PrintCurrentNodeTableAction(CyApplicationManager applicationManager) {
        super("Print Current Node Table", applicationManager, null, null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = applicationManager.getCurrentNetwork();
        CyTable nodeTable = network.getDefaultNodeTable();
        Collection<CyColumn> cols = nodeTable.getColumns();
        
        StringBuilder out = new StringBuilder();
        out.append(cols.stream().map(CyColumn::getName).collect(joining(", "))).append('\n');
        
        for(CyRow row : nodeTable.getAllRows()) {
            String rs = cols.stream()
                .map(col -> row.get(col.getName(),col.getType()))
                .map(String::valueOf)
                .collect(joining(", "));
            out.append(rs).append('\n');
        }
        
        System.out.println(out.toString());
    }
    
    @Override
    public void menuSelected(MenuEvent e) {
        CySwingApplication swingApplication = serviceRegistrar.getService(CySwingApplication.class);
        System.out.println("PrintCurrentNodeTableAction: " + swingApplication);
    }

}
