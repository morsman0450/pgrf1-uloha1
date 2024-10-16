package rasterizer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster{

    private final BufferedImage image;
    private int color;

    public RasterBufferedImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void draw(RasterBufferedImage raster){
        Graphics2D g = image.createGraphics();

        g.setColor(new Color(color));
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.drawImage(raster.getImage(), 0, 0, null);
    }

    public void repaint(Graphics g){
        g.drawImage(image, 0, 0, null);

    }


    @Override
    public void clear() {
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(color));
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
    }

    @Override
    public void setClearColor(int color) {
        this.color = color;
    }

    @Override
    public int getPixel(int x, int y) {
        return image.getRGB(x, y);
    }

    @Override
    public void setPixel(int x, int y, int color) {
        image.setRGB(x, y, color);
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }
}
