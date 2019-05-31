package Draw;

import Draw.Draw;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * <h2>Draw a Polygon</h2>
 * class to handle drawing a polygon.
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class PolygonDraw extends Draw {
    /**
     * pen colour of the shape to be drawn.
     */
    private Color colour = null;
    /**
     * Point of the shape to be drawn.
     */
    private Point point = null;
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
        if(list.size()<9)
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
            g.setColor(Color.black);
        }
        g.drawPolygon(p);
        doNothing(PAUSE);
    }
}
