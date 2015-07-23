package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;
import java.util.Properties;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.CyProperty.SavePolicy;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySessionManager;

import com.google.inject.Inject;

/**
 * Creates a new menu item under Apps menu section.
 *
 */
@SuppressWarnings("serial")
public class SessionPropertyRegistrarTestAction extends SessionPropertyTestAction {

    public static final String CY_PROPERTY_NAME = "test-line-registrar";
    
    @Inject private CySessionManager sessionManager;
    @Inject private CyServiceRegistrar serviceRegistrar;
    

    @Inject
    public SessionPropertyRegistrarTestAction(CyApplicationManager applicationManager) {
        super("Session Property Registrar Test", applicationManager);
    }

    @Override
    protected String getCyPropertyName() {
        return CY_PROPERTY_NAME;
    }
    
    public void actionPerformed(ActionEvent e) {
        setUpCyProperty();
        super.actionPerformed(e);
    }
    
    
    private void setUpCyProperty() {
        boolean exists = sessionManager.getCurrentSession().getProperties().stream().anyMatch(p -> CY_PROPERTY_NAME.equals(p.getName()));
        
        if(!exists) {
            Properties props = new Properties();
            props.put("startPoint.x", "4");
            props.put("startPoint.y", "5");
            props.put("endPoint.x", "6");
            props.put("endPoint.y", "7");
            
            CyProperty<Properties> sessionProps = new SimpleCyProperty<>(CY_PROPERTY_NAME, props, Properties.class, SavePolicy.SESSION_FILE_AND_CONFIG_DIR);
            Properties serviceProps = new Properties();
            serviceProps.setProperty("cyPropertyName", CY_PROPERTY_NAME + ".props");
            
            serviceRegistrar.registerAllServices(sessionProps, serviceProps);
        }
        
    }
    
}
