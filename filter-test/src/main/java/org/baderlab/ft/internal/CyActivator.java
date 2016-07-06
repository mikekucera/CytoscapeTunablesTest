package org.baderlab.ft.internal;

import org.baderlab.ft.internal.transformers.FirstNeighboursTransformerFactory;
import org.baderlab.ft.internal.transformers.FirstNeighboursTransformerViewFactory;
import org.cytoscape.filter.model.ElementTransformerFactory;
import org.cytoscape.filter.view.TransformerViewFactory;
import org.cytoscape.service.util.AbstractCyActivator;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {
    
    @Override
    public void start(BundleContext bc) throws Exception {
//        // Tired of manually passing around Cytoscape service references? Use Guice!
//        Injector injector = Guice.createInjector(osgiModule(bc), new MainModule());

        registerService(bc, new FirstNeighboursTransformerFactory(), ElementTransformerFactory.class);
        registerService(bc, new FirstNeighboursTransformerViewFactory(), TransformerViewFactory.class);
    }
    
//    /**
//     * Guice module.
//     */
//    private class MainModule extends AbstractModule {
//        @Override
//        protected void configure() {
//            // Bind Cytoscape services for injection
//            bind(CyApplicationManager.class).toProvider(service(CyApplicationManager.class).single());
//            bind(CySwingApplication.class).toProvider(service(CySwingApplication.class).single());
//            bind(UndoSupport.class).toProvider(service(UndoSupport.class).single());
//            bind(CySessionManager.class).toProvider(service(CySessionManager.class).single());
//            bind(CyServiceRegistrar.class).toProvider(service(CyServiceRegistrar.class).single());            
//            bind(DialogTaskManager.class).toProvider(service(DialogTaskManager.class).single());
//            bind(PanelTaskManager.class).toProvider(service(PanelTaskManager.class).single());
//        }
//    }
    
}
