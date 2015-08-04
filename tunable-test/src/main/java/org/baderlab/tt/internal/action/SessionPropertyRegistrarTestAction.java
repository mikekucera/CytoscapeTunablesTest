package org.baderlab.tt.internal.action;

import java.util.Optional;
import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.CyProperty.SavePolicy;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.service.util.CyServiceRegistrar;

import com.google.inject.Inject;


/**
 * Tests dynamically registering a new CyProperties object using the service registrar.
 */
@SuppressWarnings("serial")
public class SessionPropertyRegistrarTestAction extends SessionPropertyTestAction {

    public static final String CY_PROPERTY_NAME = "test-line-registrar2";
    
    @Inject private CyServiceRegistrar serviceRegistrar;
    

    @Inject
    public SessionPropertyRegistrarTestAction(CyApplicationManager applicationManager) {
        super("Session Property Registrar Test", applicationManager);
    }

    
    @Override
    protected Optional<CyProperty<Properties>> getCyProperty() {
        Optional<CyProperty<Properties>> cyProperty = super.getCyProperty();
        
        if(cyProperty.isPresent()) {
            return cyProperty;
        } 
        else {
            Properties props = new Properties();
            props.put("startPoint.x", "4");
            props.put("startPoint.y", "5");
            props.put("endPoint.x", "6");
            props.put("endPoint.y", "7");
            
            CyProperty<Properties> simpleCyProps = new SimpleCyProperty<>(CY_PROPERTY_NAME, props, Properties.class, SavePolicy.SESSION_FILE_AND_CONFIG_DIR);
            
            Properties serviceProps = new Properties();
            serviceProps.setProperty("cyPropertyName", CY_PROPERTY_NAME + ".props");
            serviceRegistrar.registerAllServices(simpleCyProps, serviceProps);
            
            return Optional.of(simpleCyProps);
        }
    }
    
}
