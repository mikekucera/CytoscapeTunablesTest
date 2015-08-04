package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.Properties;

import org.baderlab.tt.internal.tunables.Line;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.property.CyProperty;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.work.properties.TunablePropertySerializer;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;

import com.google.inject.Inject;

/**
 * This is an example of restoring properties, modifying them 
 * with a dialog, then saving them again.
 * 
 * This example uses a CyProperties object that was initialized
 * as a service in the CyActivator.
 *
 */
@SuppressWarnings("serial")
public class SessionPropertyTestAction extends AbstractCyAction {

    public static final String CY_PROPERTY_NAME = "test-line";
    
    @Inject private TunablePropertySerializerFactory serializerFactory;
    @Inject private CySessionManager sessionManager;
    @Inject private DialogTestActionFactory dialogFactory;

    @Inject
    public SessionPropertyTestAction(CyApplicationManager applicationManager) {
        this("Session Property Test", applicationManager);
    }
    
    public SessionPropertyTestAction(String title, CyApplicationManager applicationManager) {
        super(title, applicationManager, null, null);
    }

    
    public void actionPerformed(ActionEvent e) {
        getCyProperty().ifPresent(this::processLineTunable);
    }
    
    
    @SuppressWarnings("unchecked")
    protected Optional<CyProperty<Properties>> getCyProperty() {
        return sessionManager.getCurrentSession().getProperties()
                .stream()
                .filter(p -> CY_PROPERTY_NAME.equals(p.getName()))
                .findAny()
                .map(p -> (CyProperty<Properties>) p);
    }
    
    
    protected void processLineTunable(CyProperty<Properties> cyProperty) {
        TunablePropertySerializer tunablePropertySerailzer = serializerFactory.createSerializer();
        
        // initialize a fresh object with Tunable fields
        Line line = new Line();
        print("New Line Object", line);
        
        // get the saved Properties
        Properties propsBefore = cyProperty.getProperties();
        print("Properties Before", propsBefore);
        
        // use the Properties to restore the values of the Tunable fields
        tunablePropertySerailzer.setTunables(line, propsBefore);
        print("Restored Line Properties", line);
        
        // show the dialog
        DialogTestAction action = dialogFactory.create("Restored Line Properties", line);
        action.setShowResultPopup(false);
        action.showDialog();
        print("Modified Line Properties", line);
        
        // convert the new Tunable values into a Properties object
        Properties propsAfter = tunablePropertySerailzer.toProperties(line);
        print("Properties After", propsAfter);
        
        // save the properties
        cyProperty.getProperties().clear();
        cyProperty.getProperties().putAll(propsAfter);
    }
    
    
    protected static void print(String message, Object object) {
        System.out.println(message);
        System.out.println(object);
        System.out.println();
    }
}
