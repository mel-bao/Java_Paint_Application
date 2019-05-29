package a2;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class EllipseDraw extends Draw{
    private Point startPoint = null;
    private Point endPoint = null;
    private Color colour = null;
    private Color fill = null;
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if(list.size() < 2) {
            return;
        }

        Draw tempTool = (Draw)it.next();
        startPoint = (Point)it.next();
        endPoint = (Point)it.next();
        colour = (Color)it.next();
        fill = (Color)it.next();

        if(startPoint == null || endPoint == null) {
            return;
        } else {
            if (fill != null) {
                g.setColor(fill);
                g.fillOval((int)startPoint.getX(), (int)startPoint.getY(), (int)(endPoint.getX()-startPoint.getX()), (int)(endPoint.getY()-startPoint.getY()));
            }
            if (colour != null) {
                g.setColor(colour);
            } else {
                g.setColor(colour.black);
            }
            g.drawOval((int)startPoint.getX(), (int)startPoint.getY(), (int)(endPoint.getX()-startPoint.getX()), (int)(endPoint.getY()-startPoint.getY()));
        }
    }
}
