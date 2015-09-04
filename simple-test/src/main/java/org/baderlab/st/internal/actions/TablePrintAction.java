package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import com.google.inject.Inject;
import static java.util.stream.Collectors.*;

@SuppressWarnings("serial")
public class TablePrintAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    
    @Inject
    public TablePrintAction(CyApplicationManager applicationManager) {
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

}
