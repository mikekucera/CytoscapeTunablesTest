package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.baderlab.st.internal.commands.PasswordTunableTask;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class PasswordTunableAction extends AbstractCyAction {
    
    @Inject private DialogTaskManager dialogTaskManager;
    
    public PasswordTunableAction() {
        super("Password Tunable Test");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        TaskIterator tasks = new TaskIterator();
        tasks.append(new PasswordTunableTask());
        dialogTaskManager.execute(tasks);
    }

}
