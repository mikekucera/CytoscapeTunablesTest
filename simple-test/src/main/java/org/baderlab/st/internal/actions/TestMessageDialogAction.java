package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.util.swing.MessageDialogs;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class TestMessageDialogAction extends AbstractCyAction {

    @Inject private CySwingApplication swingApplication;
    
    private int state = 0;
    
    public TestMessageDialogAction() {
        super("Test Message Dialog");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame parent = swingApplication.getJFrame();
        
        if(state % 3 == 0) {
            MessageDialogs.showMessageDialog(parent, "Test", "This is the message dialog");
        } else if(state % 3 == 1) {
            boolean r = MessageDialogs.showOkCancelDialog(parent, "Test", "This is the ok/cancel dialog");
            System.out.println("result: " + r);
        } else if(state % 3 == 2) {
            boolean r = MessageDialogs.showYesNoDialog(parent, "Test", "This is the yes/no dialog");
            System.out.println("result: " + r);
        }
        
        state++;
    }

}
