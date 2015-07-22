package org.baderlab.tt.internal;

import static org.cytoscape.work.ServiceProperties.*;
import static org.ops4j.peaberry.Peaberry.*;

import java.util.Properties;

import org.baderlab.tt.internal.action.DialogTestActionFactory;
import org.baderlab.tt.internal.action.ReaderTestAction;
import org.baderlab.tt.internal.action.SessionPropertyTestAction;
import org.baderlab.tt.internal.action.SetterTestAction;
import org.baderlab.tt.internal.action.WriterTestAction;
import org.baderlab.tt.internal.action.YesNoMaybeHandler;
import org.baderlab.tt.internal.layout.NothingLayoutAlgorithm;
import org.baderlab.tt.internal.tunables.Line;
import org.baderlab.tt.internal.tunables.Vote;
import org.baderlab.tt.internal.tunables.YesNoMaybe;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.session.CySessionManager;
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

    @Override
    public void start(BundleContext bc) throws Exception {
        Injector injector = Guice.createInjector(osgiModule(bc), new MainModule());

        DialogTestActionFactory dialogTestActionFactory = injector.getInstance(DialogTestActionFactory.class);
        
        registerMenuAction(bc, dialogTestActionFactory.create("Line Dialog Test", new Line()));
        registerMenuAction(bc, dialogTestActionFactory.create("Vote Dialog Test", new Vote()));
        registerMenuAction(bc, injector.getInstance(SetterTestAction.class));
        registerMenuAction(bc, injector.getInstance(WriterTestAction.class));
        registerMenuAction(bc, injector.getInstance(ReaderTestAction.class));
        registerMenuAction(bc, injector.getInstance(SessionPropertyTestAction.class));
        
        SimpleGUITunableHandlerFactory<YesNoMaybeHandler> yesNoMaybeHandlerFactory = 
                new SimpleGUITunableHandlerFactory<YesNoMaybeHandler>(YesNoMaybeHandler.class, YesNoMaybe.class);
        registerService(bc, yesNoMaybeHandlerFactory, GUITunableHandlerFactory.class, new Properties());
        
        NothingLayoutAlgorithm nothingLayout = injector.getInstance(NothingLayoutAlgorithm.class);
        registerLayoutAlgorithm(bc, nothingLayout);
        
        
        Properties defaultProps = new Properties();
        defaultProps.put("startPoint.x", "9");
        defaultProps.put("startPoint.y", "10");
        defaultProps.put("endPoint.x", "11");
        defaultProps.put("endPoint.y", "12");
        CyProperty<Properties> sessionProps = new SimpleCyProperty<>("test-line", defaultProps, Properties.class, CyProperty.SavePolicy.SESSION_FILE_AND_CONFIG_DIR);
        Properties serviceProps = new Properties();
        serviceProps.setProperty("cyPropertyName","test-line.props");
        registerAllServices(bc, sessionProps, new Properties());
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
    
    
    
    private class MainModule extends AbstractModule {
        @Override
        protected void configure() {
            // Bind Cytoscape services for injection
            bind(CyApplicationManager.class).toProvider(service(CyApplicationManager.class).single());
            bind(CySwingApplication.class).toProvider(service(CySwingApplication.class).single());
            bind(UndoSupport.class).toProvider(service(UndoSupport.class).single());
            bind(CySessionManager.class).toProvider(service(CySessionManager.class).single());
            
            bind(DialogTaskManager.class).toProvider(service(DialogTaskManager.class).single());
            bind(PanelTaskManager.class).toProvider(service(PanelTaskManager.class).single());
            
            bind(TunableSetter.class).toProvider(service(TunableSetter.class).single());
            bind(TunablePropertySerializerFactory.class).toProvider(service(TunablePropertySerializerFactory.class).single());
            
            install(new FactoryModuleBuilder().build(DialogTestActionFactory.class));
        }
    }
    
}
