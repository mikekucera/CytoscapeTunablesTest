package org.baderlab.ft.internal;

import org.cytoscape.equations.Function;
import org.cytoscape.service.util.AbstractCyActivator;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext bc) {
		registerService(bc, new AddOneFunction(), Function.class);
	}

}
