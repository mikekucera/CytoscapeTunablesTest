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
public class WriterTestAction extends AbstractCyAction {

    @Inject private TunablePropertySerializerFactory serializerFactory;

    @Inject
    public WriterTestAction(CyApplicationManager applicationManager) {
        super("Property Writer Test", applicationManager, null, null);
    }

    public void actionPerformed(ActionEvent e) {
        LotsOfTunables tunables = new LotsOfTunables();
        TunablePropertySerializer tunablePropertySerailzer = serializerFactory.createSerializer();
        Properties props = tunablePropertySerailzer.toProperties(tunables);
        System.out.println("Properties:");
        System.out.println(props);
    }
}
