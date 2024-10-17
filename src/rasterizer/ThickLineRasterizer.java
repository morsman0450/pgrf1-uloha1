package rasterizer;

import java.awt.Color;

public class ThickLineRasterizer extends LineRasterizer {
    private int thickness;
    private Color color;

    public ThickLineRasterizer(Raster raster, int thickness, Color color) {
        super(raster);
        this.thickness = thickness;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / steps;
        float yIncrement = (float) dy / steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            for (int j = -thickness / 2; j <= thickness / 2; j++) {
                raster.setPixel(Math.round(x), Math.round(y + j), color.getRGB());
            }
            x += xIncrement;
            y += yIncrement;
        }
    }
}
