package org.baderlab.st.internal.actions;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.baderlab.st.internal.util.ComboItem;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.events.RowsSetEvent;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.service.util.CyServiceRegistrar;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class ColumnSetAllAction extends AbstractCyAction {

    @Inject private CySwingApplication swingApplication;
    @Inject private CyApplicationManager applicationManager;
    @Inject private CyServiceRegistrar serviceRegistrar;

    private RowsSetListener rowsSetListener = null;
    
    @Inject
    public ColumnSetAllAction(CyApplicationManager applicationManager) {
        super("CyColumn.setAll()", applicationManager, null, null);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        registerListener();
        
        CyNetwork network = applicationManager.getCurrentNetwork();
        CyTable table = network.getDefaultNodeTable();
        ColumnAndValueInputPanel inputPanel = new ColumnAndValueInputPanel(table);
        JFrame parent = swingApplication.getJFrame();
        
        int option = JOptionPane.showConfirmDialog(parent, inputPanel, "Set all column values", JOptionPane.OK_CANCEL_OPTION);
        if(option == JOptionPane.OK_OPTION) {
            CyColumn column = inputPanel.getColumn();
            Object value = inputPanel.getValue();
            
            column.replaceAll(value, column.getType());
        }
    }

    
    class MyRowsSetListener implements RowsSetListener {
        @Override
        public void handleEvent(RowsSetEvent e) {
            String name = Thread.currentThread().getName();
            System.out.println("RowsSetEvent fired on '" + name + "'");
        }
    }
    
    
    private synchronized void registerListener() {
        if(rowsSetListener == null) {
            rowsSetListener = new MyRowsSetListener();
            serviceRegistrar.registerAllServices(rowsSetListener, new Properties());
        }
    }
    
    
    class ColumnAndValueInputPanel extends JPanel {
        private JComboBox<ComboItem<CyColumn>> columns;
        private JTextField value;

        public ColumnAndValueInputPanel(CyTable table) {
            columns = new JComboBox<>();
            for(CyColumn column : table.getColumns()) {
                String prefix = column.getVirtualColumnInfo().isVirtual() ? "V: " : "L: ";
                columns.addItem(new ComboItem<CyColumn>(column, prefix + column.getName()));
            }
            
            value = new JTextField();
            
            setLayout(new BorderLayout());
            add(columns, BorderLayout.NORTH);
            add(value, BorderLayout.CENTER);
        }

        public CyColumn getColumn() {
            return columns.getItemAt(columns.getSelectedIndex()).getValue();
        }

        public Object getValue() {
            String valueText = value.getText().trim();
            Class<?> type = getColumn().getType();
            
            if(String.class.equals(type)) {
                return valueText.isEmpty() ? null : valueText;
            }
            if(Long.class.equals(type)) {
                return Long.valueOf(valueText);
            }
            if(Integer.class.equals(type)) {
                return Integer.valueOf(valueText);
            }
            if(Boolean.class.equals(type)) {
                return Boolean.valueOf(valueText);
            }
            if(Double.class.equals(type)) {
                return Double.valueOf(valueText);
            }
            return null;
        }
    }

}
