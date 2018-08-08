package org.baderlab.st.internal;

import java.util.HashMap;
import java.util.Map;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;

import com.google.inject.Singleton;

@Singleton
public class ChartFactoryManager {

	private Map<String, CyCustomGraphicsFactory<?>> factories = new HashMap<>();

	public void addFactory(CyCustomGraphicsFactory<?> factory, Map<String,String> serviceProps) {
		factories.put(factory.getPrefix(), factory);
	}

	public void removeFactory(CyCustomGraphicsFactory<?> factory, Map<String,String> serviceProps) {
		factories.remove(factory.getPrefix());
	}

	public CyCustomGraphicsFactory<?> getChartFactory(final String id) {
		return factories.get(id);
	}
}
