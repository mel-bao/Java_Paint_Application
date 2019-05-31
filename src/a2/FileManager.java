package a2;

import Draw.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Class to Manage Saving or Loading File or ArrayList Formats</h1>
 * This class provides methods to iterate through either a file or ArrayList of shapes to draw
 * and return these formatted into the relevant variable type.
 *
 * @author Melanie Howard
 * @version 1.0
 */
public class FileManager {

    /**
     * This method takes an ArrayList of shapes drawn on the CanvasPanel and iterates through returning
     * a string of each command and it's VEC co-ordinates followed by a unix LF line break. The string
     * will then be written into a .VEC file.
     * @param listOfShapes this is the ArrayList<ArrayList> of shapes used to draw in CanvasPanel.
     * @return String of commands in VEC format.
     */
    public String saveFile(ArrayList<ArrayList> listOfShapes) {
        String string = "";
        String pen = "";
        String fill = "";

        for (ArrayList draw: listOfShapes) {
            String shape;
            String coord = "";

            if (draw.get(0) instanceof PlotDraw) {
                //check for pen colour
                if (draw.get(draw.size()-1) != null) {
                    Color color = (Color)draw.get(draw.size()-1);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(pen)) {
                        pen = hex;
                        string += "PEN " + pen + "\n";
                    }
                }
                shape = "PLOT ";
                coord = draw.get(1) + " " + draw.get(2);
                string += shape + coord + "\n";
            }else if (draw.get(0) instanceof LineDraw) {
                //check for pen colour
                if (draw.get(draw.size()-1) != null) {
                    Color color = (Color)draw.get(draw.size()-1);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(pen)) {
                        pen = hex;
                        string += "PEN " + pen + "\n";
                    }
                }
                shape = "LINE ";
                coord = draw.get(1) + " " + draw.get(2) + " " + draw.get(3)+ " " + draw.get(4);
                string += shape + coord + "\n";
            } else if (draw.get(0) instanceof RectangleDraw) {
                //check for pen colour
                if (draw.get(draw.size()-2) != null) {
                    Color color = (Color)draw.get(draw.size()-2);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(pen)) {
                        pen = hex;
                        string += "PEN " + pen + "\n";
                    }
                }
                //check for fill colour
                if (draw.get(draw.size()-1) != null) {
                    Color color = (Color)draw.get(draw.size()-1);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(fill)) {
                        fill = hex;
                        string += "FILL " + fill + "\n";
                    }
                }
                shape = "RECTANGLE ";
                coord = draw.get(1) + " " + draw.get(2) + " " + draw.get(3)+ " " + draw.get(4);
                string += shape + coord + "\n";
            } else if (draw.get(0) instanceof EllipseDraw) {
                //check for pen colour
                if (draw.get(draw.size()-2) != null) {
                    Color color = (Color)draw.get(draw.size()-2);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(pen)) {
                        pen = hex;
                        string += "PEN " + pen + "\n";
                    }
                }
                //check for fill colour
                if (draw.get(draw.size()-1) != null) {
                    Color color = (Color)draw.get(draw.size()-1);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(fill)) {
                        fill = hex;
                        string += "FILL " + fill + "\n";
                    }
                }
                shape = "ELLIPSE ";
                coord = draw.get(1) + " " + draw.get(2) + " " + draw.get(3)+ " " + draw.get(4);
                string += shape + coord + "\n";
            } else if (draw.get(0) instanceof PolygonDraw) {
                //check for pen colour
                if (draw.get(draw.size()-2) != null) {
                    Color color = (Color)draw.get(draw.size()-2);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(pen)) {
                        pen = hex;
                        string += "PEN " + pen + "\n";
                    }
                }
                //check for fill colour
                if (draw.get(draw.size()-1) != null) {
                    Color color = (Color)draw.get(draw.size()-1);
                    String hex = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
                    if (!hex.equals(fill)) {
                        fill = hex;
                        string += "FILL " + fill + "\n";
                    }
                }
                shape = "POLYGON ";
                for (int i=1; i < (draw.size() - 2); i++) {
                    if (i == (draw.size() - 3)) {
                        coord += draw.get(i);
                    } else {
                        coord += draw.get(i) + " ";
                    }
                }
                string += shape + coord + "\n";
            }

        }

        return string;
    }

    /**
     * This method takes a file in VEC format and iterates through it, returning ArrayList of instances off Shapes
     * with co-ordinates and pen and fill colours in RGB format, which can be used by CanvasPanel to
     * draw these shapes.
     * @param file this a file .VEC chosen with a fileChooser.
     * @return ArrayList<ArrayList> of Shapes in the requisite format to be drawn in CanvasPanel.
     */
    public ArrayList<ArrayList> loadFile(File file) {
        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        ArrayList<String> strings = new ArrayList<String>();
        Color c = null;
        Color f = null;

        //split file into strings by line
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                strings.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (String string: strings) {
            ArrayList tempList = new ArrayList();
            //split each line into separate strings by space
            String[] split = string.split("\\s+");
            String tool = split[0];
            switch (tool) {
                case "PEN":
                    c = Color.decode(split[1]);
                    break;
                case "FILL":
                    if (split[1].equals("OFF")) {
                        f = null;
                    } else {
                        f = Color.decode(split[1]);
                    }
                    break;
                case "PLOT":
                    Draw toolP = new PlotDraw();
                    tempList.add(toolP);
                    for (int i=1; i < split.length; i++) {
                        double coord = Double.parseDouble(split[i]);
                        tempList.add(coord);
                    }
                    tempList.add(c);
                    list.add(tempList);
                    break;
                case "LINE":
                    Draw toolL = new LineDraw();
                    tempList.add(toolL);
                    for (int i=1; i < split.length; i++) {
                        double coord = Double.parseDouble(split[i]);
                        tempList.add(coord);
                    }
                    tempList.add(c);
                    list.add(tempList);
                    break;
                case "RECTANGLE":
                    Draw toolR = new RectangleDraw();
                    tempList.add(toolR);
                    for (int i=1; i < split.length; i++) {
                        double coord = Double.parseDouble(split[i]);
                        tempList.add(coord);
                    }
                    tempList.add(c);
                    tempList.add(f);
                    list.add(tempList);
                    break;
                case "ELLIPSE":
                    Draw toolE = new EllipseDraw();
                    tempList.add(toolE);
                    for (int i=1; i < split.length; i++) {
                        double coord = Double.parseDouble(split[i]);
                        tempList.add(coord);
                    }
                    tempList.add(c);
                    tempList.add(f);
                    list.add(tempList);
                    break;
                case "POLYGON":
                    Draw toolPg = new PolygonDraw();
                    tempList.add(toolPg);
                    for (int i=1; i < split.length; i++) {
                        double coord = Double.parseDouble(split[i]);
                        tempList.add(coord);
                    }
                    tempList.add(c);
                    tempList.add(f);
                    list.add(tempList);
            }
        }
        return list;
    }

}
