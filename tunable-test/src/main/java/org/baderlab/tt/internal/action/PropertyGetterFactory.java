package org.baderlab.tt.internal.action;

import java.util.Optional;
import java.util.Properties;

import org.baderlab.tt.internal.action.PropertyTestAction.PropertyGetter;
import org.cytoscape.property.AbstractConfigDirPropsReader;
import org.cytoscape.property.CyProperty;
import org.cytoscape.property.CyProperty.SavePolicy;
import org.cytoscape.property.SimpleCyProperty;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySessionManager;

import com.google.inject.Inject;

/**
 * Factory that provides different ways of getting a CyProperties object.
 */
public class PropertyGetterFactory {

    public static final String CY_PROPERTY_NAME_SESSION = "test-line";
    public static final String CY_PROPERTY_NAME_SESSION_REGISTRAR = "test-line-registrar2";
    public static final String CY_PROPERTY_NAME_CONFIGDIR_REGISTRAR = "test-line-configdir";
    
    @Inject private CySessionManager sessionManager;
    @Inject private CyServiceRegistrar serviceRegistrar;
    
    
    /**
     * Read CyProperty from session, CyProperty must be registered in CyActivator.
     */
    public PropertyGetter sessionGetter() {
        return new PropertyGetter() {
            @SuppressWarnings("unchecked")
            @Override
            public Optional<CyProperty<Properties>> getCyProperty() {
                return sessionManager.getCurrentSession().getProperties()
                          .stream()
                          .filter(p -> CY_PROPERTY_NAME_SESSION.equals(p.getName()))
                          .findAny()
                          .map(p -> (CyProperty<Properties>) p);
            }
        };
    }
    
    
    /**
     * Read CyProperty from session, but do it dynamically using the service registrar.
     */
    public PropertyGetter registrarSessionGetter() {
        return new PropertyGetter() {
            @Override
            public Optional<CyProperty<Properties>> getCyProperty() {
                Optional<CyProperty<Properties>> cyProperty = sessionGetter().getCyProperty();
                
                if(cyProperty.isPresent()) {
                    return cyProperty;
                } 
                else {
                    // some default values, not really necessary
                    Properties props = new Properties();
                    props.put("startPoint.x", "4");
                    props.put("startPoint.y", "5");
                    props.put("endPoint.x", "6");
                    props.put("endPoint.y", "7");
                    
                    CyProperty<Properties> simpleCyProps = new SimpleCyProperty<>(CY_PROPERTY_NAME_SESSION_REGISTRAR, props, Properties.class, SavePolicy.SESSION_FILE_AND_CONFIG_DIR);
                    
                    Properties serviceProps = new Properties();
                    serviceProps.setProperty("cyPropertyName", CY_PROPERTY_NAME_SESSION_REGISTRAR + ".props");
                    serviceRegistrar.registerAllServices(simpleCyProps, serviceProps);
                    
                    return Optional.of(simpleCyProps);
                }
            }
        };
    }
    
    
    private PropsReader propsReader = null;
    
    /**
     * Read CyProperty from config dir, but do it dynamically using service registrar.
     */
    public PropertyGetter configDirRegistrarGetter() {
        return new PropertyGetter() {
            @Override
            public Optional<CyProperty<Properties>> getCyProperty() {
                if(propsReader == null) {
                    PropsReader propsReader = new PropsReader(CY_PROPERTY_NAME_CONFIGDIR_REGISTRAR);
                    Properties props = new Properties();
                    props.setProperty("cyPropertyName", propsReader.getName());
                    serviceRegistrar.registerAllServices(propsReader, props);
                }
                return Optional.of(propsReader);
            }
        };
    }
    
    
    /**
     * This subclass may seem pointless but because of OSGi the concrete class must be defined in this bundle.
     */
    private static class PropsReader extends AbstractConfigDirPropsReader {
        public PropsReader(String name) {
            super(name, name + ".props", SavePolicy.CONFIG_DIR);
        }
    }
}







