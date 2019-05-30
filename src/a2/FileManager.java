package a2;

import java.util.ArrayList;

public class FileManager {

    public String SaveFile(ArrayList<ArrayList> listOfShapes) {
        String string = "";

        for (ArrayList draw: listOfShapes) {
            String shape;
            String pen = null;
            String fill = null;
            String coord = "";

            if (draw.get(0) instanceof PlotDraw) {
                shape = "PLOT ";
                coord = draw.get(1) + " " + draw.get(2);
                string += shape + coord + "\n";
            }else if (draw.get(0) instanceof LineDraw) {
                shape = "LINE ";
                coord = draw.get(1) + " " + draw.get(2) + " " + draw.get(3)+ " " + draw.get(4);
                string += shape + coord + "\n";
            } else if (draw.get(0) instanceof RectangleDraw) {
                shape = "RECTANGLE ";
                coord = draw.get(1) + " " + draw.get(2) + " " + draw.get(3)+ " " + draw.get(4);
                string += shape + coord + "\n";
            } else if (draw.get(0) instanceof EllipseDraw) {
                shape = "ELLIPSE ";
                coord = draw.get(1) + " " + draw.get(2) + " " + draw.get(3)+ " " + draw.get(4);
                string += shape + coord + "\n";
            } else if (draw.get(0) instanceof PolygonDraw) {
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

}
