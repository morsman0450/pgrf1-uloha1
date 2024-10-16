package rasterizer;

public interface Raster {

    void clear();

    void setClearColor(int color);

    int getPixel(int x, int y);

    void setPixel(int x, int y, int Color);

    int getWidth();

    int getHeight();
}
