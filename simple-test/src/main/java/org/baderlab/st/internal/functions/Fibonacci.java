package org.baderlab.st.internal.functions;

import org.cytoscape.equations.AbstractFunction;
import org.cytoscape.equations.ArgDescriptor;
import org.cytoscape.equations.ArgType;
import org.cytoscape.equations.FunctionError;

public class Fibonacci extends AbstractFunction {

    
    public Fibonacci() {
        super(new ArgDescriptor[] { new ArgDescriptor(ArgType.INT, "number", "any numeric value") });
    }
    
    
    @Override
    public String getName() {
        return "FIB";
    }

    @Override
    public String getFunctionSummary() {
        return "Returns the Nth fibbonacci number";
    }


    @Override
    public Class<?> getReturnType() {
        return Long.class;
    }

    @Override
    public Object evaluateFunction(Object[] args) throws FunctionError {
        int n = ((Number) args[0]).intValue();
        
        long x = 0, y = 1, z = 1;
        
        for (int i = 0; i < n; i++) {
            x = y;
            y = z;
            z = x + y;
        }
        
        return x;
    }

  
    @Override
    public String toString() {
        return getName();
    }
}
