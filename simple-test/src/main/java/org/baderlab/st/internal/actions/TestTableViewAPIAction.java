package org.baderlab.st.internal.actions;

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.view.model.CyColumnView;
import org.cytoscape.view.model.CyTableView;
import org.cytoscape.view.model.CyTableViewFactory;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.table.BasicTableVisualLexicon;
import org.cytoscape.view.presentation.property.table.CellBarChart;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingMultiAttributeFunctionFactory;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

public class TestTableViewAPIAction extends AbstractCyAction {

    // Assume we acquire these services in the usual ways
    private CyTableManager tableManager;
    private CyTableFactory tableFactory;
    private CyTableViewFactory tableViewFactory;
    private VisualStyleFactory visualStyleFactory;
    
    private VisualMappingFunctionFactory continuousMappingFactory;
    private VisualMappingMultiAttributeFunctionFactory multiAttributeMappingFactory;
    
    public TestTableViewAPIAction() {
        super("TestTableViewAPIAction");
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        createTableViewAndStyle();
    }
    
    
    private void createTableViewAndStyle() {
        // Create a table
        CyTable table = tableFactory.createTable("My Table", "SUID", Long.class, true, true);
        tableManager.addTable(table);
        
        // Add two columns and one row
        table.createColumn("namespace", "string_col", String.class, false);
        CyColumn stringCol = table.getColumn("my", "string_col");
        table.createColumn("namespace", "int_col", Integer.class, false);
        CyColumn intCol = table.getColumn("namespace", "int_col");
        CyColumn doubleCol = table.getColumn("namespace", "double_col");
        CyRow row = table.getRow(1);
        
        // flush events here?
        
        // Get view objects
        CyTableView tableView = tableViewFactory.createTableView(table);
        View<CyColumn> stringColView = tableView.getColumnView(stringCol);
        View<CyColumn> intColView = tableView.getColumnView(intCol);
        View<CyColumn> doubleColView = tableView.getColumnView(doubleCol);
        View<CyRow> rowView = tableView.getRowView(row);
                
        
        // Create a visual style
        VisualStyle visualStyle = visualStyleFactory.createVisualStyle("My Table Visual Style");
        
        
        
        // set bypasses
        rowView.setLockedValue(BasicTableVisualLexicon.ROW_HIGHLIGHT, true);
        
        
        
        // set default VPs
        visualStyle.setDefaultValue(BasicTableVisualLexicon.TABLE_BACKGROUND_PAINT, Color.GRAY);
        visualStyle.setDefaultValue(BasicTableVisualLexicon.COLUMN_HEADER_PAINT, Color.RED);
        
        
        
        // Create a mapping for cells in a column. Source data column is 'my::int_col', target column is also 'my::int_col'.
        
        // First argument to createVisualMappingFunction() specifies the source column
        // Because its a CELL_XXX visual property it will be applied to every cell in the column.
        ContinuousMapping<Integer,Paint> mapping = (ContinuousMapping<Integer, Paint>)continuousMappingFactory.createVisualMappingFunction(
                "namespace::int_col", Integer.class, BasicTableVisualLexicon.CELL_BACKGROUND_PAINT);
        // not shown: setting up the mapping in the usual way
        // new version of addVisualMappingFunction with argument that allows you to specify the target columns
        visualStyle.addVisualMappingFunction(mapping, intColView);
        
        
        // Below is an idea for how we could implement multiple source columns.
        
        // Create a column to display the chart. The type of the column doesn't matter, all the values can be null.
        table.createColumn("namespace", "bar_chart", String.class, true);
        // flush events?
        View<CyColumn> barChartDisplayColumnView = tableView.getColumnView(table.getColumn("namespace", "bar_chart"));
        
        // Number.class used as the type allows to mix int and double source columns.
        VisualMappingFunction<List<Number>,CellBarChart> multiMapping = multiAttributeMappingFactory.createVisualMappingFunction(
                Arrays.asList("namespace::int_col", "namespace::double_col"), Number.class, BasicTableVisualLexicon.CELL_BAR_CHART);
        visualStyle.addVisualMappingFunction(multiMapping, barChartDisplayColumnView);
        
        
        
        // There's no special support for namespaces. If you want to apply a VP to all columns in a namespace just do this...
        Collection<CyColumnView> namespaceColumns = tableView.getColumnViews("namespace");
        visualStyle.addVisualMappingFunction(mapping, namespaceColumns);

        
        
        // Apply the visual style
        visualStyle.apply(tableView);        
    }

}






