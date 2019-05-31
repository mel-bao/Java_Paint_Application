package Draw;

import Draw.Draw;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * <h2>Draw an Ellipse</h2>
 * class to handle drawing an ellipse.
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class EllipseDraw extends Draw {
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
     * fill colour of the shape to be drawn.
     */
    private Color fill = null;

    /**
     * This method draws the shape on the panel it is called from, with the relevant fill and pen colours.
     * If param list does not have the required information, returns.
     * @param list List of tool instance, Points, pen colour and fill colour.
     * @param g Java Swing paint graphics.
     */
    public void draw(List list, Graphics g) {
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if(list.size() < 7) {
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
                g.setColor(Color.black);
            }
            g.drawOval((int)startPoint.getX(), (int)startPoint.getY(), (int)(endPoint.getX()-startPoint.getX()), (int)(endPoint.getY()-startPoint.getY()));
            doNothing(PAUSE);
        }
    }
}
