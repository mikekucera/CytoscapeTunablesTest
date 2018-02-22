package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.util.swing.OpenBrowser;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class TestBadURLAction extends AbstractCyAction {

    @Inject private OpenBrowser openBrowser;
    
    @Inject
    public TestBadURLAction(CyApplicationManager applicationManager) {
        super("Open URL", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        openBrowser.openURL("http://yahoo.com");
    }

}
