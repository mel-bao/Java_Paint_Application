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
    private Point pointStart = null;
    private Point pointEnd   = null;
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //continuous drawing on click and drag
        if (pointStart != null) {
            if (tool instanceof LineDraw) {
                g.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y);
            } else if (tool instanceof RectangleDraw) {
                //g.drawRect(pointStart.x, pointStart.y, pointStart.x - pointEnd.x, pointStart.y - pointEnd.y);
            } else if (tool instanceof EllipseDraw) {
                //g.drawOval(pointStart.x, pointStart.y, pointStart.x - pointEnd.x, pointStart.y - pointEnd.y);
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
        repaint();
    }

    public ArrayList<ArrayList> getListOfShapes() {
        return listOfShapes;
    }

    public void clearListOfShapes() {
        listOfShapes.clear();
        repaint();
    }

    public void undoLastCommand() {
        if (!listOfShapes.isEmpty()) {
            listOfShapes.remove(listOfShapes.size() - 1);
            repaint();
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
        pointStart = e.getPoint();
    }

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

    public void mouseMoved(MouseEvent e) {
        pointEnd = e.getPoint();
    }

    public void mouseDragged(MouseEvent e) {
        pointEnd = e.getPoint();
        repaint();
    }

    public void componentResized(ComponentEvent e) {
        Dimension d = this.getSize();
        w = d.getWidth();
        h = d.getHeight();
        repaint();
    }

    public void componentMoved(ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {}

    public void componentHidden(ComponentEvent e) {}
}
