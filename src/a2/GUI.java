package a2;

import javax.swing.*;
import java.awt.*;

class SquarePanel extends JPanel {

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
}

public class GUI extends JFrame {



    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("CAB302 Assignment 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());


        //Create the menu bar
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setOpaque(true);
        mainMenuBar.setBackground(Color.white);
        mainMenuBar.setPreferredSize(new Dimension(200, 40));
        JMenu menuFile = new JMenu("File");
        JMenu menuEdit = new JMenu("Edit");
        JMenuItem menuNew = new JMenuItem("New");
        JMenuItem menuSave = new JMenuItem("Save");
        JMenuItem menuLoad = new JMenuItem("Load");
        JMenuItem menuUndo = new JMenuItem("Undo");
        mainMenuBar.add(menuFile);
        mainMenuBar.add(menuEdit);
        menuFile.add(menuNew);
        menuFile.add(menuSave);
        menuFile.add(menuLoad);
        menuEdit.add(menuUndo);
        //add action listeners for menuItems
        menuNew.addActionListener(new MenuActionListener());
        menuSave.addActionListener(new MenuActionListener());
        menuLoad.addActionListener(new MenuActionListener());
        menuUndo.addActionListener(new MenuActionListener());

        //create canvas panel
        JPanel squarePanel = new JPanel(new GridBagLayout());
        squarePanel.setBackground(Color.RED);
        SquarePanel canvas = new SquarePanel();
        canvas.setBackground(Color.WHITE);
        squarePanel.add(canvas);

        //create tool panel
        JPanel toolPanel = new JPanel(new GridLayout(9, 1));
        toolPanel.setBackground(Color.GREEN);

        //add graphical tool buttons to toolpanel

        JButton plotButton = new JButton("PLOT");
        toolPanel.add(plotButton);
        JButton lineButton = new JButton("LINE");
        toolPanel.add(lineButton);
        JButton rectangleButton = new JButton("RECTANGLE");
        toolPanel.add(rectangleButton);
        JButton ellipseButton = new JButton("ELLIPSE");
        toolPanel.add(ellipseButton);
        JButton polygonButton = new JButton("POLYGON");
        toolPanel.add(polygonButton);
        JButton penColourButton = new JButton("PEN COLOUR");
        toolPanel.add(penColourButton);
        JButton fillButton = new JButton("FILL");
        toolPanel.add(fillButton);
        //add action listeners for tool buttons
        plotButton.addActionListener(new ToolButtonListener());
        lineButton.addActionListener(new ToolButtonListener());
        rectangleButton.addActionListener(new ToolButtonListener());
        ellipseButton.addActionListener(new ToolButtonListener());
        polygonButton.addActionListener(new ToolButtonListener());
        penColourButton.addActionListener(new ToolButtonListener());
        fillButton.addActionListener(new ToolButtonListener());

        //Add menuBar
        frame.setJMenuBar(mainMenuBar);
        //add toolPanel on left, canvas
        mainPanel.add(toolPanel, BorderLayout.WEST);
        mainPanel.add(squarePanel);
        frame.add(mainPanel);

        //Display the window.
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setLocation(new Point(200, 200));
        frame.pack();
        frame.setVisible(true);
    }
}
