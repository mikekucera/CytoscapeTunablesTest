package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.hide.UnHideTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class HideUnBAction extends AbstractCyAction {
    
    @Inject private UnHideTaskFactory unhideTaskFactory;
    @Inject private CyApplicationManager applicationManager;
    @Inject private DialogTaskManager dialogTaskManager;
    
    
    @Inject
    public HideUnBAction(CyApplicationManager applicationManager) {
        super("Show nodes with \"B\"", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetworkView networkView = applicationManager.getCurrentNetworkView();
        CyNetwork network = networkView.getModel();
        
        List<CyNode> nodesToShow = network.getNodeList().stream()
                .filter(node -> network.getRow(node).get(CyNetwork.NAME, String.class).contains("B"))
                .collect(Collectors.toList());
        
        TaskIterator taskIterator = unhideTaskFactory.createTaskIterator(networkView, nodesToShow, null);
        dialogTaskManager.execute(taskIterator);
    }

}
