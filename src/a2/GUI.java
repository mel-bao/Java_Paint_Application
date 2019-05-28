package a2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;




public class GUI extends JFrame {

    CanvasPanel canvas;
    JPanel mainPanel;

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public GUI() {
        super("CAB302 Assignment 2");
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout());


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
        //
        KeyStroke keyStrokeToUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
        menuUndo.setAccelerator(keyStrokeToUndo);
        //add action listeners for menuItems
        menuNew.addActionListener(new MenuActionListener());
        menuSave.addActionListener(new MenuActionListener());
        menuLoad.addActionListener(new MenuActionListener());
        menuUndo.addActionListener(new MenuActionListener());

        //create canvas panel
        JPanel squarePanel = new JPanel(new GridBagLayout());
        squarePanel.setBackground(Color.LIGHT_GRAY);
        canvas = new CanvasPanel();
        //add mouse listener so canvas can handle mouse events
        canvas.addMouseListener(canvas);
        canvas.setBackground(Color.WHITE);
        squarePanel.add(canvas);


        //create tool panel
        JPanel toolPanel = new JPanel(new GridLayout(9, 1));
        toolPanel.setBackground(Color.GRAY);

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
        JButton penColourButton = new JButton("PEN");
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
        setJMenuBar(mainMenuBar);
        //add toolPanel on left, canvas
        mainPanel.add(toolPanel, BorderLayout.WEST);
        mainPanel.add(squarePanel);
        add(mainPanel);

        //Display the window.
        setPreferredSize(new Dimension(1000, 500));
        setLocation(new Point(200, 200));
        pack();
        setVisible(true);
    }

    private class ToolButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println("Selected: " + e.getActionCommand());
            if (e.getActionCommand() == "PLOT") {
                PlotDraw plot = new PlotDraw();
                canvas.setTool(plot);
            } else if (e.getActionCommand() == "RECTANGLE") {
                RectangleDraw rect = new RectangleDraw();
                canvas.setTool(rect);
            } else if (e.getActionCommand() == "LINE") {
                LineDraw line = new LineDraw();
                canvas.setTool(line);
            } else if (e.getActionCommand() == "ELLIPSE") {
                EllipseDraw elli = new EllipseDraw();
                canvas.setTool(elli);
            } else if (e.getActionCommand() == "POLYGON") {
                PolygonDraw poly = new PolygonDraw();
                canvas.setTool(poly);
            } else if (e.getActionCommand() == "PEN") {
                Color c = JColorChooser.showDialog(null, "Choose a Color", mainPanel.getForeground());
                if (c != null) {
                    canvas.setColour(c);
                }
            }
        }
    }
}
