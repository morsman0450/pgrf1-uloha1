package model.fill;

import rasterizer.Raster;

public class SeedFill implements Filler {

    private Raster raster;
    private int x, y;
    private int backgroundColor, fillColor;

    public SeedFill(Raster raster, int x, int y, int fillColor) {
        this.raster = raster;
        this.x = x;
        this.y = y;
        this.backgroundColor = raster.getPixel(x, y);
        this.fillColor = fillColor;
    }

    @Override
    public void fill() {
        seedFill(x, y);
    }

    /**
     * @param x souradnice x pro vyplnovani
     * @param y souradnice y pro vyplnovani
     */
    private void seedFill(int x, int y) {

        int pixelColor = raster.getPixel(x, y);

        // TODO:
        // dokoncit kontrolu hranice obrazovky
        if (x < 0 && x >= raster.getWidth() && y < 0 && y >= raster.getHeight())
            return;
        if (pixelColor != backgroundColor)
            return;

        raster.setPixel(x, y, fillColor);

        seedFill(x + 1, y);
        seedFill(x - 1, y);
        seedFill(x, y + 1);
        seedFill(x, y - 1);
    }

}

