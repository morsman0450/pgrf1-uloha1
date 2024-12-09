package view;

import rasterizer.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private RasterBufferedImage rasterImage;
    public static final int WIDTH = 1000, HEIGHT = 1000;

    public Panel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        rasterImage = new RasterBufferedImage(WIDTH, HEIGHT);
        rasterImage.setClearColor(Color.BLACK.getRGB());
        setFocusable(true);
        requestFocus();
        rasterImage.clear();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        rasterImage.repaint(g);
    }

    public  void clear(){
        rasterImage.clear();
    }

    public RasterBufferedImage getRasterImage() {
        return rasterImage;
    }
}
