package org.baderlab.tt.internal.task;

import org.cytoscape.model.CyNode;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.TunableValidator;
import org.cytoscape.work.util.ListSingleSelection;

public class ShiftNodeViewTask implements Task, TunableValidator {

    @Tunable
    public ListSingleSelection<ShiftDirection> directionSelection = new ListSingleSelection<>(ShiftDirection.values());
    
    private final View<CyNode> nodeView;
    private final CyNetworkView networkView;
    
    
    public ShiftNodeViewTask(View<CyNode> nodeView, CyNetworkView networkView) {
        this.nodeView = nodeView;
        this.networkView = networkView;
    }

    
    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {
        ShiftDirection direction = directionSelection.getSelectedValue();
        double currX = nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        double currY = nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
        
        switch(direction) {
        case UP:
            nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, currY + 10);
            break;
        case DOWN:
            nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, currY - 10);
            break;
        case LEFT:
            nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, currX - 10);
            break;
        case RIGHT:
            nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, currX + 10);
            break;
        }
        
        networkView.updateView();
    }

    @Override
    public void cancel() {
    }


    @Override
    public ValidationState getValidationState(Appendable errMsg) {
        return ValidationState.INVALID;
    }

}
