import javax.swing.*;
import java.awt.*;

public class GUI {

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Assignment2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the label.
        JLabel label = new JLabel("The First Step...");
        frame.getContentPane().add(label); // PREFERRED WAY TO ADD COMPONENTS
//      frame.add(label);

        //Display the window.
        frame.setPreferredSize(new Dimension(300, 100));
        frame.setLocation(new Point(200, 200));
        frame.pack();
        frame.setVisible(true);
    }
}
