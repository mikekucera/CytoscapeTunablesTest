package org.baderlab.st.internal.tuneables;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.BoundedInteger;

public class BoundedIntegerTask extends AbstractTask {

    @Tunable(description="Test BoundedInteger")
    public BoundedInteger myBoundedInteger = new BoundedInteger(1, 5, 10, true, true);
    
    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {
        System.out.println("value = " + myBoundedInteger.getValue());
    }

}
