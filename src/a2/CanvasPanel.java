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
    private Point startPoint = null;
    private Point endPoint = null;
    private ArrayList<Point> list = new ArrayList<Point>();
    private ArrayList<ArrayList> listOfPlots = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> listOfLines = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> listOfRectangles = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> listOfEllipses = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> listOfPolygons = new ArrayList<ArrayList>();


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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!listOfPlots.isEmpty()) {
            Draw tempTool = new PlotDraw();
            for (ArrayList plot: listOfPlots) {
                tempTool.draw(plot, g);
            }
        }
        if (!listOfLines.isEmpty()) {
            Draw tempTool = new LineDraw();
            for (ArrayList line: listOfLines) {
                tempTool.draw(line, g);
            }
        }
        if (!listOfRectangles.isEmpty()) {
            Draw tempTool = new RectangleDraw();
            for (ArrayList rectangle: listOfRectangles) {
                tempTool.draw(rectangle, g);
            }
        }
        if (!listOfEllipses.isEmpty()) {
            Draw tempTool = new EllipseDraw();
            for (ArrayList ellipse: listOfEllipses) {
                tempTool.draw(ellipse, g);
            }
        }
        if (!listOfPolygons.isEmpty()) {
            Draw tempTool = new PolygonDraw();
            for (ArrayList polygon: listOfPolygons) {
                tempTool.draw(polygon, g);
            }
        }
    }

    public void addPlot(Point plot, Color colour) {
        ArrayList tempList = new ArrayList();
        tempList.add(plot);
        tempList.add(colour);
        listOfPlots.add(tempList);
        repaint();
    }
    public void addLine(Point s, Point e, Color colour) {
        ArrayList tempList = new ArrayList();
        tempList.add(s);
        tempList.add(e);
        tempList.add(colour);
        listOfLines.add(tempList);
        System.out.println(tempList);
        repaint();
    }
    public void addRectangle(Point s, Point e, Color colour) {
        ArrayList tempList = new ArrayList();
        tempList.add(s);
        tempList.add(e);
        tempList.add(colour);
        listOfRectangles.add(tempList);
        repaint();
    }
    public void addEllipse(Point s, Point e, Color colour) {
        ArrayList tempList = new ArrayList();
        tempList.add(s);
        tempList.add(e);
        tempList.add(colour);
        listOfEllipses.add(tempList);
        repaint();
    }
    public void addPolygon(ArrayList<Point> l, Color colour) {
        ArrayList tempList = new ArrayList();
        for (Point p: l) {
            tempList.add(p);
        }
        tempList.add(colour);
        listOfPolygons.add(tempList);
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
                addPolygon(list, colour);
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
        if(endPoint.getX() < startPoint.getX()) {
            Point temp = endPoint;
            endPoint = startPoint;
            startPoint = temp;
        }
        if(endPoint.getY() < startPoint.getY()) {
            int temp = (int)endPoint.getY();
            endPoint.y = (int)startPoint.getY();
            startPoint.y = temp;
        }

        if (tool instanceof LineDraw) {
            addLine(startPoint, endPoint, colour);
        } else if (tool instanceof RectangleDraw) {
            addRectangle(startPoint, endPoint, colour);
        } else if (tool instanceof EllipseDraw) {
            addEllipse(startPoint, endPoint, colour);
        }
    }
    public void mouseMoved(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
}
