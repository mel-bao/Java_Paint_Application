package Draw;

import java.awt.*;
import java.util.List;

public abstract class Draw {
    public static final int PAUSE = 1;

    /**abstract method draw()
     @return void
     */
    public abstract void draw(List list, Graphics g);

    public void doNothing(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ie) {
            System.out.println("Unexpected Interrupt");
            System.exit(0);
        }
    }
}
