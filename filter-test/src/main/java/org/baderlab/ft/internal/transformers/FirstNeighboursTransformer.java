package org.baderlab.ft.internal.transformers;

import java.util.Collections;
import java.util.List;

import org.cytoscape.filter.model.AbstractTransformer;
import org.cytoscape.filter.model.ElementTransformer;
import org.cytoscape.filter.model.TransformerSink;
import org.cytoscape.filter.model.ValidatableTransformer;
import org.cytoscape.filter.model.ValidationWarning;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;

public class FirstNeighboursTransformer extends AbstractTransformer<CyNetwork, CyIdentifiable> 
                                        implements ElementTransformer<CyNetwork,CyIdentifiable>,
                                                   ValidatableTransformer<CyNetwork, CyIdentifiable> {

    
    public static final String ID = FirstNeighboursTransformer.class.getName();
    
    
    @Override
    public void apply(CyNetwork network, CyIdentifiable element, TransformerSink<CyIdentifiable> sink) {
        if(element instanceof CyNode) {
            CyNode currentNode = (CyNode) element;
            sink.collect(currentNode);
            
            Iterable<CyEdge> adjacentEdges  = network.getAdjacentEdgeIterable(currentNode, CyEdge.Type.ANY);
            for(CyEdge edge : adjacentEdges) {
                CyNode neighbour = otherNode(currentNode, edge);
                sink.collect(neighbour);
            }
        }
    }
    
    private static CyNode otherNode(CyNode node, CyEdge edge) {
        if(node != edge.getSource() && node != edge.getTarget())
            throw new RuntimeException("hey, this node is not the source or the target!");
        
        CyNode other = edge.getSource();
        if(other == node)
            other = edge.getTarget();
        return other;
    }
    
    @Override
    public List<ValidationWarning> validate(CyNetwork context) {
        int numEdges = context.getDefaultEdgeTable().getRowCount();
        if(numEdges <= 0) {
            return ValidationWarning.warn("The current network does not have any edges");
        }
        return Collections.emptyList();
    }
    
    
    @Override
    public String getName() {
        return "First Neighbours Transformer";
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Class<CyNetwork> getContextType() {
        return CyNetwork.class;
    }

    @Override
    public Class<CyIdentifiable> getElementType() {
        return CyIdentifiable.class;
    }

}
