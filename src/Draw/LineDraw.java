package Draw;

import Draw.Draw;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * <h2>Draw a Line</h2>
 * class to handle drawing a line.
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class LineDraw extends Draw {
    /**
     * start Point of the shape to be drawn.
     */
    private Point startPoint = null;
    /**
     * end Point of the shape to be drawn.
     */
    private Point endPoint = null;
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
        //if the list does not contain the required two points, return.
        if(list.size() < 4) {
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
