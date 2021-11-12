package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.session.CySessionManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class SessionCreateAction extends AbstractCyAction {

    @Inject private CySessionManager sessionManager;
    
    public SessionCreateAction() {
        super("CySessionManager.getCurrentSession()");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("getting current session");
        var session = sessionManager.getCurrentSession();
        System.out.println(session);
        System.out.println("done");
    }
    

}
