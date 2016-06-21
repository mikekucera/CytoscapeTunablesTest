package org.baderlab.st.internal.actions;

import java.awt.event.ActionEvent;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.subnetwork.CyRootNetwork;
import org.cytoscape.model.subnetwork.CySubNetwork;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class CreateSubnetworkAction extends AbstractCyAction {

    @Inject private CyNetworkFactory networkFactory;
    @Inject private CyNetworkManager networkManager;
    
    public CreateSubnetworkAction() {
        super("Create sub network");
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = networkFactory.createNetwork();
        CySubNetwork subNetwork = (CySubNetwork)network;
        
        networkManager.addNetwork(subNetwork);
        
        CyRootNetwork rootNetwork = subNetwork.getRootNetwork();
        CySubNetwork subNetwork2 = rootNetwork.addSubNetwork();
        
        networkManager.addNetwork(subNetwork2);
        
        System.out.println("Done");
    }

}
