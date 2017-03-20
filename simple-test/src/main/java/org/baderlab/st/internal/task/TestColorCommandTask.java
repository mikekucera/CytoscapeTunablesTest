package org.baderlab.st.internal.task;

import java.awt.Color;

import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.presentation.property.values.NodeShape;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.ListSingleSelection;

public class TestColorCommandTask extends AbstractTask {

    @Tunable (description = "Choose the shape")
    public ListSingleSelection<NodeShape> shape = new ListSingleSelection<NodeShape>(NodeShapeVisualProperty.DIAMOND, 
            NodeShapeVisualProperty.ELLIPSE, NodeShapeVisualProperty.HEXAGON, NodeShapeVisualProperty.OCTAGON,
            NodeShapeVisualProperty.PARALLELOGRAM, NodeShapeVisualProperty.RECTANGLE,         NodeShapeVisualProperty.ROUND_RECTANGLE, NodeShapeVisualProperty.TRIANGLE);
    
    
    @Tunable(description = "Choose the first color")    
    public ListSingleSelection<String> col1 = new ListSingleSelection<String>("MAGENTA", "BLUE", "CYAN", "ORANGE");
   
    
    
    @Override
    public void run(TaskMonitor taskMonitor) throws Exception {
        Color color = getColor(col1.getSelectedValue());
        
        System.out.println("Shape: " + shape.getSelectedValue());
        System.out.println("Color: " + col1.getSelectedValue());
    }
    
    
    private static Color getColor(String arg) {
        switch(arg) {
            default:
            case "MAGENTA": return Color.MAGENTA;
            case "BLUE": return Color.BLUE;
        }
    }

}
