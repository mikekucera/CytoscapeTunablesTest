package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;
import java.util.Properties;

import org.baderlab.tt.internal.tunables.LotsOfTunables;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.service.util.ServiceGetter;
import org.cytoscape.work.properties.TunablePropertySerializer;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;

import com.google.inject.Inject;

/**
 * Creates a new menu item under Apps menu section.
 *
 */
@SuppressWarnings("serial")
public class WithServicesAction extends AbstractCyAction {

    private CyServiceRegistrar serviceRegistrar;

    @Inject
    public WithServicesAction(CyServiceRegistrar serviceRegistrar) {
        super("Test withService Action", serviceRegistrar.getService(CyApplicationManager.class), null, null);
        this.serviceRegistrar = serviceRegistrar;
    }

    public void actionPerformed(ActionEvent e) {
        
        
        int x = 0;
        serviceRegistrar.withService(TunablePropertySerializerFactory.class, 
            serializerFactory -> {
                LotsOfTunables tunables = new LotsOfTunables();
                TunablePropertySerializer tunablePropertySerailzer = serializerFactory.createSerializer();
                Properties props = tunablePropertySerailzer.toProperties(tunables);
                System.out.println("Properties:");
                System.out.println(props);
            }
        );
        
        serviceRegistrar.withServices(CyApplicationManager.class, CyNetworkManager.class, 
            (applicationManager, networkManager) -> {
                System.out.println("This is the application manager: " + applicationManager);
                System.out.println("This is the networkManager: " + networkManager);
            }
        );
        
        
        try (ServiceGetter getter = serviceRegistrar.getServiceGetter()) {
            CyApplicationManager applicationManager = getter.get(CyApplicationManager.class);
            CyNetworkManager networkManager = getter.get(CyNetworkManager.class);
            
            System.out.println("This is the application manager: " + applicationManager);
            System.out.println("This is the networkManager: " + networkManager);
            
            x = 1;
        }
       
    }
}
