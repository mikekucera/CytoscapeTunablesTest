package org.baderlab.tt.internal;

import static org.ops4j.peaberry.Peaberry.*;

import java.util.Properties;

import org.baderlab.tt.internal.action.DialogTestAction;
import org.baderlab.tt.internal.action.DialogTestActionFactory;
import org.baderlab.tt.internal.action.SetterTestAction;
import org.baderlab.tt.internal.action.YesNoMaybeHandler;
import org.baderlab.tt.internal.tunables.Line;
import org.baderlab.tt.internal.tunables.Vote;
import org.baderlab.tt.internal.tunables.YesNoMaybe;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.work.TunableSetter;
import org.cytoscape.work.swing.DialogTaskManager;
import org.cytoscape.work.swing.GUITunableHandlerFactory;
import org.cytoscape.work.swing.PanelTaskManager;
import org.cytoscape.work.swing.SimpleGUITunableHandlerFactory;
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
        
        DialogTestAction lineAction = dialogTestActionFactory.create("Line Dialog Test", new Line());
        registerMenuAction(bc, lineAction);
        
        DialogTestAction voteAction = dialogTestActionFactory.create("Vote Dialog Test", new Vote());
        registerMenuAction(bc, voteAction);
        
        SetterTestAction setterTestAction = injector.getInstance(SetterTestAction.class);
        registerMenuAction(bc, setterTestAction);
        
        
        SimpleGUITunableHandlerFactory<YesNoMaybeHandler> yesNoMaybeHandlerFactory = 
                new SimpleGUITunableHandlerFactory<YesNoMaybeHandler>(YesNoMaybeHandler.class, YesNoMaybe.class);
        registerService(bc, yesNoMaybeHandlerFactory, GUITunableHandlerFactory.class, new Properties());
    }

    
    private void registerMenuAction(BundleContext bc, AbstractCyAction action) {
        action.setPreferredMenu("Apps.Tunable Test");
        registerAllServices(bc, action, new Properties());
    }
    
    
    private class MainModule extends AbstractModule {

        // Bind Cytoscape services for injection
        @Override
        protected void configure() {
            bind(CyApplicationManager.class).toProvider(service(CyApplicationManager.class).single());
            bind(CySwingApplication.class).toProvider(service(CySwingApplication.class).single());
            bind(DialogTaskManager.class).toProvider(service(DialogTaskManager.class).single());
            bind(PanelTaskManager.class).toProvider(service(PanelTaskManager.class).single());
            bind(TunableSetter.class).toProvider(service(TunableSetter.class).single());
            
            install(new FactoryModuleBuilder().build(DialogTestActionFactory.class));
        }
    }
    
}
