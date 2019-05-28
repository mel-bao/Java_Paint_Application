package a2;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class EllipseDraw extends Draw{
    Point startPoint = null;
    Point endPoint = null;
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if(list.size() < 2) {
            return;
        }

        startPoint = (Point)it.next();
        endPoint = (Point)it.next();

        if(startPoint == null || endPoint == null) {
            return;
        } else {
            g.drawOval((int)startPoint.getX(), (int)startPoint.getY(), (int)(endPoint.getX()-startPoint.getX()), (int)(endPoint.getY()-startPoint.getY()));
        }
    }
}
