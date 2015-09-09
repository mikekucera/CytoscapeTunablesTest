package org.baderlab.st.internal.actions;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;

import com.google.inject.Inject;

/**
 * Creates an attribute (table column) in the local node table for the current network.
 * Needed this because there doesn't seem to be a way to do this through the Cytoscape UI.
 */
@SuppressWarnings("serial")
public class CreateLocalAttributeAction extends AbstractCyAction {

    @Inject private CyApplicationManager applicationManager;
    @Inject private CySwingApplication swingApplication;
    
    @Inject
    public CreateLocalAttributeAction(CyApplicationManager applicationManager) {
        super("Create Local Attribute", applicationManager, null, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        CyNetwork network = applicationManager.getCurrentNetwork();
        if(network == null)
            return;
        
        ColumnAndTypeInputPanel inputPanel = new ColumnAndTypeInputPanel();
        JFrame parent = swingApplication.getJFrame();
        JOptionPane.showMessageDialog(parent, inputPanel, "Local Attribute Name", JOptionPane.QUESTION_MESSAGE);
        
        String name = inputPanel.getName();
        Class<?> type = inputPanel.getType();
        
        if(name != null && !name.isEmpty() && type != null) {
            CyTable localNodeTable = network.getTable(CyNode.class, CyNetwork.LOCAL_ATTRS);
            localNodeTable.createColumn(name, type, false);
        }
    }
    
    
    
    enum SupportedTypes {
        INTEGER("Integer", Integer.class),
        LONG("Long", Long.class),
        DOUBLE("Double", Double.class),
        STRING("String", String.class),
        BOOLEAN("Boolean", Boolean.class);
        
        private final String name;
        private final Class<?> type;
        
        SupportedTypes(String name, Class<?> type) {
            this.name = name;
            this.type = type;
        }
        
        public String toString() {
            return name;
        }
        
        public Class<?> getType() {
            return type;
        }
    }
    
    
    class ColumnAndTypeInputPanel extends JPanel {
        private JTextField name;
        private JComboBox<SupportedTypes> types;
        
        public ColumnAndTypeInputPanel() {
           name = new JTextField();
           types = new JComboBox<>(SupportedTypes.values());
           
           setLayout(new BorderLayout());
           add(name, BorderLayout.NORTH);
           add(types, BorderLayout.CENTER);
        }
        
        public String getName() {
            return name.getText().trim();
        }
        
        public Class<?> getType() {
            return types.getItemAt(types.getSelectedIndex()).type;
        }
    }

}
