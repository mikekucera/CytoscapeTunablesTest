package org.baderlab.tt.internal.tunables;

import java.util.Date;

import org.cytoscape.work.Tunable;

/**
 * Type not supported.
 * 
 * This is used to test how AbstractTunableHelper will handle types that it doesn't support.
 * Use AbstractTunableHandler.setThrowExceptions() to make sure that tunable objects, like
 * layout contexts, don't have unsupported tunable fields.
 */
public class BadTunables {

    // Property serializer does not support java.util.Date
    @Tunable
    public Date date = new Date();
}
