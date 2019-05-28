package a2;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class RectangleDraw extends Draw {
    Point startPoint = null;
    Point endPoint = null;
    Color colour = null;
    Color fill = null;
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if(list.size() < 2) {
            return;
        }

        startPoint = (Point)it.next();
        endPoint = (Point)it.next();
        colour = (Color)it.next();
        fill = (Color)it.next();

        if(startPoint == null || endPoint == null) {
            return;
        } else {
            if (fill != null) {
                g.setColor(fill);
                g.fillRect((int)startPoint.getX(), (int)startPoint.getY(), (int)(endPoint.getX()-startPoint.getX()), (int)(endPoint.getY()-startPoint.getY()));
            }
            if (colour != null) {
                g.setColor(colour);
            } else {
                g.setColor(colour.black);
            }
            g.drawRect((int)startPoint.getX(), (int)startPoint.getY(), (int)(endPoint.getX()-startPoint.getX()), (int)(endPoint.getY()-startPoint.getY()));
        }
    }
}
