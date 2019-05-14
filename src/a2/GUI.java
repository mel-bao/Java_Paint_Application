package a2;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Assignment2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        //create tool panel
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
        toolPanel.add(Box.createHorizontalGlue());
        toolPanel.setMaximumSize( new Dimension(  150, 500) );
        toolPanel.setBackground(Color.GREEN);
        toolPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //add buttons to toolpanel
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

        //create label
        JLabel label = new JLabel("Canvas goes here!");

        //Add components
        frame.setJMenuBar(mainMenuBar);
        //add toolpanel
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        frame.getContentPane().add(toolPanel);
        frame.getContentPane().add(label); //add component

        //Display the window.
        frame.setPreferredSize(new Dimension(1000, 500));
        frame.setLocation(new Point(200, 200));
        frame.pack();
        frame.setVisible(true);
    }
}
