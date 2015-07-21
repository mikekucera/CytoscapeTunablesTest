package org.baderlab.tt.internal.tunables;

import org.cytoscape.work.ContainsTunables;
import org.cytoscape.work.Tunable;

public class LotsOfTunables {

    @ContainsTunables
    public Line line = new Line();
    
    @Tunable
    public YesNoMaybe yesNoMaybe = YesNoMaybe.YES;
    
    @Tunable
    public boolean what = true;
    
    @Tunable
    public String str = "This is a string";

    @Override
    public String toString() {
        return "LotsOfTunables [line=" + line + ", yesNoMaybe=" + yesNoMaybe + ", what=" + what + ", str=" + str + "]";
    }
    
    
    
}
