package org.baderlab.tt.internal.action;

import com.google.inject.assistedinject.Assisted;

/**
 * Guice assisted inject factory for actions that need to take extra parameters in their constructors.
 */
public interface ActionFactory {

    DialogTestAction createDialogTestAction(@Assisted String title, @Assisted Object objectWithTunables);
    
    PropertyTestAction createPropertyTestAction(@Assisted String title, @Assisted PropertyTestAction.PropertyGetter propertyGetter);
    
}
