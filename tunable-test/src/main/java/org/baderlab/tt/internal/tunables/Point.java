package org.baderlab.tt.internal.tunables;

import org.cytoscape.work.Tunable;

public class Point {

    @Tunable(description="X Coordinate")
    public int x = 0;

    @Tunable(description="Y Coordinate")
    public int y = 0;

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

}
