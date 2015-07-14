package org.baderlab.tt.internal.action;

import com.google.inject.assistedinject.Assisted;

public interface DialogTestActionFactory {

    DialogTestAction create(@Assisted String title, @Assisted Object objectWithTunables);
}
