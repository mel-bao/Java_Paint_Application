package a2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class CanvasPanel extends JPanel implements MouseListener {
    //String tool;
    private Point startPoint = null;
    private Point endPoint = null;

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

    /*public void setTool(String tool)
    {
        this.tool = tool;
    }*/

    public void mouseClicked(MouseEvent e) {
        System.out.println("Mouse clicked on canvas");
    }
    public void mouseEntered(MouseEvent e){
        System.out.println("Mouse entered canvas");
    }
    public void mouseExited(MouseEvent e){
        System.out.println("Mouse exited canvas");
    }
    public void mousePressed(MouseEvent e){
        startPoint = e.getPoint();
        System.out.println("Mouse pressed, start point: " + startPoint);
    }
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released, end point: " + endPoint);
        endPoint = e.getPoint();
    }
}
