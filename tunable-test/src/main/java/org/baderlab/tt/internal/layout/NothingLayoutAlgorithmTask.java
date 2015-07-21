package org.baderlab.tt.internal.layout;

import java.util.Set;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.AbstractLayoutTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.undo.UndoSupport;

public class NothingLayoutAlgorithmTask extends AbstractLayoutTask {

    private final NothingLayoutContext context;
    
    public NothingLayoutAlgorithmTask(String displayName, NothingLayoutContext context, CyNetworkView networkView, 
                                      Set<View<CyNode>> nodesToLayOut, String layoutAttribute, UndoSupport undo) {
        super(displayName, networkView, nodesToLayOut, layoutAttribute, undo);
        this.context = context;
    }

    @Override
    protected void doLayout(TaskMonitor taskMonitor) {
        System.out.println(context.yesNoMaybe);
        // Do nothing
    }

}
