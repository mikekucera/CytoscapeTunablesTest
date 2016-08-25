package org.baderlab.st.internal.functions;

import org.cytoscape.equations.AbstractFunction;
import org.cytoscape.equations.ArgDescriptor;
import org.cytoscape.equations.ArgType;
import org.cytoscape.equations.FunctionError;

public class Factorial extends AbstractFunction {

    
    public Factorial() {
        super(new ArgDescriptor[] { new ArgDescriptor(ArgType.INT, "number", "any numeric value") });
    }
    
    
    @Override
    public String getName() {
        return "FAC";
    }

    @Override
    public String getFunctionSummary() {
        return "Returns N factorial";
    }


    @Override
    public Class<?> getReturnType() {
        return Long.class;
    }

    @Override
    public Object evaluateFunction(Object[] args) throws FunctionError {
        int n = ((Number) args[0]).intValue();
        
        long x = 1;
        if(x == 0) {
            return 1;
        }
        else {
            for(int i = 2; i <= n; i++) {
                x *= i;
            }
            return x;
        }
    }

  
    @Override
    public String toString() {
        return getName();
    }
}
