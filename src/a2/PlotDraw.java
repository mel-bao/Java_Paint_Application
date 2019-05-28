package a2;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class PlotDraw extends Draw{
    Point startPoint = null;
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required point, return.
        if (list.size() < 1) {
            return;
        }

        startPoint = (Point) it.next();

        if (startPoint == null) {
            return;
        } else {
            g.drawLine((int) startPoint.getX(), (int) startPoint.getY(), (int) startPoint.getX(), (int) startPoint.getY());

        }
    }
}
