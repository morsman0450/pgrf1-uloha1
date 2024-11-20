package rasterizer;

import model.Line;
import model.Point;

import java.awt.*;
import java.util.ArrayList;

public abstract class LineRasterizer {

    Raster raster;
    Color color;

    public LineRasterizer(Raster raster) {
        this.raster = raster;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void rasterize(int x1, int y1, int x2, int y2) {
        drawLine(x1, y1, x2, y2);
    }

    public void rasterize(Line line) {
        drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    protected void drawLine(int x1, int y1, int x2, int y2) {

    }
    public ArrayList<Point> getLinePoints(Line line) {
        ArrayList<Point> points = new ArrayList<>();
        int x1 = line.getX1();
        int y1 = line.getY1();
        int x2 = line.getX2();
        int y2 = line.getY2();
        if (x1 > x2) {
            int tempX = x1;
            int tempY = y1;
            x1 = x2;
            y1 = y2;
            x2 = tempX;
            y2 = tempY;
        }

        float k = (float) (y2 - y1) / (x2 - x1);
        float q = y1 - k * x1;

        if (x1 == x2) {

            int startY = Math.min(y1, y2);
            int endY = Math.max(y1, y2);
            for (int y = startY; y <= endY; y++) {
                points.add(new Point(x1, y));
            }
        }
        else {
            if (Math.abs(k) > 1) {

                int startY = Math.min(y1, y2);
                int endY = Math.max(y1, y2);
                for (int y = startY; y <= endY; y++) {
                    int x = Math.round((y - q) / k);
                    points.add(new Point(x, y));
                }
            } else {
                for (int x = x1; x <= x2; x++) {
                    int y = Math.round(k * x + q);
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

}
