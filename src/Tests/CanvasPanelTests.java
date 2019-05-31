package Tests;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import Draw.*;
import a2.CanvasPanel;
import org.junit.jupiter.api.*;

/**
 * <h1>Test CanvasPanel Class</h1>
 *
 * @author Melanie Howard
 * @version 1.0
 */
class CanvasPanelTests {

    /**
     * Tests CanvasPanel.setTool
     */
    @Test
    void testSetTool() {
        CanvasPanel canvas = new CanvasPanel();
        Draw expectedTool = new PlotDraw();
        canvas.setTool(expectedTool);
        Draw actualTool = canvas.getTool();
        assertEquals(expectedTool, actualTool);
    }

    /**
     * Tests CanvasPanel.getTool
     */
    @Test
    void testGetTool() {
        CanvasPanel canvas = new CanvasPanel();
        Draw expectedTool = null;
        Draw actualTool = canvas.getTool();
        assertEquals(expectedTool, actualTool);
    }

    /**
     * Tests CanvasPanel.setColour
     */
    @Test
    void testSetColour() {
        CanvasPanel canvas = new CanvasPanel();
        Color expectedColour = Color.black;
        canvas.setColour(expectedColour);
        Color actualColour = canvas.getColour();
        assertEquals(expectedColour, actualColour);
    }

    /**
     * Tests CanvasPanel.getColour
     */
    @Test
    void testGetColour() {
        CanvasPanel canvas = new CanvasPanel();
        Color expectedColour = null;
        Color actualColour = canvas.getColour();
        assertEquals(expectedColour, actualColour);
    }

    /**
     * Tests CanvasPanel.setFillColour
     */
    @Test
    void testSetFillColour() {
        CanvasPanel canvas = new CanvasPanel();
        Color expectedColour = Color.black;
        canvas.setFillColour(expectedColour);
        Color actualColour = canvas.getFillColour();
        assertEquals(expectedColour, actualColour);
    }

    /**
     * Tests CanvasPanel.getFillColour
     */
    @Test
    void testGetFillColour() {
        CanvasPanel canvas = new CanvasPanel();
        Color expectedColour = null;
        Color actualColour = canvas.getFillColour();
        assertEquals(expectedColour, actualColour);
    }

    /**
     * Tests CanvasPanel.setListOfShapes
     */
    @Test
    void testSetListOfShapes() {
        CanvasPanel canvas = new CanvasPanel();
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        ArrayList temp = new ArrayList();
        Draw tool = new PlotDraw();
        temp.add(tool);
        temp.add(0.5);
        temp.add(0.5);
        temp.add(Color.blue);
        list.add(temp);

        canvas.setListOfShapes(list);
        ArrayList<ArrayList> actualList = canvas.getListOfShapes();
        assertEquals(list, actualList);
    }

    /**
     * Tests CanvasPanel.getListOfShapes
     */
    @Test
    void testGetListOfShapes() {
        CanvasPanel canvas = new CanvasPanel();
        ArrayList<ArrayList> expectedList = new ArrayList<ArrayList>();
        ArrayList<ArrayList> actualList = canvas.getListOfShapes();
        assertEquals(expectedList, actualList);
    }

    /**
     * Tests CanvasPanel.clearListOfShapes
     */
    @Test
    void testClearListOfShapes() {
        CanvasPanel canvas = new CanvasPanel();
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        ArrayList temp = new ArrayList();
        Draw tool = new PlotDraw();
        temp.add(tool);
        temp.add(0.5);
        temp.add(0.5);
        temp.add(Color.blue);
        list.add(temp);
        canvas.setListOfShapes(list);
        ArrayList<ArrayList> expectedList = new ArrayList<ArrayList>();

        canvas.clearListOfShapes();
        ArrayList<ArrayList> actualList = canvas.getListOfShapes();
        assertEquals(expectedList, actualList);
    }

    /**
     * Tests CanvasPanel.undoLastCommand
     */
    @Test
    void testUndoLastCommand() {
        CanvasPanel canvas = new CanvasPanel();
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        ArrayList temp = new ArrayList();
        Draw tool = new PlotDraw();
        temp.add(tool);
        temp.add(0.5);
        temp.add(0.5);
        temp.add(Color.blue);
        list.add(temp);
        ArrayList<ArrayList> expectedList = new ArrayList<ArrayList>();
        expectedList.add(temp);
        temp.clear();
        temp.add(tool);
        temp.add(0.5);
        temp.add(0.5);
        temp.add(Color.blue);
        list.add(temp);
        canvas.setListOfShapes(list);
        canvas.undoLastCommand();
        ArrayList<ArrayList> actualList = canvas.getListOfShapes();
        assertEquals(expectedList, actualList);
    }
}
