package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.work.swing.PanelTaskManager;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * Creates a new menu item under Apps menu section.
 *
 */
@SuppressWarnings("serial")
public class DialogTestAction extends AbstractCyAction {

    @Inject private PanelTaskManager panelTaskManager;
    @Inject private CySwingApplication swingApplication;

    private final Object objectWithTunables;
    
    @Inject
    public DialogTestAction(CyApplicationManager applicationManager, @Assisted String title, @Assisted Object objectWithTunables) {
        super(title, applicationManager, null, null);
        this.objectWithTunables = objectWithTunables;
    }

    
    public void actionPerformed(ActionEvent e) {
        JPanel panel = panelTaskManager.getConfiguration(null, objectWithTunables);

        int result = JOptionPane.showConfirmDialog(swingApplication.getJFrame(), 
                                                   new Object[] { panel },
                                                   "Testing Tunables", 
                                                   JOptionPane.OK_CANCEL_OPTION);
        
        if(result == JOptionPane.OK_OPTION) {
            panelTaskManager.validateAndApplyTunables(objectWithTunables);
            JOptionPane.showMessageDialog(swingApplication.getJFrame(), objectWithTunables.toString());
        }
    }
}
