package org.baderlab.tt.internal.action;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import org.baderlab.tt.internal.tunables.Line;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.work.TunableSetter;

import com.google.inject.Inject;

/**
 * Creates a new menu item under Apps menu section.
 *
 */
@SuppressWarnings("serial")
public class SetterTestAction extends AbstractCyAction {

    @Inject private TunableSetter tunableSetter;

    @Inject
    public SetterTestAction(CyApplicationManager applicationManager) {
        super("Setter Test", applicationManager, null, null);
    }

    public void actionPerformed(ActionEvent e) {
        Line line = new Line();

        Map<String, Object> tunableValues = new HashMap<>();
        tunableValues.put("x", 10);
        tunableValues.put("y", 30);
        tunableValues.put("endPoint.x", 50); // doesn't work
        
        tunableSetter.applyTunables(line, tunableValues);
        
        // Fails: sets both startPoint.x and endPoint.x to 10
        System.out.println(line);
    }
}
