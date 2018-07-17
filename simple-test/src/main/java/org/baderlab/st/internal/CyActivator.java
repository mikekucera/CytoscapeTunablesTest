package org.baderlab.st.internal;

import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.Peaberry.service;

import java.net.URL;
import java.util.Properties;

import org.baderlab.st.internal.actions.ApplyVisualStyleAction;
import org.baderlab.st.internal.actions.ClearUndoStackAction;
import org.baderlab.st.internal.actions.ColumnSetAllAction;
import org.baderlab.st.internal.actions.CountTaskAction;
import org.baderlab.st.internal.actions.CreateLocalAttributeAction;
import org.baderlab.st.internal.actions.CreateNetworkTableAction;
import org.baderlab.st.internal.actions.CreateNodeAction;
import org.baderlab.st.internal.actions.CreateSubnetworkAction;
import org.baderlab.st.internal.actions.CreateTablesWithViewSuidsAction;
import org.baderlab.st.internal.actions.EnvVarAction;
import org.baderlab.st.internal.actions.FindNodeNamedAAction;
import org.baderlab.st.internal.actions.FirePaloadEventsOnEDTAction;
import org.baderlab.st.internal.actions.HideAAction;
import org.baderlab.st.internal.actions.HideUnBAction;
import org.baderlab.st.internal.actions.NetworkViewUpdateAction;
import org.baderlab.st.internal.actions.PrintAllTablesAction;
import org.baderlab.st.internal.actions.PrintCurrentNodeTableAction;
import org.baderlab.st.internal.actions.PrintVisualMappingTypesAction;
import org.baderlab.st.internal.actions.ReportNodeEdgeRemovalAction;
import org.baderlab.st.internal.actions.RowsSetFacadeTestAction;
import org.baderlab.st.internal.actions.RowsSetListenAction;
import org.baderlab.st.internal.actions.TestBadURLAction;
import org.baderlab.st.internal.actions.TestRestoreEdgeAction;
import org.baderlab.st.internal.actions.ThrowExceptionAction;
import org.baderlab.st.internal.actions.TunableTestAction;
import org.baderlab.st.internal.actions.TunableTestSyncAction;
import org.baderlab.st.internal.actions.ViewChangedListenAction;
import org.baderlab.st.internal.actions.WriteToLogCommandAction;
import org.baderlab.st.internal.commands.ReturnJSONTaskFactory;
import org.baderlab.st.internal.commands.TestColorCommandTaskFactory;
import org.baderlab.st.internal.commands.WriteToLogTaskFactory;
import org.baderlab.st.internal.functions.Factorial;
import org.baderlab.st.internal.functions.Fibonacci;
import org.baderlab.st.internal.functions.FunctionRegisterListener;
import org.baderlab.st.internal.toolbar.SayHelloTaskFactory;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.search.NetworkSearchTaskFactory;
import org.cytoscape.command.CommandExecutorTaskFactory;
import org.cytoscape.equations.Function;
import org.cytoscape.equations.event.EquationFunctionAddedListener;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNetworkTableManager;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.task.hide.HideTaskFactory;
import org.cytoscape.task.hide.UnHideTaskFactory;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.ServiceProperties;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TunableSetter;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;
import org.cytoscape.work.swing.DialogTaskManager;
import org.cytoscape.work.swing.PanelTaskManager;
import org.cytoscape.work.undo.UndoSupport;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

public class CyActivator extends AbstractCyActivator {

    @Override
    public void start(BundleContext bc) {
        // Tired of manually passing around Cytoscape service references? Use Guice!
        Injector injector = Guice.createInjector(osgiModule(bc), new MainModule());
        
        registerMenuAction(bc, injector.getInstance(HideAAction.class), "Hide Task API");
        registerMenuAction(bc, injector.getInstance(HideUnBAction.class), "Hide Task API");
        
        registerMenuAction(bc, injector.getInstance(RowsSetFacadeTestAction.class));
        registerMenuAction(bc, injector.getInstance(ColumnSetAllAction.class));
        registerMenuAction(bc, injector.getInstance(PrintCurrentNodeTableAction.class));
        registerMenuAction(bc, injector.getInstance(PrintAllTablesAction.class));
        registerMenuAction(bc, injector.getInstance(PrintAllTablesAction.class).setPrintStructure(true));
        registerMenuAction(bc, injector.getInstance(ReportNodeEdgeRemovalAction.class));
        registerMenuAction(bc, injector.getInstance(CreateLocalAttributeAction.class));
        registerMenuAction(bc, injector.getInstance(ThrowExceptionAction.class));
        registerMenuAction(bc, injector.getInstance(FirePaloadEventsOnEDTAction.class));
        registerMenuAction(bc, injector.getInstance(CreateTablesWithViewSuidsAction.class));
        registerMenuAction(bc, injector.getInstance(PrintVisualMappingTypesAction.class));
        registerMenuAction(bc, injector.getInstance(CreateSubnetworkAction.class));
        registerMenuAction(bc, injector.getInstance(ClearUndoStackAction.class));
        registerMenuAction(bc, injector.getInstance(CountTaskAction.class));
        registerMenuAction(bc, injector.getInstance(TunableTestAction.class));
        registerMenuAction(bc, injector.getInstance(TunableTestSyncAction.class));
        registerMenuAction(bc, injector.getInstance(EnvVarAction.class));
        registerMenuAction(bc, injector.getInstance(TestRestoreEdgeAction.class));
        registerMenuAction(bc, injector.getInstance(FindNodeNamedAAction.class));
        registerMenuAction(bc, injector.getInstance(CreateNetworkTableAction.class));
        registerMenuAction(bc, injector.getInstance(TestBadURLAction.class));
        registerMenuAction(bc, injector.getInstance(WriteToLogCommandAction.class));
        registerMenuAction(bc, injector.getInstance(RowsSetListenAction.class));
        registerMenuAction(bc, injector.getInstance(ViewChangedListenAction.class));
        registerMenuAction(bc, injector.getInstance(NetworkViewUpdateAction.class));
        registerMenuAction(bc, injector.getInstance(CreateNodeAction.class));
        registerMenuAction(bc, injector.getInstance(ApplyVisualStyleAction.class));
        
        registerService(bc, new Factorial(), Function.class);
        registerService(bc, new Fibonacci(), Function.class);
        registerService(bc, new FunctionRegisterListener(), EquationFunctionAddedListener.class);
        
        registerService(bc, new SimpleNetworkSearchBar(), NetworkSearchTaskFactory.class);
        registerService(bc, new SimpleRowsSetListener(), RowsSetListener.class);
        
        registerCommand(bc, "shape-color", injector.getInstance(TestColorCommandTaskFactory.class));
        registerCommand(bc, "write-to-log", injector.getInstance(WriteToLogTaskFactory.class));
        
        registerToolBarButton(bc);
        
        {
            Properties props = new Properties();
            props.put(ServiceProperties.COMMAND, "test-contains-tunables");
            props.put(ServiceProperties.COMMAND_NAMESPACE, "simpletest");
            props.put(ServiceProperties.COMMAND_SUPPORTS_JSON, "true");
            registerService(bc, new ReturnJSONTaskFactory(), TaskFactory.class, props);
        }
//        {
//            Properties props = new Properties();
//            props.put(CyColumnPresentation.NAMESPACE, "simple");
//            registerService(bc, new SimpleColumnPresentaiton("thumbs_up_16.png"), CyColumnPresentation.class, props);
//        }
//        {
//            Properties props = new Properties();
//            props.put(CyColumnPresentation.NAMESPACE, "simple128");
//            registerService(bc, new SimpleColumnPresentaiton("thumbs_up_128.png"), CyColumnPresentation.class, props);
//        }
    }
    
    private void registerToolBarButton(BundleContext bc) {
        TaskFactory taskFactory = new SayHelloTaskFactory();
        Properties props = new Properties();
        props.setProperty(ServiceProperties.IN_TOOL_BAR, "true");
        props.setProperty(ServiceProperties.TOOL_BAR_GRAVITY, "0.0f");
        props.setProperty(ServiceProperties.TOOLTIP, "Say Hello");
        URL pngFile = getClass().getClassLoader().getResource("thumbs_up_16.png");
        props.setProperty(ServiceProperties.LARGE_ICON_URL, pngFile.toString());
        props.setProperty(ServiceProperties.SMALL_ICON_URL, pngFile.toString());
        props.setProperty(ServiceProperties.INSERT_TOOLBAR_SEPARATOR_BEFORE, "true");
        props.setProperty(ServiceProperties.INSERT_TOOLBAR_SEPARATOR_AFTER, "true");
        registerService(bc, taskFactory, TaskFactory.class, props);
    }

    private void registerMenuAction(BundleContext bc, AbstractCyAction action) {
        action.setPreferredMenu("Apps.Simple Test");
        registerAllServices(bc, action, new Properties());
    }
    
    private void registerMenuAction(BundleContext bc, AbstractCyAction action, String subMenu) {
        action.setPreferredMenu("Apps.Simple Test."+subMenu);
        registerAllServices(bc, action, new Properties());
    }
    
    
    private void registerCommand(BundleContext context, String name, TaskFactory factory) {
        Properties props = new Properties();
        props.put(ServiceProperties.COMMAND, name);
        props.put(ServiceProperties.COMMAND_NAMESPACE, "simpletest");
        props.put(ServiceProperties.COMMAND_LONG_DESCRIPTION, "this is **bold** this is *italic*");
        registerService(context, factory, TaskFactory.class, props);
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
            bindService(OpenBrowser.class);
            
            bindService(HideTaskFactory.class);
            bindService(UnHideTaskFactory.class);
            
            bindService(CyNetworkFactory.class);
            bindService(CyNetworkViewManager.class);
            bindService(CyNetworkTableManager.class);
            bindService(CyTableManager.class);
            bindService(CyTableFactory.class);
            bindService(CyRootNetworkManager.class);
            bindService(CyNetworkViewFactory.class);
            bindService(CyLayoutAlgorithmManager.class);
            bindService(CommandExecutorTaskFactory.class);
            
            bindService(TunableSetter.class);
            bindService(TunablePropertySerializerFactory.class);
            
            TypeLiteral<SynchronousTaskManager<?>> synchronousManager = new TypeLiteral<SynchronousTaskManager<?>>(){};
            bind(synchronousManager).toProvider(service(synchronousManager).single());
        }
        
        private <T> void bindService(Class<T> serviceClass) {
            bind(serviceClass).toProvider(service(serviceClass).single());
        }
    }
    
}
