package a2;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class PlotDraw extends Draw{
    private Point startPoint = null;
    private Color colour = null;
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required point, return.
        if (list.size() < 1) {
            return;
        }

        Draw tempTool = (Draw)it.next();
        startPoint = (Point) it.next();
        colour = (Color)it.next();

        if (startPoint == null) {
            return;
        } else {
            if (colour != null) {
                g.setColor(colour);
            }
            g.drawLine((int) startPoint.getX(), (int) startPoint.getY(), (int) startPoint.getX(), (int) startPoint.getY());
        }
    }
}
