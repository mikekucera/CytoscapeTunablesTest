package org.baderlab.st.internal;

import org.cytoscape.session.events.SessionLoadedEvent;
import org.cytoscape.session.events.SessionLoadedListener;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

public class SessionCustomGraphicsListener implements SessionLoadedListener {

    @Inject private DialogTaskManager dialogTaskManager;
    @Inject private ChartFactoryManager chartManager;
    
    @Override
    public void handleEvent(SessionLoadedEvent e) {
        System.out.println("SessionCustomGraphicsListener.handleEvent(SessionLoadedEvent)");
        CyCustomGraphicsFactory<?> chartFactory = chartManager.getChartFactory("image");
        chartFactory.parseSerializableString("99,/blah/blah/blah");

    }

}
