package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.command.CommandExecutorTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class WriteToLogCommandAction extends AbstractCyAction {

    @Inject private CommandExecutorTaskFactory commandTaskFactory;
    @Inject private DialogTaskManager dialogTaskManager;
    
    @Inject
    public WriteToLogCommandAction(CyApplicationManager applicationManager) {
        super("Run 'simpletest write-to-log'", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String c = "simpletest write-to-log echo1=\"blah1\" echo2=\"blah2\"";
        
        TaskIterator taskIterator = commandTaskFactory.createTaskIterator(null, c);
        dialogTaskManager.execute(taskIterator);
    }
    
}
