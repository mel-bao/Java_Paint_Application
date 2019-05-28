package a2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PolygonDraw extends Draw{
    Color colour = null;
    Point point = null;
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if(list.size()<3)
        {
            return;
        }
        Polygon p = new Polygon();

        for (int i =0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                point = (Point) it.next();
                p.addPoint((int) point.getX(), (int) point.getY());
            } else {
                colour = (Color)it.next();
            }
        }

        if (colour != null) {
            g.setColor(colour);
        }
        g.drawPolygon(p);
    }
}
