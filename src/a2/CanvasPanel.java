package a2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener {
    private Draw tool;
    private Color colour;
    private Color fill;
    private Point startPoint = null;
    private Point endPoint = null;
    private ArrayList<Point> list = new ArrayList<Point>();
    private ArrayList<ArrayList> listOfShapes = new ArrayList<ArrayList>();


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

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public void setFillColour(Color colour) {
        this.fill = colour;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!listOfShapes.isEmpty()) {
            for (ArrayList shape: listOfShapes) {
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

    public void addPlot(Point plot, Color colour) {
        ArrayList tempList = new ArrayList();
        Draw tempTool = new PlotDraw();
        tempList.add(tempTool);
        tempList.add(plot);
        tempList.add(colour);
        listOfShapes.add(tempList);
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
}
