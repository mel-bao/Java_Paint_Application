package a2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {
    private Draw tool;
    private Color colour;
    private Color fill;
    private Point startPoint = null;
    private Point endPoint = null;
    private double w;
    private double h;
    private ArrayList<Point> list = new ArrayList<Point>();
    private ArrayList<ArrayList> listOfShapes = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> listOfDrawableShapes = new ArrayList<ArrayList>();


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

    public void setTool(Draw tool) {
        this.tool = tool;
    }

    public Draw getTool() {
        return tool;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public Color getColour() {
        return colour;
    }

    public void setFillColour(Color colour) {
        this.fill = colour;
    }

    public Color getFillColour() {
        return fill;
    }

    public void setListOfShapes(ArrayList<ArrayList> list) {
        this.listOfShapes = list;
    }

    public ArrayList<ArrayList> getListOfShapes() {
        return listOfShapes;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        listOfDrawableShapes.clear();
        //translate list of shapes with vec co-ords and set as listOfDrawableShapes
        for (ArrayList list: listOfShapes) {
            ArrayList tempList = new ArrayList();
            tempList.add(list.get(0));
            Point point = translateToDrawableFormat((double)list.get(1), (double)list.get(2));
            tempList.add(point);
            tempList.add(list.get(3));
            listOfDrawableShapes.add(tempList);
            System.out.println(listOfDrawableShapes);
        }

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

    private double translateXToVecFormat(Point point) {
        //(0,0) on panel is equivalent to (0,0) Vec co-ordinate
        //(w,h) on panel is equivalent to (1,1) Vec co-ordinate
        double x = 1.0 / (w + 1.0);
        double xcoord = x * point.getX();
        return xcoord;
    }

    private double translateYToVecFormat(Point point) {
        double y = 1.0 / (h + 1.0);
        double ycoord = y * point.getY();
        return ycoord;
    }

    private Point translateToDrawableFormat(double xcoord, double ycoord) {
        double x = 1.0 / (w + 1.0);
        double y = 1.0 / (h + 1.0);
        double xpoint = xcoord / x;
        double ypoint = ycoord / y;
        Point p = new Point((int)xpoint, (int)ypoint);
        return p;
    }

    public void addPlot(Point plot, Color colour) {
        ArrayList tempList = new ArrayList();
        Draw tempTool = new PlotDraw();
        tempList.add(tempTool);
        //tempList.add(plot);
        double xcoord = translateXToVecFormat(plot);
        double ycoord = translateYToVecFormat(plot);
        tempList.add(xcoord);
        tempList.add(ycoord);
        tempList.add(colour);
        listOfShapes.add(tempList);
        System.out.println("listofshapes" + listOfShapes);
        repaint();
    }
    public void addLine(Point s, Point e, Color colour) {
        ArrayList tempList = new ArrayList();
        Draw tempTool = new LineDraw();
        tempList.add(tempTool);
        tempList.add(s);
        tempList.add(e);
        tempList.add(colour);
        listOfShapes.add(tempList);
        repaint();
    }
    public void addRectangle(Point s, Point e, Color colour, Color fill) {
        ArrayList tempList = new ArrayList();
        Draw tempTool = new RectangleDraw();
        tempList.add(tempTool);
        tempList.add(s);
        tempList.add(e);
        tempList.add(colour);
        tempList.add(fill);
        listOfShapes.add(tempList);
        repaint();
    }
    public void addEllipse(Point s, Point e, Color colour, Color fill) {
        ArrayList tempList = new ArrayList();
        Draw tempTool = new EllipseDraw();
        tempList.add(tempTool);
        tempList.add(s);
        tempList.add(e);
        tempList.add(colour);
        tempList.add(fill);
        listOfShapes.add(tempList);
        repaint();
    }
    public void addPolygon(ArrayList<Point> l, Color colour, Color fill) {
        ArrayList tempList = new ArrayList();
        Draw tempTool = new PolygonDraw();
        tempList.add(tempTool);
        for (Point p: l) {
            tempList.add(p);
        }
        tempList.add(colour);
        tempList.add(fill);
        listOfShapes.add(tempList);
        repaint();
        list.clear();
    }

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
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){
        startPoint = e.getPoint();
    }
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
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
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}

    public void componentResized(ComponentEvent e) {
        Dimension d = this.getSize();
        w = (double) d.getWidth();
        h = (double) d.getHeight();
        repaint();
    }

    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
}
