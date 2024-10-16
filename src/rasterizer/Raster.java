package rasterizer;

public interface Raster {

    // setPixel, getPixel, clear, setClearColor, getWidth, getHeight


    void clear();

    void setClearColor(int color);

    int getPixel(int x, int y);

    void setPixel(int x, int y, int Color);

    int getWidth();

    int getHeight();
}
