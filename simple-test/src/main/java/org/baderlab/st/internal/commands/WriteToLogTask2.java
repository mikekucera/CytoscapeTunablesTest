package org.baderlab.st.internal.commands;

import org.cytoscape.application.CyUserLog;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.TaskMonitor.Level;
import org.cytoscape.work.Tunable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteToLogTask2 extends AbstractTask {

    private final static Logger logger = LoggerFactory.getLogger(WriteToLogTask2.class);
    private final static Logger userLog = LoggerFactory.getLogger(CyUserLog.NAME);
    
    @Tunable(longDescription=
            "This is a header\n" +
            "================\n" +
            "This is **bold**, this is *italic*\n"
            )
    public String echo2;
    
    @Override
    public void run(TaskMonitor taskMonitor) {
        System.out.println("WriteToLogTask2.run()");
        taskMonitor.showMessage(Level.ERROR, "WriteToLogTask2: from taskMonitor " + echo2);
//        userLog.error("WriteToLogTask: from CyUserLog");
//        logger.error("WriteToLogTask: from logger");
    }

}
