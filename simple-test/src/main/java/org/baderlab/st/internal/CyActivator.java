package org.baderlab.st.internal;

import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.Peaberry.service;

import java.util.Properties;

import org.baderlab.st.internal.actions.ClearUndoStackAction;
import org.baderlab.st.internal.actions.CountTaskAction;
import org.baderlab.st.internal.actions.CreateLocalAttributeAction;
import org.baderlab.st.internal.actions.CreateSubnetworkAction;
import org.baderlab.st.internal.actions.CreateTablesWithViewSuidsAction;
import org.baderlab.st.internal.actions.FirePaloadEventsOnEDTAction;
import org.baderlab.st.internal.actions.PrintAllTablesAction;
import org.baderlab.st.internal.actions.PrintCurrentNodeTableAction;
import org.baderlab.st.internal.actions.PrintVisualMappingTypesAction;
import org.baderlab.st.internal.actions.ReportNodeEdgeRemovalAction;
import org.baderlab.st.internal.actions.ThrowExceptionAction;
import org.baderlab.st.internal.functions.Factorial;
import org.baderlab.st.internal.functions.Fibonacci;
import org.baderlab.st.internal.functions.FunctionRegisterListener;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.equations.Function;
import org.cytoscape.equations.event.EquationFunctionAddedListener;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingManager;
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
        
        registerMenuAction(bc, injector.getInstance(PrintCurrentNodeTableAction.class));
        registerMenuAction(bc, injector.getInstance(PrintAllTablesAction.class));
        registerMenuAction(bc, injector.getInstance(ReportNodeEdgeRemovalAction.class));
        registerMenuAction(bc, injector.getInstance(CreateLocalAttributeAction.class));
        registerMenuAction(bc, injector.getInstance(ThrowExceptionAction.class));
        registerMenuAction(bc, injector.getInstance(FirePaloadEventsOnEDTAction.class));
        registerMenuAction(bc, injector.getInstance(CreateTablesWithViewSuidsAction.class));
        registerMenuAction(bc, injector.getInstance(PrintVisualMappingTypesAction.class));
        registerMenuAction(bc, injector.getInstance(CreateSubnetworkAction.class));
        registerMenuAction(bc, injector.getInstance(ClearUndoStackAction.class));
        registerMenuAction(bc, injector.getInstance(CountTaskAction.class));
        
        registerService(bc, new Factorial(), Function.class);
        registerService(bc, new Fibonacci(), Function.class);
        registerService(bc, new FunctionRegisterListener(), EquationFunctionAddedListener.class);
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
            bindService(CyApplicationManager.class);
            bindService(CyNetworkManager.class);
            bindService(CySwingApplication.class);
            bindService(UndoSupport.class);
            bindService(CySessionManager.class);
            bindService(CyServiceRegistrar.class);            
            bindService(DialogTaskManager.class);
            bindService(PanelTaskManager.class);
            bindService(CyEventHelper.class);
            bindService(VisualMappingManager.class);
            
            bindService(CyNetworkFactory.class);
            bindService(CyNetworkViewManager.class);
            bindService(CyNetworkTableManager.class);
            bindService(CyTableManager.class);
            bindService(CyTableFactory.class);
            
            bindService(TunableSetter.class);
            bindService(TunablePropertySerializerFactory.class);
        }
        
        private <T> void bindService(Class<T> serviceClass) {
            bind(serviceClass).toProvider(service(serviceClass).single());
        }
    }
    
}
