package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;
import java.util.Properties;
import java.util.Set;

import org.baderlab.tt.internal.tunables.Line;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.property.CyProperty;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.work.properties.TunablePropertySerializer;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;

import com.google.inject.Inject;

/**
 * Creates a new menu item under Apps menu section.
 *
 */
@SuppressWarnings("serial")
public class SessionPropertyTestAction extends AbstractCyAction {

    @Inject private TunablePropertySerializerFactory serializerFactory;
    @Inject private CySessionManager sessionManager;
    @Inject private DialogTestActionFactory dialogFactory;

    @Inject
    public SessionPropertyTestAction(CyApplicationManager applicationManager) {
        super("Session Property Test", applicationManager, null, null);
    }

    public void actionPerformed(ActionEvent e) {
        Set<CyProperty<?>> propertySet = sessionManager.getCurrentSession().getProperties();
        for(CyProperty<?> cyProperty : propertySet) {
            if("test-line".equals(cyProperty.getName())) {
                processLineTunable((CyProperty<Properties>)cyProperty);
                break;
            }
        }
    }
    
    
    private void processLineTunable(CyProperty<Properties> cyProperty) {
        TunablePropertySerializer tunablePropertySerailzer = serializerFactory.createSerializer();
        
        Line line = new Line();
        print("New Line Object", line);
        
        Properties propsBefore = cyProperty.getProperties();
        print("Properties Before", propsBefore);
        
        tunablePropertySerailzer.setTunables(line, propsBefore);
        print("Restored Line Properties", line);
        
        DialogTestAction action = dialogFactory.create("Restored Line Properties", line);
        action.setShowResultPopup(false);
        action.showDialog();
        print("Modified Line Properties", line);
        
        Properties propsAfter = tunablePropertySerailzer.toProperties(line);
        print("Properties After", propsAfter);
        
        cyProperty.getProperties().clear();
        cyProperty.getProperties().putAll(propsAfter);
    }
    
    
    private static void print(String message, Object object) {
        System.out.println(message);
        System.out.println(object);
        System.out.println();
    }
}
