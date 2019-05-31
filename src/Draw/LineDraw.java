package Draw;

import Draw.Draw;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class LineDraw extends Draw {
    private Point startPoint = null;
    private Point endPoint = null;
    private Color colour = null;
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

        if(startPoint == null || endPoint == null) {
            return;
        } else {
            if (colour != null) {
                g.setColor(colour);
            }
            g.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());
            doNothing(PAUSE);
        }
    }
}
