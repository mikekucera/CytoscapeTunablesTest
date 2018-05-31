package org.baderlab.st.internal;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.JComponent;

import org.cytoscape.application.swing.search.NetworkSearchTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskObserver;

public class SimpleNetworkSearchBar implements NetworkSearchTaskFactory {

    @Override
    public TaskIterator createTaskIterator() {
        return null;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public String getId() {
        return "org.baderlab.simple";
    }

    @Override
    public String getName() {
        return "simple";
    }

    @Override
    public String getDescription() {
        return "oh so simple";
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public URL getWebsite() {
        return null;
    }

    @Override
    public TaskObserver getTaskObserver() {
        return null;
    }

    @Override
    public JComponent getQueryComponent() {
        return null;
    }

    @Override
    public JComponent getOptionsComponent() {
        return null;
    }

}
