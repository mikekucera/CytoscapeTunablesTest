package org.baderlab.ft.internal.transformers;

import org.cytoscape.filter.model.ElementTransformer;
import org.cytoscape.filter.model.ElementTransformerFactory;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;

public class FirstNeighboursTransformerFactory implements ElementTransformerFactory<CyNetwork, CyIdentifiable> {

    @Override
    public String getId() {
        return FirstNeighboursTransformer.ID;
    }

    @Override
    public ElementTransformer<CyNetwork, CyIdentifiable> createElementTransformer() {
        return new FirstNeighboursTransformer();
    }

}
