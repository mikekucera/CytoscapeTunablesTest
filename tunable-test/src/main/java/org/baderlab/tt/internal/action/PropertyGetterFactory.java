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

    
    @Inject private CySessionManager sessionManager;
    @Inject private CyServiceRegistrar serviceRegistrar;
    
    
    /**
     * Read CyProperty from session, CyProperty must be registered in CyActivator.
     */
    public PropertyGetter session(final String cyPropName) {
        return new PropertyGetter() {
            @SuppressWarnings("unchecked")
            @Override
            public Optional<CyProperty<Properties>> getCyProperty() {
                return sessionManager.getCurrentSession().getProperties()
                          .stream()
                          .filter(p -> cyPropName.equals(p.getName()))
                          .findAny()
                          .map(p -> (CyProperty<Properties>) p);
            }
        };
    }
    
    
    /**
     * Read CyProperty from session, but do it dynamically using the service registrar.
     */
    public PropertyGetter registrarSession(final String cyPropName) {
        return new PropertyGetter() {
            @Override
            public Optional<CyProperty<Properties>> getCyProperty() {
                Optional<CyProperty<Properties>> cyProperty = session(cyPropName).getCyProperty();
                
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
                    
                    CyProperty<Properties> simpleCyProps = new SimpleCyProperty<>(cyPropName, props, Properties.class, SavePolicy.SESSION_FILE_AND_CONFIG_DIR);
                    
                    Properties serviceProps = new Properties();
                    serviceProps.setProperty("cyPropertyName", cyPropName + ".props");
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
    public PropertyGetter configDirRegistrar(final String cyPropName) {
        return new PropertyGetter() {
            @Override
            public Optional<CyProperty<Properties>> getCyProperty() {
                if(propsReader == null) {
                    PropsReader propsReader = new PropsReader(cyPropName);
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







