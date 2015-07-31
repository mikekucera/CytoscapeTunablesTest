package org.baderlab.tt.internal.tunables;

import org.cytoscape.work.ContainsTunables;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.BoundedDouble;
import org.cytoscape.work.util.BoundedInteger;
import org.cytoscape.work.util.ListMultipleSelection;
import org.cytoscape.work.util.ListSingleSelection;

public class LotsOfTunables {

    @ContainsTunables
    public Line line = new Line();
    
    @Tunable
    public YesNoMaybe yesNoMaybe = YesNoMaybe.YES;
    
    @Tunable
    public boolean what = true;
    
    @Tunable
    public String str = "This is a string";

    @Tunable
    public ListSingleSelection<Integer> numbers = new ListSingleSelection<>(1,2,3,4,5);
    
    @Tunable
    public ListMultipleSelection<String> names = new ListMultipleSelection<>("Fred", "Mark", "Max", "Nancy", "Ester");

    @Tunable
    public BoundedInteger intRange = new BoundedInteger(0, 5, 9, true, true);
    
    @Tunable
    public BoundedDouble doubleRange = new BoundedDouble(0.0, 0.5, 1.0, true, true);

    
    @Override
    public String toString() {
        return "LotsOfTunables [line=" + line + ", yesNoMaybe=" + yesNoMaybe + ", what=" + what + ", str=" + str
                + ", numbers=" + numbers.getSelectedValue() + ", names=" + names.getSelectedValues() + 
                ", intRange=" + intRange.getValue() + ", doubleRange=" + doubleRange.getValue() + "]";
    }
    
    
    
}
