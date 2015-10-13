package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ThrowExceptionAction extends AbstractCyAction {

    @Inject private DialogTaskManager dialogTaskManager;
    
    
    @Inject
    public ThrowExceptionAction(CyApplicationManager applicationManager) {
        super("Throw Exception", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        dialogTaskManager.execute(new TaskIterator(new AbstractTask() {
            @Override
            public void run(TaskMonitor taskMonitor) throws Exception {
                throw new RuntimeException("KABOOM");
            }
        }));
    }

}
