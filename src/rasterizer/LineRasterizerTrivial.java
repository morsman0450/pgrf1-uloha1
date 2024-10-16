package rasterizer;

import model.Point;

import java.util.ArrayList;

public class LineRasterizerTrivial extends LineRasterizer {
    public LineRasterizerTrivial(Raster raster) {
        super(raster);
    }
    private ArrayList<Point> points;

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        float k = (float) (y2 - y1) / (x2 - x1);
        float q = y1 - k * x1;

        if (x1 == x2) {

            int startY = Math.min(y1, y2);
            int endY = Math.max(y1, y2);
            for (int y = startY; y <= endY; y++) {
                raster.setPixel(x1, y, 0xFF0F0F);
            }
        }
        else {
            if (Math.abs(k) > 1) {

                int startY = Math.min(y1, y2);
                int endY = Math.max(y1, y2);
                for (int y = startY; y <= endY; y++) {
                    int x = Math.round((y - q) / k);
                    raster.setPixel(x, y, 0xFF0F0F);
                }
            } else {
                for (int x = x1; x <= x2; x++) {
                    int y = Math.round(k * x + q);
                    raster.setPixel(x, y, 0xFF0F0F);
                }
            }
        }



        // raster.setPixel(x, y, color)
    }
}
