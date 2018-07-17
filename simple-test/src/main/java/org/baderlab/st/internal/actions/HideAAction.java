package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.hide.HideTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class HideAAction extends AbstractCyAction {

    @Inject private HideTaskFactory hideTaskFactory;
    @Inject private CyApplicationManager applicationManager;
    @Inject private DialogTaskManager dialogTaskManager;
    
    
    @Inject
    public HideAAction(CyApplicationManager applicationManager) {
        super("Hide nodes with \"A\"", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetworkView networkView = applicationManager.getCurrentNetworkView();
        CyNetwork network = networkView.getModel();
        
        List<CyNode> nodesToHide = network.getNodeList().stream()
                .filter(node -> network.getRow(node).get(CyNetwork.NAME, String.class).contains("A"))
                .collect(Collectors.toList());
        
        TaskIterator taskIterator = hideTaskFactory.createTaskIterator(networkView, nodesToHide, null);
        dialogTaskManager.execute(taskIterator);
    }

}
