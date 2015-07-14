package org.baderlab.tt.internal.tunables;

import org.cytoscape.work.ContainsTunables;

public class Line {

    @ContainsTunables
    public Point startPoint = new Point();

    @ContainsTunables
    public Point endPoint = new Point();

    @Override
    public String toString() {
        return "Line [startPoint=" + startPoint + ", endPoint=" + endPoint + "]";
    }

}
