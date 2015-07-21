package org.baderlab.tt.internal.tunables;

import java.util.Arrays;

import org.cytoscape.work.Tunable;
import org.cytoscape.work.util.ListSingleSelection;

public class VoteList {

    @Tunable(description="What is your opinion?")
    public ListSingleSelection<YesNoMaybe> yesNoMaybe = new ListSingleSelection<>(Arrays.asList(YesNoMaybe.values()));

    @Override
    public String toString() {
        return "VoteList [yesNoMaybe=" + yesNoMaybe + "]";
    }
    
    
}
