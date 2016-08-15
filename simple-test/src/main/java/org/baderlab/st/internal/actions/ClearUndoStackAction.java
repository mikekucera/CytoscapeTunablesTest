package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.undo.UndoSupport;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ClearUndoStackAction extends AbstractCyAction {

    @Inject private UndoSupport undoSupport;
    
    @Inject
    public ClearUndoStackAction(CyApplicationManager applicationManager) {
        super("Clear Undo Stack", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        undoSupport.reset();
        System.out.println("Undo Stack Cleared");
    }

}
