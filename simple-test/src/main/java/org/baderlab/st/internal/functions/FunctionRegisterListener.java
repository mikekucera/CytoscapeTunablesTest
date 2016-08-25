package org.baderlab.st.internal.functions;

import java.util.Collection;

import org.cytoscape.equations.Function;
import org.cytoscape.equations.event.EquationFunctionAddedEvent;
import org.cytoscape.equations.event.EquationFunctionAddedListener;
import org.cytoscape.equations.event.EquationFunctionRemovedEvent;
import org.cytoscape.equations.event.EquationFunctionRemovedListener;

public class FunctionRegisterListener implements EquationFunctionAddedListener, EquationFunctionRemovedListener {

    @Override
    public void handleEvent(EquationFunctionAddedEvent e) {
        Collection<Function> functions = e.getPayloadCollection();
        System.out.println("EquationFunctionAddedEvent: " + functions);

    }

    @Override
    public void handleEvent(EquationFunctionRemovedEvent e) {
        Collection<Function> functions = e.getPayloadCollection();
        System.out.println("EquationFunctionRemovedEvent: " + functions);
    }

}
