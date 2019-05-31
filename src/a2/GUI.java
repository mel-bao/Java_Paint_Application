package a2;

import Draw.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * <h1>Create and Show GUI</h1>
 * The class GUI creates the GUI and shows it. ActionListeners are added to relevant GUI components.
 * For thread safety, an instance of this class should be invoked from the event-dispatching thread.
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class GUI extends JFrame {
    /**
     * The CanvasPanel that drawing is handled on.
     */
    private CanvasPanel canvas;
    /**
     * The main JPanel that contains the toolPanel and canvas.
     */
    private JPanel mainPanel;
    /**
     * JLabel included in toolPanel that is changed depending on selected tool.
     */
    private JLabel toolLabel;

    /**
     * Constructor for the GUI class. This method is used to set up the JFrame, add menu, canvas and
     * toolPanel created in other methods, as well as the mainPanel and display the window.
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

    /**
     *This method creates the menuBar, adds menuItems, adds their MenuActionListeners and maps keyEvents to them.
     * The menuBar is set in the JFrame.
     */
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

        //add keyevents for menu items
        KeyStroke keyStrokeToUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
        menuUndo.setAccelerator(keyStrokeToUndo);
        KeyStroke keyStrokeToSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        menuSave.setAccelerator(keyStrokeToSave);
        KeyStroke keyStrokeToLoad = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
        menuLoad.setAccelerator(keyStrokeToLoad);
        KeyStroke keyStrokeToNew = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        menuNew.setAccelerator(keyStrokeToNew);
        //add action listeners for menuItems
        menuNew.addActionListener(new MenuActionListener());
        menuSave.addActionListener(new MenuActionListener());
        menuLoad.addActionListener(new MenuActionListener());
        menuUndo.addActionListener(new MenuActionListener());

        //Add menuBar
        setJMenuBar(mainMenuBar);
    }

    /**
     * This method sets the canvas instance of CanvasPanel, adds mouseListeners, mouseMotionListeners,
     * and adds it to the mainPanel
     */
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

    /**
     * This method creates the toolPanel and toolButtons, to which it adds ToolButtonListeners.
     * The toolPanel is added to the mainPanel.
     */
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

    /**
     * <h2>ActionListener Class for ToolButtons</h2>
     * This class calls the CanvasPanel tool setter method and sets the tool in use depending on
     * which toolButton is pressed.
     *
     * @author Melanie Howard
     * @version 1.0
     */
    private class ToolButtonListener implements ActionListener {

        /**
         * theis method sets canvas tool depending on which toolButton selected.
         * @param e this is the actionCommand received from button press.
         */
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            System.out.println("Selected: " + cmd);
            switch (cmd) {
                case "PLOT":
                    toolLabel.setText("");
                    PlotDraw plot = new PlotDraw();
                    canvas.setTool(plot);
                    break;
                case "LINE":
                    toolLabel.setText("");
                    LineDraw line = new LineDraw();
                    canvas.setTool(line);
                    break;
                case "RECTANGLE":
                    toolLabel.setText("");
                    RectangleDraw rect = new RectangleDraw();
                    canvas.setTool(rect);
                    break;
                case "ELLIPSE":
                    toolLabel.setText("");
                    EllipseDraw elli = new EllipseDraw();
                    canvas.setTool(elli);
                    break;
                case "POLYGON":
                    toolLabel.setText("right click to end");
                    PolygonDraw poly = new PolygonDraw();
                    canvas.setTool(poly);
                    break;
                case "PEN":
                    toolLabel.setText("");
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
                    toolLabel.setText("");
                    break;
            }
        }
    }

    /**
     * <h2>ActionListener Class for MenuItems</h2>
     * This class calls the relevant FileManager or CanvasPanel method depending on
     * which menuItem is selected.
     *
     * @author Melanie Howard
     * @version 1.0
     */
    private class MenuActionListener implements ActionListener {
        /**
         * This method open s aJFileChooser if Load or Save is selected and calls a Filemanager method to put
         * either the file or ArrayList in the correct format, then saves the file or sets it to be drawn
         * on the canvas. If New or Undo is selected the relevant CanvasPanel method is called.
         * @param e this is the actionCommand received when a menuItem is selected.
         */
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            FileManager file = new FileManager();
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
