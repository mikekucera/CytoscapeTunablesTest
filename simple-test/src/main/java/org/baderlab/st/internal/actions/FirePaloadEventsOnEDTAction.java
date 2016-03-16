package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.event.CyEventHelper;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("serial")
public class FirePaloadEventsOnEDTAction extends AbstractCyAction {

    @Inject private Provider<CyEventHelper> eventHelperProvider;
    
    public FirePaloadEventsOnEDTAction() {
        super("Start Thread to fire payload events on EVT");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Runnable runnable = ()-> {
            SwingUtilities.invokeLater(() -> {
                System.out.println("flushPayloadEvents " + Thread.currentThread().getName());
                CyEventHelper eventHelper = eventHelperProvider.get();
                eventHelper.flushPayloadEvents();
            });
        };

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(runnable, 1000, 250, TimeUnit.MILLISECONDS);
    }

}
