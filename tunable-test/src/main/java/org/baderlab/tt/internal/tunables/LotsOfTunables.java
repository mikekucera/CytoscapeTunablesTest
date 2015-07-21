package org.baderlab.tt.internal.tunables;

import org.cytoscape.work.ContainsTunables;
import org.cytoscape.work.Tunable;
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

    
    
    @Override
    public String toString() {
        return "LotsOfTunables [line=" + line + ", yesNoMaybe=" + yesNoMaybe + ", what=" + what + ", str=" + str
                + ", numbers=" + numbers.getSelectedValue() + "]";
    }
    
    
}
