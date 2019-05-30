package a2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class GUI extends JFrame {

    private CanvasPanel canvas;
    private JPanel mainPanel;
    private FileManager file = new FileManager();
    private JLabel toolLabel;

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public GUI() {
        super("CAB302 Assignment 2");

        //Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout());

        createMenu();
        setupCanvas();
        createToolPanel();

        add(mainPanel);

        //Display the window.
        setPreferredSize(new Dimension(1000, 500));
        setLocation(new Point(200, 200));
        pack();
        setVisible(true);
    }

    private void createMenu() {
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

        //add keyevent for undo menu item
        KeyStroke keyStrokeToUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
        menuUndo.setAccelerator(keyStrokeToUndo);
        //add action listeners for menuItems
        menuNew.addActionListener(new MenuActionListener());
        menuSave.addActionListener(new MenuActionListener());
        menuLoad.addActionListener(new MenuActionListener());
        menuUndo.addActionListener(new MenuActionListener());

        //Add menuBar
        setJMenuBar(mainMenuBar);
    }

    private void setupCanvas() {
        //create canvas panel
        JPanel squarePanel = new JPanel(new GridBagLayout());
        squarePanel.setBackground(Color.LIGHT_GRAY);
        canvas = new CanvasPanel();
        //add mouse listener so canvas can handle mouse events
        canvas.addMouseListener(canvas);
        //add mouse motion listener for continuous drawing
        canvas.addMouseMotionListener(canvas);
        //add component listener for resizing
        canvas.addComponentListener(canvas);

        canvas.setBackground(Color.WHITE);
        squarePanel.add(canvas);
        mainPanel.add(squarePanel);
    }

    private void createToolPanel() {
        //create tool panel
        JPanel toolPanel = new JPanel(new GridLayout(10, 1));
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

        //create toolPanel label
        toolLabel = new JLabel();
        toolPanel.add(toolLabel);

        //add toolPanel on left
        mainPanel.add(toolPanel, BorderLayout.WEST);
    }

    private class ToolButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            System.out.println("Selected: " + cmd);
            switch (cmd) {
                case "PLOT":
                    PlotDraw plot = new PlotDraw();
                    canvas.setTool(plot);
                    break;
                case "LINE":
                    LineDraw line = new LineDraw();
                    canvas.setTool(line);
                    break;
                case "RECTANGLE":
                    RectangleDraw rect = new RectangleDraw();
                    canvas.setTool(rect);
                    break;
                case "ELLIPSE":
                    EllipseDraw elli = new EllipseDraw();
                    canvas.setTool(elli);
                    break;
                case "POLYGON":
                    toolLabel.setText("right click to end");
                    PolygonDraw poly = new PolygonDraw();
                    canvas.setTool(poly);
                    break;
                case "PEN":
                    Color c = JColorChooser.showDialog(null, "Choose a Pen Colour", Color.black);
                    canvas.setColour(c);
                    break;
                case "FILL":
                    toolLabel.setText("reset to turn off");
                    Color initf = new Color(0, 0, 0, 0);
                    Color f = JColorChooser.showDialog(null, "Choose a Fill Colour", initf);
                    if (f != initf) {
                        canvas.setFillColour(f);
                    } else {
                        canvas.setFillColour(null);
                    }
                    break;
            }
        }
    }

    private class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "Load":
                    System.out.println("load selected");
                    // create an object of JFileChooser class
                    JFileChooser lchooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    lchooser.setDialogTitle("Select a file to Load");
                    lchooser.setAcceptAllFileFilterUsed(false);
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("VEC files", "vec");
                    lchooser.addChoosableFileFilter(filter);
                    if (lchooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
                        //get loaded file into listOfShapes format
                        File selectedFile = lchooser.getSelectedFile();
                        ArrayList<ArrayList> listOfShapes = file.loadFile(selectedFile);
                        canvas.setListOfShapes(listOfShapes);
                    } else {
                        System.out.println("the user cancelled the operation");
                    }
                    break;
                case "Save":
                    System.out.println("save selected");
                    // create an object of JFileChooser class
                    JFileChooser schooser = new JFileChooser();
                    schooser.setCurrentDirectory(new File("/home/me/Desktop"));
                    schooser.setDialogTitle("Save File");
                    if (schooser.showSaveDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
                        try(FileWriter writer = new FileWriter(schooser.getSelectedFile()+".vec")) {
                            //get listOfShapes as string/VEC file
                            ArrayList<ArrayList> listOfShapes = canvas.getListOfShapes();
                            String fileString = file.saveFile(listOfShapes);
                            writer.write(fileString);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("the user cancelled the operation");
                    }
                    break;
                case "New":
                    canvas.clearListOfShapes();
                    break;
                case "Undo":
                    canvas.undoLastCommand();
                    break;
            }
        }
    }
}
