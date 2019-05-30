package a2;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

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

    public ArrayList<ArrayList> loadFile(File file) {
        ArrayList<ArrayList> list = null;

        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return list;
    }

}
