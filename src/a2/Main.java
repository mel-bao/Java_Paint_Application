package a2;

public class Main {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's a2.GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                GUI gui = new GUI();
            }
        });
    }
}
