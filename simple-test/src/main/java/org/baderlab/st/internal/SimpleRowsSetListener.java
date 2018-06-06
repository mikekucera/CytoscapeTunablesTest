package org.baderlab.st.internal;

import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;

public class SimpleRowsSetListener implements RowsSetListener {

    @Override
    public void handleEvent(RowsSetEvent event) {
        if(event.containsColumn("selected")) {
            CyTable table = event.getSource();
            System.out.println("RowsSetEvent: " + table.getSUID() + ", size: " + event.getPayloadCollection().size() + " " + table.getTitle());
        }
    }

}
