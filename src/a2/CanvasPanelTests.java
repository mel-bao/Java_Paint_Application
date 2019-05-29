package a2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

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

    @Test
    void testGetTool() {
        CanvasPanel canvas = new CanvasPanel();
        Draw expectedTool = null;
        Draw actualTool = canvas.getTool();
        assertEquals(expectedTool, actualTool);
    }

    @Test
    void testSetColour() {
        CanvasPanel canvas = new CanvasPanel();
        Color expectedColour = Color.black;
        canvas.setColour(expectedColour);
        Color actualColour = canvas.getColour();
        assertEquals(expectedColour, actualColour);
    }

    @Test
    void testGetColour() {
        CanvasPanel canvas = new CanvasPanel();
        Color expectedColour = null;
        Color actualColour = canvas.getColour();
        assertEquals(expectedColour, actualColour);
    }

    @Test
    void testAddPlot() {
        CanvasPanel canvas = new CanvasPanel();
        Point point = new Point(200,200);
        Color colour = Color.black;
        canvas.addPlot(point, colour);
    }

}
