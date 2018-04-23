package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.baderlab.st.internal.commands.CountTask;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class CountTaskAction extends AbstractCyAction {
    
    @Inject private DialogTaskManager dialogTaskManager;
    
    public CountTaskAction() {
        super("Count Task Test");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        TaskIterator tasks = new TaskIterator();
        
        tasks.append(new CountTask(10));
        tasks.append(new CountTask(20));
        
        dialogTaskManager.execute(tasks);
    }

}
