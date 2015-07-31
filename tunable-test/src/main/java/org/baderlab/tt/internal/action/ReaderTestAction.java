package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;
import java.util.Properties;

import org.baderlab.tt.internal.tunables.LotsOfTunables;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.properties.TunablePropertySerializer;
import org.cytoscape.work.properties.TunablePropertySerializerFactory;

import com.google.inject.Inject;

/**
 * Creates a new menu item under Apps menu section.
 *
 */
@SuppressWarnings("serial")
public class ReaderTestAction extends AbstractCyAction {

    @Inject private TunablePropertySerializerFactory serializerFactory;

    @Inject
    public ReaderTestAction(CyApplicationManager applicationManager) {
        super("Property Reader Test", applicationManager, null, null);
    }

    public void actionPerformed(ActionEvent e) {
        LotsOfTunables tunables = new LotsOfTunables();
        TunablePropertySerializer tunablePropertySerailzer = serializerFactory.createSerializer();
        
        Properties props = new Properties();
        props.setProperty("line.startPoint.x", "5");
        props.setProperty("line.startPoint.y", "10");
        props.setProperty("line.endPoint.x", "15");
        props.setProperty("line.endPoint.y", "20");
        props.setProperty("what", "false");
        props.setProperty("str", "This is now a string that has a different value");
        props.setProperty("yesNoMaybe", "MAYBE");
        props.setProperty("numbers", "3");
        props.setProperty("intRange", "1");
        props.setProperty("doubleRange", "0.1");
        props.setProperty("names", "Max,Fred");
        
        
        tunablePropertySerailzer.setTunables(tunables, props);
        
        System.out.println(tunables);
    }
}
