package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.FinishStatus;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskObserver;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class TunableTestSyncAction extends AbstractCyAction {

    @Inject private SynchronousTaskManager<?> syncTaskManager;
    
    public TunableTestSyncAction() {
        super("Test Tunable SyncTaskManager");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        TunableTestAction.TaskWithTunables task = new TunableTestAction.TaskWithTunables();
        
        Map<String,Object> tunableValues = new HashMap<>();
        tunableValues.put("feeling", "great");
        tunableValues.put("happy", true);
        tunableValues.put("throwException", true);
        
        syncTaskManager.setExecutionContext(tunableValues);
        
        syncTaskManager.execute(new TaskIterator(task), new TaskObserver() {
            
            @Override
            public void taskFinished(ObservableTask task) {
            }
            
            @Override
            public void allFinished(FinishStatus finishStatus) {
                Exception e = finishStatus.getException();
                System.out.println("allFinished:" + e.getMessage());
            }
        });
        
    }
    
    

}
