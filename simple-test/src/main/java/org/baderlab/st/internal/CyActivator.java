package org.baderlab.st.internal;

import static org.ops4j.peaberry.Peaberry.*;

import java.util.Properties;

import org.baderlab.st.internal.actions.CreateLocalAttributeAction;
import org.baderlab.st.internal.actions.FirePaloadEventsOnEDTAction;
import org.baderlab.st.internal.actions.TablePrintAction;
import org.baderlab.st.internal.actions.ThrowExceptionAction;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.work.TunableSetter;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;
import org.cytoscape.work.swing.DialogTaskManager;
import org.cytoscape.work.swing.PanelTaskManager;
import org.cytoscape.work.undo.UndoSupport;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class CyActivator extends AbstractCyActivator {

    @Override
    public void start(BundleContext bc) throws Exception {
        // Tired of manually passing around Cytoscape service references? Use Guice!
        Injector injector = Guice.createInjector(osgiModule(bc), new MainModule());
        
        registerMenuAction(bc, injector.getInstance(TablePrintAction.class));
        registerMenuAction(bc, injector.getInstance(CreateLocalAttributeAction.class));
        registerMenuAction(bc, injector.getInstance(ThrowExceptionAction.class));
        registerMenuAction(bc, injector.getInstance(FirePaloadEventsOnEDTAction.class));
    }
    
    
    private void registerMenuAction(BundleContext bc, AbstractCyAction action) {
        action.setPreferredMenu("Apps.Simple Test");
        registerAllServices(bc, action, new Properties());
    }
    
    
    /**
     * Guice module.
     */
    private class MainModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(CyApplicationManager.class).toProvider(service(CyApplicationManager.class).single());
            bind(CySwingApplication.class).toProvider(service(CySwingApplication.class).single());
            bind(UndoSupport.class).toProvider(service(UndoSupport.class).single());
            bind(CySessionManager.class).toProvider(service(CySessionManager.class).single());
            bind(CyServiceRegistrar.class).toProvider(service(CyServiceRegistrar.class).single());            
            bind(DialogTaskManager.class).toProvider(service(DialogTaskManager.class).single());
            bind(PanelTaskManager.class).toProvider(service(PanelTaskManager.class).single());
            bind(CyEventHelper.class).toProvider(service(CyEventHelper.class).single());
            
            bind(TunableSetter.class).toProvider(service(TunableSetter.class).single());
            bind(TunablePropertySerializerFactory.class).toProvider(service(TunablePropertySerializerFactory.class).single());
        }
    }
    
}
