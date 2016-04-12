package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PrintVisualMappingTypesAction extends AbstractCyAction {

    @Inject private CyApplicationManager appManager;
    @Inject private VisualMappingManager visualMappingManager;
    
    public PrintVisualMappingTypesAction() {
        super("Print Visual Mapping Types");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetworkView networkView = appManager.getCurrentNetworkView();
        VisualStyle visualStyle = visualMappingManager.getVisualStyle(networkView);
        
        for(VisualMappingFunction<?, ?> vmFunc : visualStyle.getAllVisualMappingFunctions()) {
            String propName = vmFunc.getVisualProperty().getDisplayName();
            Class<?> type = vmFunc.getMappingColumnType();
            
            
            String colName = vmFunc.getMappingColumnName();
            CyColumn col = networkView.getModel().getDefaultNodeTable().getColumn(colName);
            Class<?> colType = col.getType();
            
            System.out.println(propName + ": " + colName + ", "+ type.getName() + ", " + colType);
        }
        System.out.println();
        
    }

}