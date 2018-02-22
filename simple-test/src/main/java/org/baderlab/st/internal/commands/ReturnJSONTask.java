package org.baderlab.st.internal.commands;

import java.util.Arrays;
import java.util.List;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.ContainsTunables;
import org.cytoscape.work.ObservableTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.json.JSONResult;

public class ReturnJSONTask extends AbstractTask implements ObservableTask {


    @ContainsTunables
    public TaskParams t1 = new TaskParams();
    
    @ContainsTunables
    public TaskParams t2 = new TaskParams();
    
    @Override
    public void run(TaskMonitor tm) {
        System.out.println(t1.x);
        System.out.println(t1.y);
        System.out.println(t2.x);
        System.out.println(t2.y);
    }
    
    @Override
    public List<Class<?>> getResultClasses() {
        return Arrays.asList(JSONResult.class);
    }
    
    @Override
    public <R> R getResults(Class<? extends R> type) {
        if(JSONResult.class.equals(type)) {
            type.cast((JSONResult)() -> "[]");
        }
        return null;
    }

}
