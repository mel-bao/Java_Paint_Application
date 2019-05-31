package a2;

import javax.swing.*;

/**
 * <h1>Main Class</h1>
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class Main {

    /**
     * this is the main method which declares an instance of the GUI class inside the event-dispatching thread.
     * @param args Unused.
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's a2.GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            /**
             * create and show GUI in event-dispatching thread.
             */
            public void run () {
                JFrame.setDefaultLookAndFeelDecorated(true);
                GUI gui = new GUI();
            }
        });
    }
}
