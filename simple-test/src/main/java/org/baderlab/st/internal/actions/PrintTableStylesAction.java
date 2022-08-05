package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.vizmap.TableVisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PrintTableStylesAction extends AbstractCyAction {

    @Inject private CyServiceRegistrar registrar;
    
    @Inject
    public PrintTableStylesAction(CyApplicationManager applicationManager) {
        super("Print Table Styles", applicationManager, null, null);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       var tableVMM = registrar.getService(TableVisualMappingManager.class);
       
       System.out.println();
       System.out.println("Direct associated styles:");
       for(var entry : tableVMM.getAllVisualStylesMap().entrySet()) {
           var colView = entry.getKey();
           var style = entry.getValue();
           System.out.println("Direct: " + colView.getModel().getName() + " -> " + styleToString(style));
       }

       System.out.println();
       System.out.println("Style associations");
       for(var ass : tableVMM.getAllStyleAssociations()) {
           var netStyle = ass.networkVisualStyle();
           var colName = ass.colName();
           var colStyle = ass.columnVisualStyle();
           var tableType = ass.tableType();
           
           System.out.println("Association: " + styleToString(netStyle) + ", " + tableType.getSimpleName() + ", " + colName + " -> " + styleToString(colStyle));
       }
    }
    
    
    private static String styleToString(VisualStyle style) {
        return "Style(" + style.getTitle() + ":" + style.hashCode() + ")";
    }

}
