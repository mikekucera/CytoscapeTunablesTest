package org.baderlab.ft.internal.transformers;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.cytoscape.filter.model.Transformer;
import org.cytoscape.filter.view.TransformerViewFactory;

public class FirstNeighboursTransformerViewFactory implements TransformerViewFactory {

    public static final Color UNSELECTED_BACKGROUND_COLOR = UIManager.getColor("Table.background");
    
    @Override
    public String getId() {
        return FirstNeighboursTransformer.ID;
    }

    @Override
    public JComponent createView(Transformer<?, ?> transformer) {
        FirstNeighboursTransformer model = (FirstNeighboursTransformer) transformer;
        View view = new View(model);
        return view;
    }

    
    @SuppressWarnings("serial")
    private class View extends JPanel {
        // Not actually using the model because it is stateless
        private final FirstNeighboursTransformer model;
        
        public View(FirstNeighboursTransformer model) {
            this.model = model;
            setBackground(UNSELECTED_BACKGROUND_COLOR);
            
            JLabel label = new JLabel("Take nodes and add first neighbours.");
            label.putClientProperty("JComponent.sizeVariant", "small");
            
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
        }
    }
}
