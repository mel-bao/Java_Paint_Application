package a2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CanvasPanelTests {

    @BeforeEach
    public void setUp() {

    }

    @Test
    void testSetTool() {
        CanvasPanel canvas = new CanvasPanel();
        Draw expectedTool = new PlotDraw();
        canvas.setTool(expectedTool);
        Draw actualTool = canvas.getTool();
        assertEquals(expectedTool, actualTool);
    }

}
