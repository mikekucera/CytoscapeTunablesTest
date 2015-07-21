package org.baderlab.tt.internal.layout;

import java.io.IOException;

import org.baderlab.tt.internal.tunables.YesNoMaybe;
import org.cytoscape.work.Tunable;
import org.cytoscape.work.TunableValidator;

public class NothingLayoutContext implements TunableValidator {

    @Tunable(description="Make the graph look nice?")
    public YesNoMaybe yesNoMaybe = YesNoMaybe.YES;
    
    @Override
    public ValidationState getValidationState(Appendable errMsg) {
        try {
            if(yesNoMaybe == YesNoMaybe.MAYBE) {
                errMsg.append("Are you sure you don't want to give a difinitive answer?");
                return ValidationState.REQUEST_CONFIRMATION;
            }
        } catch(IOException e) { }
        return ValidationState.OK;
    }

}
