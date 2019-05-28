package a2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener {
    private Draw tool;
    private Point startPoint = null;
    private Point endPoint = null;
    private ArrayList<ArrayList<Point>> listOfPlots = new ArrayList<ArrayList<Point>>();
    private ArrayList<ArrayList<Point>> listOfLines = new ArrayList<ArrayList<Point>>();


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
    }

    public void addPlot(Point plot) {
        ArrayList<Point> tempList = new ArrayList<Point>();
        tempList.add(plot);
        listOfPlots.add(tempList);
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        if(tool instanceof PlotDraw) {
            //list.clear();
            addPlot(startPoint);
        } else if (tool instanceof PolygonDraw) {
            //if left click record polygon point
            if (e.getButton() == MouseEvent.BUTTON1) {
                System.out.println("left click");
                //list.add(e.getPoint());
            } // if right click add last point and paint
            else if (e.getButton() == MouseEvent.BUTTON3) {
                System.out.println("right click");
                //list.add(e.getPoint());
                repaint();
            }
        }
    }
    public void mouseEntered(MouseEvent e){
        //System.out.println("Mouse entered canvas");
    }
    public void mouseExited(MouseEvent e){
        //System.out.println("Mouse exited canvas");
    }
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
        //startPoint = null;
        if (tool instanceof LineDraw) {
            //list.clear();
            //list.add(startPoint);
            //list.add(endPoint);
            //the following can be implemented in its own method for extensibility??
            //listOfLines.add(list);
            repaint();
        } else if (tool instanceof RectangleDraw) {
            //list.clear();
            //list.add(startPoint);
            //list.add(endPoint);
            repaint();
        } else if (tool instanceof EllipseDraw) {
            //list.clear();
            //list.add(startPoint);
            //list.add(endPoint);
            repaint();
        }
    }
    public void mouseMoved(MouseEvent e) {
        endPoint = e.getPoint();
    }
    public void mouseDragged(MouseEvent e) {
        endPoint = e.getPoint();
        repaint();
    }
}
