package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Draw.*;
import a2.FileManager;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1>Test FileManager class</h1>
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class FileManagerTests {

    /**
     * tests FileManager.saveFile
     */
    @Test
    void testSaveFile() {
        FileManager file = new FileManager();
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        ArrayList temp = new ArrayList();
        Draw tool = new PlotDraw();
        temp.add(tool);
        temp.add(0.5);
        temp.add(0.5);
        temp.add(Color.black);
        list.add(temp);

        String actualString = file.saveFile(list);
        String expectedString = "PEN #000000\nPLOT 0.5 0.5\n";
        assertEquals(actualString, expectedString);
    }

    /**
     * tests FileManager.loadFile
     */
    @Test
    void testLoadFile() {
        FileManager file = new FileManager();
        File testFile = new File("testFile");
        String fileString = "PEN #000000\nPLOT 0.5 0.5\n";
        try {
        FileWriter writer = new FileWriter("testFile");
        writer.write(fileString);
        writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        File ourFile = new File("testFile");
        System.out.println(ourFile.getAbsolutePath());
        ArrayList<ArrayList> actualList = file.loadFile(ourFile);

        ArrayList<ArrayList> expectedList = new ArrayList<ArrayList>();
        ArrayList temp = new ArrayList();
        Draw tool = new PlotDraw();
        temp.add(tool);
        temp.add(0.5);
        temp.add(0.5);
        temp.add(Color.black);
        expectedList.add(temp);

        assertEquals(actualList.get(0).get(1), expectedList.get(0).get(1));
        assertEquals(actualList.get(0).get(2), expectedList.get(0).get(2));
        assertEquals(actualList.get(0).get(3), expectedList.get(0).get(3));

        ourFile.delete();
    }
}
