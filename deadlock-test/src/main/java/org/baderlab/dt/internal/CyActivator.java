package org.baderlab.dt.internal;


import static org.cytoscape.work.ServiceProperties.*;

import java.util.Properties;

import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.swing.DialogTaskManager;
import org.osgi.framework.BundleContext;


public class CyActivator extends AbstractCyActivator {

    @Override
    public void start(BundleContext bc) throws Exception {
        DialogTaskManager taskManager = getService(bc, DialogTaskManager.class);
        TaskAFactory taskAFactory = new TaskAFactory(taskManager);
        
        Properties props = new Properties();
        props.setProperty(PREFERRED_MENU,"Apps.Deadlock Test");
        props.setProperty(TITLE,"Deadlock!");
        registerService(bc, taskAFactory, TaskFactory.class, props);
    }
}
