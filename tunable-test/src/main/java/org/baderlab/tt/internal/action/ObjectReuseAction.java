package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.baderlab.tt.internal.tunables.Line;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.work.swing.PanelTaskManager;

import com.google.inject.Inject;

/**
 * Creates a new menu item under Apps menu section.
 *
 */
@SuppressWarnings("serial")
public class ObjectReuseAction extends AbstractCyAction {

    @Inject private PanelTaskManager panelTaskManager;
    @Inject private CySwingApplication swingApplication;
    
    private boolean showResultPopup = true;
    
    @Inject
    public ObjectReuseAction(CyApplicationManager applicationManager) {
        super("Object Reuse Test", applicationManager, null, null);
    }

    public ObjectReuseAction setShowResultPopup(boolean show) {
        this.showResultPopup = show;
        return this;
    }
    
    public void actionPerformed(ActionEvent e) {
        Line line = new Line();
        showDialog(line);
        line.startPoint.x = 99;
        showDialog(line);
    }
    
    public void showDialog(Object objectWithTunables) {
        JPanel panel = panelTaskManager.getConfiguration(null, objectWithTunables);

        int result = JOptionPane.showConfirmDialog(swingApplication.getJFrame(), 
                                                   new Object[] { panel },
                                                   "Testing Tunables", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION) {
            panelTaskManager.validateAndApplyTunables(objectWithTunables);
            
            if(showResultPopup) {
                JOptionPane.showMessageDialog(swingApplication.getJFrame(), objectWithTunables.toString());
            }
        }
    }
}
