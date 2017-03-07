package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class TunableTestAction extends AbstractCyAction {

    @Inject private DialogTaskManager dialogTaskManager;
    
    public TunableTestAction() {
        super("Test Tunable Dialog");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        TaskWithTunables task = new TaskWithTunables();
        dialogTaskManager.execute(new TaskIterator(task));
    }
    
    
    public static class TaskWithTunables extends AbstractTask {
        
        @Tunable(description="How do you feel?")
        public String feeling;
        
        @Tunable(description="Are you happy?")
        public boolean happy;

        @Tunable(description="Would you like to throw an exception?")
        public boolean throwException = false;
        
        
        @Override
        public void run(TaskMonitor tm) throws Exception {
            tm.setTitle("TaskWithTunables");
            tm.setStatusMessage("This is a task with tunables!");
            
            System.out.println("Feeling: " + feeling);
            System.out.println("Happy?: " + happy);
            System.out.println("Throw Exception?: " + throwException);
            
            if(throwException) {
                throw new RuntimeException("KABOOM!");            
            }
        }
        
    }

}
