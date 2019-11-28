package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.NetworkViewRenderer;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.swing.DialogTaskManager;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class CreateNetworkViewAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    @Inject private CySwingApplication swingApplication;
    @Inject private CyNetworkViewManager networkViewManager;
    @Inject private DialogTaskManager taskManager;
    @Inject private VisualMappingManager visualMappingManager;
    @Inject private ApplyPreferredLayoutTaskFactory layoutTaskFactory;
    
    
    @Inject
    public CreateNetworkViewAction(CyApplicationManager applicationManager) {
        super("Create Network View", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = applicationManager.getCurrentNetwork();
        if(network == null)
            return;
        VisualStyle style = visualMappingManager.getCurrentVisualStyle();
        
        NetworkViewRenderer renderer = getRenderer();
        CyNetworkViewFactory networkViewFactory = renderer.getNetworkViewFactory();
        CyNetworkView networkView = networkViewFactory.createNetworkView(network);
        
        networkViewManager.addNetworkView(networkView);
        if(style != null) {
            visualMappingManager.setVisualStyle(style, networkView);
            style.apply(networkView);
        }
        
        TaskIterator layoutTasks = layoutTaskFactory.createTaskIterator(Arrays.asList(networkView));
        taskManager.execute(layoutTasks);
    }
    
    
    
    private NetworkViewRenderer getRenderer() {
        Set<NetworkViewRenderer> rendererSet = applicationManager.getNetworkViewRendererSet();
        if(rendererSet.size() == 1) {
            return rendererSet.iterator().next();
        } else {
            return promptUserForRenderer(rendererSet);
        }
    }
    
    private NetworkViewRenderer promptUserForRenderer(Set<NetworkViewRenderer> rendererSet) {
        NetworkViewRenderer[] renderers = new NetworkViewRenderer[rendererSet.size()];
        rendererSet.toArray(renderers);
        JFrame parent = swingApplication.getJFrame();
        
        NetworkViewRenderer renderer = (NetworkViewRenderer)JOptionPane.showInputDialog(
                parent, 
                "Choose a Renderer", 
                "Choose a Renderer", 
                JOptionPane.QUESTION_MESSAGE, 
                null,
                renderers, 
                renderers[0]);
        
        return renderer;
    }

}
