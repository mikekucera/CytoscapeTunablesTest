package org.baderlab.st.internal.commands;

import org.cytoscape.application.CyUserLog;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.TaskMonitor.Level;
import org.cytoscape.work.Tunable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteToLogTask1 extends AbstractTask {

    private final static Logger logger = LoggerFactory.getLogger(WriteToLogTask1.class);
    private final static Logger userLog = LoggerFactory.getLogger(CyUserLog.NAME);
    
    @Tunable
    public String echo1;
    
    @Override
    public void run(TaskMonitor taskMonitor) {
        System.out.println("WriteToLogTask1.run()");
        taskMonitor.showMessage(Level.ERROR, "WriteToLogTask1: from taskMonitor " + echo1);
//        userLog.error("WriteToLogTask: from CyUserLog");
//        logger.error("WriteToLogTask: from logger");
    }

}
