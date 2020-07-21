package org.baderlab.st.internal;

import static org.cytoscape.application.swing.ActionEnableSupport.ENABLE_FOR_ALWAYS;
import static org.cytoscape.work.ServiceProperties.ENABLE_FOR;
import static org.cytoscape.work.ServiceProperties.PREFERRED_ACTION;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.Peaberry.service;
import static org.ops4j.peaberry.util.Filters.ldap;

import java.net.URL;
import java.util.Properties;

import org.baderlab.st.internal.actions.ApplyVisualStyleAction;
import org.baderlab.st.internal.actions.ClearUndoStackAction;
import org.baderlab.st.internal.actions.ColumnSetAllAction;
import org.baderlab.st.internal.actions.CountTaskAction;
import org.baderlab.st.internal.actions.CreateLocalAttributeAction;
import org.baderlab.st.internal.actions.CreateNetworkAndViewAction;
import org.baderlab.st.internal.actions.CreateNetworkTableAction;
import org.baderlab.st.internal.actions.CreateNetworkViewAction;
import org.baderlab.st.internal.actions.CreateNodeAction;
import org.baderlab.st.internal.actions.CreateSubnetworkAction;
import org.baderlab.st.internal.actions.CreateTablesWithViewSuidsAction;
import org.baderlab.st.internal.actions.EnvVarAction;
import org.baderlab.st.internal.actions.FindNodeNamedAAction;
import org.baderlab.st.internal.actions.FirePaloadEventsOnEDTAction;
import org.baderlab.st.internal.actions.FitContentTest;
import org.baderlab.st.internal.actions.NetworkViewUpdateAction;
import org.baderlab.st.internal.actions.PrintAllTablesAction;
import org.baderlab.st.internal.actions.PrintCurrentNodeTableAction;
import org.baderlab.st.internal.actions.PrintListenerCountAction;
import org.baderlab.st.internal.actions.PrintServiceTypeAction;
import org.baderlab.st.internal.actions.PrintVisualMappingTypesAction;
import org.baderlab.st.internal.actions.RowsSetFacadeTestAction;
import org.baderlab.st.internal.actions.RowsSetListenAction;
import org.baderlab.st.internal.actions.TestAnnotationGradientPaint;
import org.baderlab.st.internal.actions.TestAnnotationGradientPaint.Gradient;
import org.baderlab.st.internal.actions.TestBadURLAction;
import org.baderlab.st.internal.actions.TestMessageDialogAction;
import org.baderlab.st.internal.actions.TestRestoreEdgeAction;
import org.baderlab.st.internal.actions.ThrowExceptionAction;
import org.baderlab.st.internal.actions.TunableTestAction;
import org.baderlab.st.internal.actions.TunableTestSyncAction;
import org.baderlab.st.internal.actions.ViewChangedListenAction;
import org.baderlab.st.internal.actions.WriteToLogCommandAction;
import org.baderlab.st.internal.commands.TestColorCommandTaskFactory;
import org.baderlab.st.internal.commands.WriteToLogTaskFactory;
import org.baderlab.st.internal.functions.Factorial;
import org.baderlab.st.internal.functions.Fibonacci;
import org.baderlab.st.internal.functions.FunctionRegisterListener;
import org.baderlab.st.internal.layout.SimpleTestLayout;
import org.baderlab.st.internal.tasks.EdgeViewDoubleClickTaskFactory;
import org.baderlab.st.internal.tasks.NetworkViewDoubleClickTaskFactory;
import org.baderlab.st.internal.tasks.NodeViewDoubleClickTaskFactory;
import org.baderlab.st.internal.toolbar.SayHelloTaskFactory;
import org.baderlab.st.internal.tuneables.BoundedIntegerTaskFactory;
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
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.util.swing.OpenBrowser;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.presentation.annotations.AnnotationFactory;
import org.cytoscape.view.presentation.annotations.AnnotationManager;
import org.cytoscape.view.presentation.annotations.ShapeAnnotation;
import org.cytoscape.view.presentation.annotations.TextAnnotation;
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
        
        registerMenuAction(bc, injector.getInstance(TestMessageDialogAction.class));
        registerMenuAction(bc, injector.getInstance(PrintListenerCountAction.class));
        registerMenuAction(bc, injector.getInstance(PrintListenerCountAction.class).getResetAction());
        registerMenuAction(bc, injector.getInstance(PrintServiceTypeAction.class));
        registerMenuAction(bc, injector.getInstance(FitContentTest.class));
        registerMenuAction(bc, injector.getInstance(CreateNetworkAndViewAction.class));
        registerMenuAction(bc, injector.getInstance(CreateNetworkAndViewAction.class).setNumNetworks(100));
        registerMenuAction(bc, injector.getInstance(TestAnnotationGradientPaint.class).setGradient(Gradient.LINEAR));
        registerMenuAction(bc, injector.getInstance(TestAnnotationGradientPaint.class).setGradient(Gradient.RADIAL));
        registerMenuAction(bc, injector.getInstance(CreateNetworkViewAction.class));
        registerMenuAction(bc, injector.getInstance(RowsSetFacadeTestAction.class));
        registerMenuAction(bc, injector.getInstance(ColumnSetAllAction.class));
        registerMenuAction(bc, injector.getInstance(PrintCurrentNodeTableAction.class));
        registerMenuAction(bc, injector.getInstance(PrintAllTablesAction.class));
        registerMenuAction(bc, injector.getInstance(PrintAllTablesAction.class).setPrintStructure(true));
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
        
        registerCommand(bc, "shape-color", injector.getInstance(TestColorCommandTaskFactory.class));
        registerCommand(bc, "write-to-log", injector.getInstance(WriteToLogTaskFactory.class));
        
        registerToolBarButton(bc);
        registerActionInFileMenu(bc);
        registerDoubleClickTasks(bc, injector);
        
        registerService(bc, injector.getInstance(SimpleTestLayout.class), CyLayoutAlgorithm.class);
        
        {
            Properties props = new Properties();
            props.setProperty(PREFERRED_MENU, "Apps.Simple Test");
            props.setProperty(TITLE, "Test BoundedInteger");
            props.setProperty(ENABLE_FOR, ENABLE_FOR_ALWAYS);
            registerService(bc, new BoundedIntegerTaskFactory(), NetworkViewTaskFactory.class, props);
        }
    }
    
    
    private void registerDoubleClickTasks(BundleContext bc, Injector injector) {
        {
            NetworkViewTaskFactory taskFactory = injector.getInstance(NetworkViewDoubleClickTaskFactory.class);
            Properties props = new Properties();
            props.setProperty(PREFERRED_ACTION, "OPEN");
            props.setProperty(TITLE, "Test Network Double Click");
            registerService(bc, taskFactory, NetworkViewTaskFactory.class, props);
        }
        {
            EdgeViewTaskFactory taskFactory = injector.getInstance(EdgeViewDoubleClickTaskFactory.class);
            Properties props = new Properties();
            props.setProperty(PREFERRED_ACTION, "OPEN");
            props.setProperty(TITLE, "Test Edge Double Click");
            registerService(bc, taskFactory, EdgeViewTaskFactory.class, props);
        }
        {
            NodeViewTaskFactory taskFactory = injector.getInstance(NodeViewDoubleClickTaskFactory.class);
            Properties props = new Properties();
            props.setProperty(PREFERRED_ACTION, "OPEN");
            props.setProperty(TITLE, "Test Node Double Click");
            registerService(bc, taskFactory, NodeViewTaskFactory.class, props);
        }
        {
            NodeViewTaskFactory taskFactory = injector.getInstance(NodeViewDoubleClickTaskFactory.class);
            Properties props = new Properties();
            props.setProperty(PREFERRED_ACTION, "OPEN");
            props.setProperty(TITLE, "Test Node Double Click 2");
            registerService(bc, taskFactory, NodeViewTaskFactory.class, props);
        }
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
    
    private void registerActionInFileMenu(BundleContext bc) {
        TaskFactory taskFactory = new SayHelloTaskFactory();
        Properties props = new Properties();
        props.setProperty(ServiceProperties.PREFERRED_MENU, "File.Import");
        props.setProperty(ServiceProperties.TITLE, "Network from (app-specific-format)...");
        props.setProperty(ServiceProperties.MENU_GRAVITY, "30.0");
        props.setProperty(ServiceProperties.INSERT_SEPARATOR_BEFORE, "true");
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
            bindService(ApplyPreferredLayoutTaskFactory.class);
            bindService(CyNetworkNaming.class);
            
            TypeLiteral<SynchronousTaskManager<?>> synchronousManager = new TypeLiteral<SynchronousTaskManager<?>>(){};
            bind(synchronousManager).toProvider(service(synchronousManager).single());
            
            bindService(AnnotationManager.class);
            TypeLiteral<AnnotationFactory<ShapeAnnotation>> shapeFactory = new TypeLiteral<AnnotationFactory<ShapeAnnotation>>(){};
            bind(shapeFactory).toProvider(service(shapeFactory).filter(ldap("(type=ShapeAnnotation.class)")).single());
            TypeLiteral<AnnotationFactory<TextAnnotation>> textFactory = new TypeLiteral<AnnotationFactory<TextAnnotation>>(){};
            bind(textFactory).toProvider(service(textFactory).filter(ldap("(type=TextAnnotation.class)")).single());
        }
        
        private <T> void bindService(Class<T> serviceClass) {
            bind(serviceClass).toProvider(service(serviceClass).single());
        }
        
        private <T> void bindService(Class<T> serviceClass, String filter) {
            bind(serviceClass).toProvider(service(serviceClass).filter(ldap(filter)).single());
        }
    }
    
}
