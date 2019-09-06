package org.baderlab.st.internal.layout;

import java.util.Set;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.AbstractLayoutTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.undo.UndoSupport;

public class SimpleTestLayoutTask extends AbstractLayoutTask {

    public SimpleTestLayoutTask(CyNetworkView networkView, Set<View<CyNode>> nodesToLayOut, String layoutAttribute, UndoSupport undo) {
        super(SimpleTestLayout.NAME, /*false,*/ networkView, nodesToLayOut, layoutAttribute, undo);
    }

    @Override
    protected void doLayout(TaskMonitor taskMonitor) {
        System.out.println("SimpleTestLayoutTask.doLayout() layoutAttribute='" + layoutAttribute + "'");
    }

//    @Override
//    public void layoutPartition(LayoutPartition partition) {
//        System.out.println("SimpleTestLayoutTask.layoutPartition()");
//    }

}
