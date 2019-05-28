package a2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class CanvasPanel extends JPanel implements MouseListener, MouseMotionListener {
    String tool;
    private Point startPoint = null;
    private Point endPoint = null;
    private java.util.ArrayList list = new java.util.ArrayList();

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

    public void setTool(String tool) {
        this.tool = tool;
        System.out.println("tool input: " + this.tool);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (startPoint != null) {
            g.setColor(Color.RED);
            g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        }
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked on canvas, tool: " + tool);
    }
    public void mouseEntered(MouseEvent e){
        //System.out.println("Mouse entered canvas");
    }
    public void mouseExited(MouseEvent e){
        //System.out.println("Mouse exited canvas");
    }
    public void mousePressed(MouseEvent e){
        startPoint = e.getPoint();
        System.out.println("Mouse pressed, start point: " + startPoint);
    }
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");
        endPoint = e.getPoint();
        //startPoint = null;
        if (tool == "line") {
            list.clear();
            list.add(startPoint);
            list.add(endPoint);
            repaint();
        }
    }
    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse moved");
        endPoint = e.getPoint();
    }
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse dragged");
        endPoint = e.getPoint();
        repaint();
    }
}
