package rasterizer;

public class FilledLineRasterizer extends LineRasterizer{
    public FilledLineRasterizer(Raster raster) {
        super(raster);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int sx = (dx < 0) ? -1 : 1;
        int sy = (dy < 0) ? -1 : 1;
        dx = Math.abs(dx);
        dy = Math.abs(dy);

        if (dx > dy) {
            int err = dx / 2;
            while (x1 != x2) {
                raster.setPixel(x1, y1, 0xFF0F0F);
                err -= dy;
                if (err < 0) {
                    y1 += sy;
                    err += dx;
                }
                x1 += sx;
            }
        } else {
            int err = dy / 2;
            while (y1 != y2) {
                raster.setPixel(x1, y1, 0xFF0F0F);
                err -= dx;
                if (err < 0) {
                    x1 += sx;
                    err += dy;
                }
                y1 += sy;
            }
        }
        raster.setPixel(x2, y2, 0xFF0F0F);
    }
}
