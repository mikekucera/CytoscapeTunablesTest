package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.property.CyProperty;
import org.cytoscape.work.properties.TunablePropertySerializer;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * This is an example of restoring properties, modifying them 
 * with a dialog, then saving them again.
 * 
 * This example uses a CyProperties object that was initialized
 * as a service in the CyActivator.
 *
 */
@SuppressWarnings("serial")
public class PropertyTestAction extends AbstractCyAction {

    @Inject private TunablePropertySerializerFactory serializerFactory;
    @Inject private ActionFactory actionFactory;

    private final PropertyGetter propertyGetter;
    private final Supplier<?> tunableProvider;
    
    
    public interface PropertyGetter {
        Optional<CyProperty<Properties>> getCyProperty();
    }
    
    
    @Inject
    public PropertyTestAction(CyApplicationManager applicationManager, @Assisted String title, 
                              @Assisted Supplier<? extends Object> tunables, @Assisted PropertyGetter propertyGetter) {
        super(title, applicationManager, null, null);
        this.propertyGetter = propertyGetter;
        this.tunableProvider = tunables;
    }

    
    public void actionPerformed(ActionEvent e) {
        propertyGetter.getCyProperty().ifPresent(this::processLineTunable);
    }
    
    
    protected void processLineTunable(CyProperty<Properties> cyProperty) {
        TunablePropertySerializer tunablePropertySerailzer = serializerFactory.createSerializer();
        
        // initialize a fresh object with Tunable fields
        Object tunables = tunableProvider.get();
        print("New Object", tunables);
        
        // get the saved Properties
        Properties propsBefore = cyProperty.getProperties();
        print("Properties Before", propsBefore);
        
        if(!propsBefore.isEmpty()) {
            // use the Properties to restore the values of the Tunable fields
            tunablePropertySerailzer.setTunables(tunables, propsBefore);
            print("Restored Properties", tunables);
        }
        
        // show the dialog
        DialogTestAction action = actionFactory.createDialogTestAction("Restored Properties", tunables);
        action.setShowResultPopup(false);
        action.showDialog();
        print("Modified Properties", tunables);
        
        // convert the new Tunable values into a Properties object
        Properties propsAfter = tunablePropertySerailzer.toProperties(tunables);
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
