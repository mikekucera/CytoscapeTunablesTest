package org.baderlab.tt.internal;

import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.TITLE;
import static org.ops4j.peaberry.Peaberry.osgiModule;
import static org.ops4j.peaberry.Peaberry.service;

import java.util.Properties;

import org.baderlab.tt.internal.action.ActionFactory;
import org.baderlab.tt.internal.action.ObjectReuseAction;
import org.baderlab.tt.internal.action.PropertyGetterFactory;
import org.baderlab.tt.internal.action.ReaderTestAction;
import org.baderlab.tt.internal.action.SetterTestAction;
import org.baderlab.tt.internal.action.WriterTestAction;
import org.baderlab.tt.internal.layout.NothingLayoutAlgorithm;
import org.baderlab.tt.internal.task.ShiftNodeViewTaskFactory;
import org.baderlab.tt.internal.tunables.BadTunables;
import org.baderlab.tt.internal.tunables.Line;
import org.baderlab.tt.internal.tunables.Vote;
import org.baderlab.tt.internal.tunables.YesNoMaybe;
import org.baderlab.tt.internal.tunables.YesNoMaybeHandler;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.CyProperty.SavePolicy;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.work.TunableSetter;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;
import org.cytoscape.work.swing.DialogTaskManager;
import org.cytoscape.work.swing.GUITunableHandlerFactory;
import org.cytoscape.work.swing.PanelTaskManager;
import org.cytoscape.work.swing.SimpleGUITunableHandlerFactory;
import org.cytoscape.work.undo.UndoSupport;
import org.osgi.framework.BundleContext;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class CyActivator extends AbstractCyActivator {

    public static final String CY_PROPERTY_SESSION = "test-line";
    public static final String CY_PROPERTY_SESSION_REGISTRAR = "test-line-registrar2";
    public static final String CY_PROPERTY_CONFIGDIR_REGISTRAR = "test-line-configdir";
    public static final String CY_PROPERTY_CONFIGDIR_BAD = "test-badtunables-configdir";
    
    
    @Override
    public void start(BundleContext bc) throws Exception {
        // Tired of manually passing around Cytoscape service references? Use Guice!
        Injector injector = Guice.createInjector(osgiModule(bc), new MainModule());
        ActionFactory actionFactory = injector.getInstance(ActionFactory.class);
        PropertyGetterFactory getterFactory = injector.getInstance(PropertyGetterFactory.class);
        
        
        registerMenuAction(bc, actionFactory.createDialogTestAction("Line Dialog Test", new Line()));
        registerMenuAction(bc, actionFactory.createDialogTestAction("Vote Dialog Test", new Vote()));
        registerMenuAction(bc, injector.getInstance(SetterTestAction.class));
        registerMenuAction(bc, injector.getInstance(WriterTestAction.class));
        registerMenuAction(bc, injector.getInstance(ReaderTestAction.class));
        registerMenuAction(bc, actionFactory.createPropertyTestAction("Session Property Action", ()->new Line(), getterFactory.session(CY_PROPERTY_SESSION))); // requires CyProperty be registered below
        registerMenuAction(bc, actionFactory.createPropertyTestAction("Session Property Registrar Action", ()->new Line(), getterFactory.registrarSession(CY_PROPERTY_SESSION_REGISTRAR)));
        registerMenuAction(bc, actionFactory.createPropertyTestAction("Config Dir Property Registrar Action", ()->new Line(), getterFactory.configDirRegistrar(CY_PROPERTY_CONFIGDIR_REGISTRAR)));
        registerMenuAction(bc, actionFactory.createPropertyTestAction("Bad Tunable Test", ()->new BadTunables(), getterFactory.registrarSession(CY_PROPERTY_CONFIGDIR_BAD)));
        registerMenuAction(bc, injector.getInstance(ObjectReuseAction.class));
        
        
        // A custom GUI tunable handler that provides a set of radio buttons for the YesNoMaybe enum.
        SimpleGUITunableHandlerFactory<YesNoMaybeHandler> yesNoMaybeHandlerFactory = 
                new SimpleGUITunableHandlerFactory<YesNoMaybeHandler>(YesNoMaybeHandler.class, YesNoMaybe.class);
        registerService(bc, yesNoMaybeHandlerFactory, GUITunableHandlerFactory.class, new Properties());
        
        // A layout algorithm that doesn't do anything, just for testing that custom tunable handlers
        // will work for Tunable fields in a layout context object.
        NothingLayoutAlgorithm nothingLayout = injector.getInstance(NothingLayoutAlgorithm.class);
        registerLayoutAlgorithm(bc, nothingLayout);
        
        
        // Here is the CyProperties used by the "Session Property Action"
        Properties defaultProps = new Properties();
        defaultProps.put("startPoint.x", "9");
        defaultProps.put("startPoint.y", "10");
        defaultProps.put("endPoint.x", "11");
        defaultProps.put("endPoint.y", "12");
        CyProperty<Properties> sessionProps = new SimpleCyProperty<>(CY_PROPERTY_SESSION, defaultProps, Properties.class, SavePolicy.SESSION_FILE_AND_CONFIG_DIR);
        Properties serviceProps = new Properties();
        serviceProps.setProperty("cyPropertyName", CY_PROPERTY_SESSION + ".props");
        registerAllServices(bc, sessionProps, serviceProps);
        
        
        // This is an example of how Apps must currently use Tasks and TaskFactories to get
        // context sensitive (ie right click) settings dialogs to work. 
        // Hopefully a new Preferences API will make this easier.
        ShiftNodeViewTaskFactory shiftNodeViewTaskFactory = new ShiftNodeViewTaskFactory();
        Properties shiftProps = new Properties();
        shiftProps.setProperty("title", "Shift Node");
        registerService(bc, shiftNodeViewTaskFactory, NodeViewTaskFactory.class, shiftProps);
    }

    
    
    private void registerMenuAction(BundleContext bc, AbstractCyAction action) {
        action.setPreferredMenu("Apps.Tunable Test");
        registerAllServices(bc, action, new Properties());
    }
    
    
    private void registerLayoutAlgorithm(BundleContext bc, CyLayoutAlgorithm algorithm) {
        Properties props = new Properties();
        props.setProperty("preferredTaskManager", "menu");
        props.setProperty(TITLE, algorithm.toString());
        props.setProperty(MENU_GRAVITY, "40.1");
        registerService(bc, algorithm, CyLayoutAlgorithm.class, props);
    }
    
    
    /**
     * Guice module.
     */
    private class MainModule extends AbstractModule {
        @Override
        protected void configure() {
            // Bind Cytoscape services for injection
            bind(CyApplicationManager.class).toProvider(service(CyApplicationManager.class).single());
            bind(CySwingApplication.class).toProvider(service(CySwingApplication.class).single());
            bind(UndoSupport.class).toProvider(service(UndoSupport.class).single());
            bind(CySessionManager.class).toProvider(service(CySessionManager.class).single());
            bind(CyServiceRegistrar.class).toProvider(service(CyServiceRegistrar.class).single());            
            bind(DialogTaskManager.class).toProvider(service(DialogTaskManager.class).single());
            bind(PanelTaskManager.class).toProvider(service(PanelTaskManager.class).single());
            
            bind(TunableSetter.class).toProvider(service(TunableSetter.class).single());
            bind(TunablePropertySerializerFactory.class).toProvider(service(TunablePropertySerializerFactory.class).single());
            
            install(new FactoryModuleBuilder().build(ActionFactory.class));
        }
    }
    
}
