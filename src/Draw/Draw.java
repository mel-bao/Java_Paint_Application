package Draw;

import java.awt.*;
import java.util.List;

/**
 * <h1>Abstract Class for Drawing Shapes</h1>
 * This class declares and abstract draw method and a thread sleep method.
 *
 * @author Melanie Howard
 * @version 1.0
 */
public abstract class Draw {
    /**
     * period of time to pause thread for in milliseconds.
     */
    public static final int PAUSE = 1;

    /**
     * abstract method draw()
     @return void
     */
    public abstract void draw(List list, Graphics g);

    /**
     * pause thread after drawing and catch thread interrupted exceptions.
     * @param time class variable PAUSE.
     */
    public void doNothing(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            System.out.println("Unexpected Interrupt");
            System.exit(0);
        }
    }
}
