package Draw;

import Draw.Draw;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * <h2>Plot a Point</h2>
 * class to handle plotting a point
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class PlotDraw extends Draw {
    /**
     * Point of the shape to be drawn.
     */
    private Point startPoint = null;
    /**
     * pen colour of the shape to be drawn.
     */
    private Color colour = null;

    /**
     * This method draws the shape on the panel it is called from, with the relevant fill and pen colours.
     * If param list does not have the required information, returns.
     * @param list List of tool instance, Points, and pen colour.
     * @param g Java Swing paint graphics.
     */
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required point, return.
        if (list.size() < 3) {
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
            doNothing(PAUSE);
        }
    }
}
