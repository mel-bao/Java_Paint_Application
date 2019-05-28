package a2;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class PolygonDraw extends Draw{
    public void draw(List list, Graphics g) {
        Point point = null;
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if(list.size()<3) {
            return;
        }
        Polygon p = new Polygon();
        for(;it.hasNext();) {
            point = (Point)it.next();
            p.addPoint((int)point.getX(), (int)point.getY());
        }
        g.drawPolygon(p);
    }
}
