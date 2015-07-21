package org.baderlab.tt.internal.layout;

import java.util.Set;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.AbstractLayoutAlgorithm;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.undo.UndoSupport;

import com.google.inject.Inject;

public class NothingLayoutAlgorithm extends AbstractLayoutAlgorithm {

    @Inject
    public NothingLayoutAlgorithm(UndoSupport undoSupport) {
        super("nothing-nothing", "Do Nothing", undoSupport);
    }

    @Override
    public TaskIterator createTaskIterator(CyNetworkView networkView, Object layoutContext, Set<View<CyNode>> nodesToLayOut, String layoutAttribute) {
        NothingLayoutContext context = (NothingLayoutContext) layoutContext;
        return new TaskIterator(new NothingLayoutAlgorithmTask(getName(), context, networkView, nodesToLayOut, layoutAttribute, undoSupport));
    }

    @Override
    public Object createLayoutContext() {
        return new NothingLayoutContext();
    }
}
