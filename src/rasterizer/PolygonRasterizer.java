package rasterizer;

import model.Line;
import model.Point;
import model.Polygon;

import java.awt.*;

public class PolygonRasterizer {
    private LineRasterizer lineRasterizer;
    private Color outlineColor; // Nová proměnná pro barvu obrysů

    public PolygonRasterizer(LineRasterizer lineRasterizer, Color outlineColor) {
        this.lineRasterizer = lineRasterizer;
        this.outlineColor = outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public void rasterize(Polygon polygon) {
        if (polygon.getSize() < 3) {
            return;
        }
        lineRasterizer.setColor(outlineColor);

        for (int i = 0; i < polygon.getSize(); i++) {
            int indexA = i;
            int indexB = i + 1;

            if (indexB == polygon.getSize()) {
                indexB = 0;
            }

            Point A = polygon.getPoint(indexA);
            Point B = polygon.getPoint(indexB);

            lineRasterizer.rasterize(new Line(A, B));
        }
    }

    public void setLineRasterizer(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }
}

