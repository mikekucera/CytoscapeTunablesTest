package org.baderlab.tt.internal.tunables;

import org.cytoscape.work.Tunable;

public class Vote {

    @Tunable(description="What do you think?")
    public YesNoMaybe vote;

    @Override
    public String toString() {
        return "Vote [vote=" + vote + "]";
    }
    
    
}
