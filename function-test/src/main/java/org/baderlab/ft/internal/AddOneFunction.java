package org.baderlab.ft.internal;

import org.cytoscape.equations.AbstractFunction;
import org.cytoscape.equations.ArgDescriptor;
import org.cytoscape.equations.ArgType;
import org.cytoscape.equations.FunctionError;
import org.cytoscape.equations.FunctionUtil;

public class AddOneFunction extends AbstractFunction {

	public AddOneFunction() {
		super(new ArgDescriptor[] {
			new ArgDescriptor(ArgType.INT	, "arg1", "value to increment")
		});
	}

	@Override
	public Object evaluateFunction(Object[] args) throws FunctionError {
		long value = FunctionUtil.getArgAsLong(args[0]);
		return value + 1;
	}

	@Override
	public String getFunctionSummary() {
		return "takes an integer and adds one to it";
	}

	@Override
	public String getName() {
		return "ADDONE";
	}

	@Override
	public Class getReturnType() {
		return Long.class;
	}

}
