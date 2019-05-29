package a2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PolygonDraw extends Draw{
    private Color colour = null;
    private Point point = null;
    private Color fill = null;
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if(list.size()<3)
        {
            return;
        }
        Polygon p = new Polygon();

        for (int i =0; i < list.size(); i++) {
            //the tool type is the first item in the list
            if (i==0){
                Draw tempTool = (Draw)it.next();
            }
            //the points cover the second to third last items in the list
            else if (i > 0 && i < list.size() - 2) {
                point = (Point) it.next();
                p.addPoint((int) point.getX(), (int) point.getY());
            }
            //the pen colour id the penultimate item in the list
            else if (i < list.size() - 1) {
                colour = (Color)it.next();
            }
            //fill colour is the final item in the list
            else {
                fill = (Color)it.next();
            }
        }

        if (fill != null) {
            g.setColor(fill);
            g.fillPolygon(p);
        }
        if (colour != null) {
            g.setColor(colour);
        } else {
            g.setColor(colour.black);
        }
        g.drawPolygon(p);
    }
}
