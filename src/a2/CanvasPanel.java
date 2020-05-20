package a2;

import Draw.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * <h1>Canvas Panel Class</h1>
 * This class draws scalable with resize shapes on a square JPanel and records these shapes based on tool
 * selected and mouse clicks, presses and releases when on the panel.
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {
    /**
     * instance of Draw set when toolButton selected.
     */
    private Draw tool;
    /**
     * RGB pen colour set with colour chooser.
     */
    private Color colour;
    /**
     * RGB fill colour set with colour chooser.
     */
    private Color fill;
    /**
     * start Point of drawn shape set when mouse pressed or clicked.
     */
    private Point startPoint = null;
    /**
     * end Point of drawn shape set when mouse released.
     */
    private Point endPoint = null;
    /**
     * the width of the panel set on resize.
     */
    private double w;
    /**
     * the height of the panel set on resize.
     */
    private double h;
    /**
     * the start Point set on mouse press used to continuously draw line when clicking and dragging.
     */
    private Point pointStart = null;
    /**
     * the end Point set as mouse is dragged and moved, used to continuously draw line.
     */
    private Point pointEnd   = null;
    /**
     * an arrayList of Points added to when clicking to draw polygon
     */
    private ArrayList<Point> list = new ArrayList<Point>();
    /**
     * the arrayList of Lists of shapes with co-ordinates in VEC format, added to when shape is added (drawn).
     */
    private ArrayList<ArrayList> listOfShapes = new ArrayList<ArrayList>();
    /**
     * the arrayList of Lists of shapes with co-ordinates in panel Point format, created before painting
     * from ListOfShapes dependant on canvas dimensions stored on resize.
     */
    private ArrayList<ArrayList> listOfDrawableShapes = new ArrayList<ArrayList>();

    /**
     * this method overrides the PreferredSize of the panel, ensuring that the panel is always square
     * depending on the dimensions of it's container panel.
     * @return new square dimensions of the panel
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        Container c = getParent();
        if (c != null) {
            d = c.getSize();
        } else {
            return new Dimension(10, 10);
        }
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        int s = (w < h ? w : h);
        return new Dimension(s, s);
    }

    /**
     * this method overrides the paintComponent method to paint on the panel, firstly a line that updates as
     * the user clicks and drags using line tool. translates the VEC coordinates in each command in
     * ListOfShapes into Point co-ordinates that can be used to draw on the panel. Than calls the relevant ShapeDraw
     * method for each command.
     * @param g Java swing graphics.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //continuous drawing on click and drag
        if (pointStart != null) {
            //flip startPoint and endPoint for drawing rectangle or ellipse
            if (tool instanceof RectangleDraw || tool instanceof EllipseDraw) {
                if (pointEnd.getX() < pointStart.getX()) {
                    Point temps = new Point(pointEnd.x, pointStart.y);
                    Point tempe = new Point(pointStart.x, pointEnd.y);
                    pointEnd = tempe;
                    pointStart = temps;
                }
                if (pointEnd.getY() < pointStart.getY()) {
                    Point tempe = new Point(pointEnd.x, pointStart.y);
                    Point temps = new Point(pointStart.x, pointEnd.y);
                    pointEnd = tempe;
                    pointStart = temps;
                }
            }
            if (tool instanceof LineDraw) {
                g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
            } else if (tool instanceof RectangleDraw) {
                g.drawRect(pointStart.x, pointStart.y, pointEnd.x - pointStart.x, pointEnd.y - pointStart.y);
            } else if (tool instanceof EllipseDraw) {
                g.drawOval(pointStart.x, pointStart.y, pointEnd.x - pointStart.x, pointEnd.y - pointStart.y);
            }
        }

        listOfDrawableShapes.clear();
        //translate list of shapes with vec co-ords and set as listOfDrawableShapes
        for (ArrayList list: listOfShapes) {
            ArrayList tempList = new ArrayList();
            tempList.add(list.get(0));
            if (list.get(0) instanceof PlotDraw) {
                Point point = translateToDrawableFormat((double) list.get(1), (double) list.get(2));
                tempList.add(point);
                tempList.add(list.get(3));
            } else if (list.get(0) instanceof LineDraw) {
                Point s = translateToDrawableFormat((double) list.get(1), (double) list.get(2));
                tempList.add(s);
                Point e = translateToDrawableFormat((double) list.get(3), (double) list.get(4));
                tempList.add(e);
                tempList.add(list.get(5));
            } else if (list.get(0) instanceof RectangleDraw) {
                Point s = translateToDrawableFormat((double) list.get(1), (double) list.get(2));
                tempList.add(s);
                Point e = translateToDrawableFormat((double) list.get(3), (double) list.get(4));
                tempList.add(e);
                tempList.add(list.get(5));
                tempList.add(list.get(6));
            } else if (list.get(0) instanceof EllipseDraw) {
                Point s = translateToDrawableFormat((double) list.get(1), (double) list.get(2));
                tempList.add(s);
                Point e = translateToDrawableFormat((double) list.get(3), (double) list.get(4));
                tempList.add(e);
                tempList.add(list.get(5));
                tempList.add(list.get(6));
            } else if (list.get(0) instanceof PolygonDraw) {
                for (int i=1; i < (list.size()-2); i++) {
                    if (i % 2 != 0) {
                        Point p = translateToDrawableFormat((double) list.get(i), (double) list.get(i+1));
                        tempList.add(p);
                    }
                }
                tempList.add(list.get(list.size()-2));
                tempList.add(list.get(list.size()-1));
            }
            listOfDrawableShapes.add(tempList);
        }

        //call relevant ShapeDraw method to draw each shape in the list
        if (!listOfDrawableShapes.isEmpty()) {
            for (ArrayList shape: listOfDrawableShapes) {
                if (shape.get(0) instanceof PlotDraw) {
                    Draw tempTool = new PlotDraw();
                    tempTool.draw(shape, g);
                } else if (shape.get(0) instanceof LineDraw) {
                    Draw tempTool = new LineDraw();
                    tempTool.draw(shape, g);
                } else if (shape.get(0) instanceof RectangleDraw) {
                    Draw tempTool = new RectangleDraw();
                    tempTool.draw(shape, g);
                } else if (shape.get(0) instanceof EllipseDraw) {
                    Draw tempTool = new EllipseDraw();
                    tempTool.draw(shape, g);
                } else if (shape.get(0) instanceof PolygonDraw) {
                    Draw tempTool = new PolygonDraw();
                    tempTool.draw(shape, g);
                }
            }
        }
    }

    /**
     * {@link CanvasPanel#tool}
     * @param tool and instance of Draw.
     */
    public void setTool(Draw tool) {
        this.tool = tool;
    }

    /**
     * {@link CanvasPanel#tool}
     * @return the current Draw instance tool.
     */
    public Draw getTool() {
        return tool;
    }

    /**
     * {@link CanvasPanel#colour}
     * @param colour RGB colour from colour chooser.
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * {@link CanvasPanel#colour}
     * @return current RGB colour colour.
     */
    public Color getColour() {
        return colour;
    }

    /**
     * {@link CanvasPanel#fill}
     * @param colour RGB colour from colour chooser.
     */
    public void setFillColour(Color colour) {
        this.fill = colour;
    }

    /**
     * {@link CanvasPanel#fill}
     * @return current RGB colour fill.
     */
    public Color getFillColour() {
        return fill;
    }

    /**
     * {@link CanvasPanel#listOfShapes}
     * @param list List of shape commands configured in FileManager.loadFile.
     */
    public void setListOfShapes(ArrayList<ArrayList> list) {
        this.listOfShapes = list;
        repaint();
    }

    /**
     * {@link CanvasPanel#listOfShapes}
     * @return current ArrayList listOfShapes.
     */
    public ArrayList<ArrayList> getListOfShapes() {
        return listOfShapes;
    }

    /**
     * {@link CanvasPanel#listOfShapes}
     * clears all values in listOfShapes.
     */
    public void clearListOfShapes() {
        listOfShapes.clear();
        repaint();
    }

    /**
     * {@link CanvasPanel#listOfShapes}
     * removes last list/command in listOfShapes.
     */
    public void undoLastCommand() {
        if (!listOfShapes.isEmpty()) {
            listOfShapes.remove(listOfShapes.size() - 1);
            repaint();
        }
    }

    /**
     * This method translates the x co-ordinate of a point into VEC 0.0 to 1.0 format based on
     * the current dimensions of the panel, when the point is recorded.
     * @param point a Point on the canvas.
     * @return Double x co-ordinate of that point in VEC format.
     */
    private double translateXToVecFormat(Point point) {
        //(0,0) on panel is equivalent to (0,0) Vec co-ordinate
        //(w,h) on panel is equivalent to (1,1) Vec co-ordinate
        double x = 1.0 / (w + 1.0);
        double xcoord = x * point.getX();
        return xcoord;
    }

    /**
     * This method translates the y co-ordinate of a point into VEC 0.0 to 1.0 format based on
     * the current dimensions of the panel, when the point is recorded.
     * @param point a Point on the canvas.
     * @return Double y co-ordinate of that point in VEC format.
     */
    private double translateYToVecFormat(Point point) {
        double y = 1.0 / (h + 1.0);
        double ycoord = y * point.getY();
        return ycoord;
    }

    /**
     * This method translates a pair of x and y co-ordinates in VEC format to the requisite Point on the panel
     * depending on the panels current dimensions.
     * @param xcoord double x co-ordinate in VEC format.
     * @param ycoord double y co-ordinate in VEC format.
     * @return Point on the panel corresponding to the two params.
     */
    private Point translateToDrawableFormat(double xcoord, double ycoord) {
        double x = 1.0 / (w + 1.0);
        double y = 1.0 / (h + 1.0);
        double xpoint = xcoord / x;
        double ypoint = ycoord / y;
        Point p = new Point((int)xpoint, (int)ypoint);
        return p;
    }

    /**
     * this method adds an ArrayList with instance of plotDraw, co-ordinates in VEC format and pen colour
     * to listOfShapes when called.
     * @param plot Point of plot.
     * @param colour Color pen colour.
     */
    private void addPlot(Point plot, Color colour) {
        ArrayList tempList = new ArrayList();
        tempList.add(tool);
        double xcoord = translateXToVecFormat(plot);
        double ycoord = translateYToVecFormat(plot);
        tempList.add(xcoord);
        tempList.add(ycoord);
        tempList.add(colour);
        listOfShapes.add(tempList);
        repaint();
    }

    /**
     * this method adds an ArrayList with instance of lineDraw, co-ordinates in VEC format and pen colour
     * to listOfShapes when called.
     * @param s start Point of line
     * @param e end Point of line
     * @param colour Color pen colour.
     */
    private void addLine(Point s, Point e, Color colour) {
        ArrayList tempList = new ArrayList();
        tempList.add(tool);
        double sxcoord = translateXToVecFormat(s);
        double sycoord = translateYToVecFormat(s);
        tempList.add(sxcoord);
        tempList.add(sycoord);
        double excoord = translateXToVecFormat(e);
        double eycoord = translateYToVecFormat(e);
        tempList.add(excoord);
        tempList.add(eycoord);
        tempList.add(colour);
        listOfShapes.add(tempList);
        repaint();
    }

    /**
     * this method adds an ArrayList with instance of rectangleDraw, co-ordinates in VEC format, pen
     * and fill colour to listOfShapes when called.
     * @param s start Point of rectangle.
     * @param e end Point of rectangle.
     * @param colour Color pen colour of rectangle.
     * @param fill Color fill colour of rectangle.
     */
    private void addRectangle(Point s, Point e, Color colour, Color fill) {
        ArrayList tempList = new ArrayList();
        tempList.add(tool);
        double sxcoord = translateXToVecFormat(s);
        double sycoord = translateYToVecFormat(s);
        tempList.add(sxcoord);
        tempList.add(sycoord);
        double excoord = translateXToVecFormat(e);
        double eycoord = translateYToVecFormat(e);
        tempList.add(excoord);
        tempList.add(eycoord);
        tempList.add(colour);
        tempList.add(fill);
        listOfShapes.add(tempList);
        repaint();
    }

    /**
     * this method adds an ArrayList with instance of ellipseDraw, co-ordinates in VEC format, pen
     * and fill colour to listOfShapes when called.
     * @param s start Point of ellipse.
     * @param e end Point of ellipse.
     * @param colour Color pen of ellipse.
     * @param fill Color fill of ellipse.
     */
    private void addEllipse(Point s, Point e, Color colour, Color fill) {
        ArrayList tempList = new ArrayList();
        tempList.add(tool);
        double sxcoord = translateXToVecFormat(s);
        double sycoord = translateYToVecFormat(s);
        tempList.add(sxcoord);
        tempList.add(sycoord);
        double excoord = translateXToVecFormat(e);
        double eycoord = translateYToVecFormat(e);
        tempList.add(excoord);
        tempList.add(eycoord);
        tempList.add(colour);
        tempList.add(fill);
        listOfShapes.add(tempList);
        repaint();
    }

    /**
     * this method adds an ArrayList with instance of polygonDraw, co-ordinates in VEC format, pen
     * and fill colour to listOfShapes when called.
     * @param l list of polygon co-ordinates in Point format.
     * @param colour Color pen of polygon.
     * @param fill Color fill of polygon.
     */
    private void addPolygon(ArrayList<Point> l, Color colour, Color fill) {
        ArrayList tempList = new ArrayList();
        tempList.add(tool);
        for (Point p: l) {
            double xcoord = translateXToVecFormat(p);
            double ycoord = translateYToVecFormat(p);
            tempList.add(xcoord);
            tempList.add(ycoord);
        }
        tempList.add(colour);
        tempList.add(fill);
        listOfShapes.add(tempList);
        repaint();
        list.clear();
    }

    /**
     * This method calls the relevant addShape method depending on tool instance when mouse clicked.
     * @param e MouseEvent click.
     */
    public void mouseClicked(MouseEvent e) {
        if(tool instanceof PlotDraw) {
            addPlot(startPoint, colour);
        } else if (tool instanceof PolygonDraw) {
            //if left click record polygon point
            if (e.getButton() == MouseEvent.BUTTON1) {
                System.out.println("left click");
                list.add(e.getPoint());
            } // if right click add last point and paint
            else if (e.getButton() == MouseEvent.BUTTON3) {
                System.out.println("right click");
                list.add(e.getPoint());
                addPolygon(list, colour, fill);
            }
        }
    }

    /**
     * This method is unused.
     * @param e mouseEvent enter panel.
     */
    public void mouseEntered(MouseEvent e){}

    /**
     * This method is unused.
     * @param e mouseEvent exited panel.
     */
    public void mouseExited(MouseEvent e){}

    /**
     * This method sets start and end points when mouse is pressed.
     * @param e mouseEvent press.
     */
    public void mousePressed(MouseEvent e){
        startPoint = e.getPoint();
        pointStart = e.getPoint();
    }

    /**
     * This method calls the relevant addShape method depending on tool instance when mouse released. And sets
     * the end point.
     * @param e mouseEvent release.
     */
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
        pointStart = null;
        //flip startPoint and endPoint for drawing rectangle or ellipse
        if (tool instanceof RectangleDraw || tool instanceof EllipseDraw) {
            if (endPoint.getX() < startPoint.getX()) {
                Point temp = endPoint;
                endPoint = startPoint;
                startPoint = temp;
            }
            if (endPoint.getY() < startPoint.getY()) {
                int temp = (int) endPoint.getY();
                endPoint.y = (int) startPoint.getY();
                startPoint.y = temp;
            }
        }

        if (tool instanceof LineDraw) {
            addLine(startPoint, endPoint, colour);
        } else if (tool instanceof RectangleDraw) {
            addRectangle(startPoint, endPoint, colour, fill);
        } else if (tool instanceof EllipseDraw) {
            addEllipse(startPoint, endPoint, colour, fill);
        }
    }

    /**
     * This method sets the end point to draw guide line when moving mouse.
     * @param e mouseEvent moves.
     */
    public void mouseMoved(MouseEvent e) {
        pointEnd = e.getPoint();
    }

    /**
     * This method sets the end point to draw guide line when dragging mouse. and repaints the panel.
     * @param e mouseEvent dragged.
     */
    public void mouseDragged(MouseEvent e) {
        pointEnd = e.getPoint();
        repaint();
    }

    /**
     * This method sets the width and height variables and repaints the panel when resizing.
     * @param e ComponentEvent resize.
     */
    public void componentResized(ComponentEvent e) {
        Dimension d = this.getSize();
        w = d.getWidth();
        h = d.getHeight();
        repaint();
    }

    /**
     * This method is unused.
     * @param e ComponentEvent
     */
    public void componentMoved(ComponentEvent e) {}

    /**
     * This method is unused.
     * @param e ComponentEvent
     */
    public void componentShown(ComponentEvent e) {}

    /**
     * This method is unused.
     * @param e ComponentEvent
     */
    public void componentHidden(ComponentEvent e) {}
}
