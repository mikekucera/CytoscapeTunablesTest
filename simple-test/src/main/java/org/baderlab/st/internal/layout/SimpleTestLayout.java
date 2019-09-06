package org.baderlab.st.internal.layout;

import java.util.Collections;
import java.util.Set;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.layout.AbstractLayoutAlgorithm;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.undo.UndoSupport;

import com.google.inject.Inject;

public class SimpleTestLayout extends AbstractLayoutAlgorithm {

    public static final String ID = "simpleTest.simpleLayout";
    public static final String NAME = "Simple Test Layout";
    
    @Inject
    public SimpleTestLayout(UndoSupport undoSupport) {
        super(ID, NAME, undoSupport);
    }

    @Override
    public TaskIterator createTaskIterator(CyNetworkView netView, Object context, Set<View<CyNode>> nodesToLayOut, String attribute) {
        return new TaskIterator(new SimpleTestLayoutTask(netView, nodesToLayOut, attribute, undoSupport));
    }
    
    @Override
    public Set<Class<?>> getSupportedEdgeAttributeTypes() {
        return Collections.singleton(String.class);
    }
    
    @Override
    public Set<Class<?>> getSupportedNodeAttributeTypes() {
        return Collections.singleton(String.class);
    }
    
    @Override
    public boolean getSupportsSelectedOnly() {
        return true;
    }

}
