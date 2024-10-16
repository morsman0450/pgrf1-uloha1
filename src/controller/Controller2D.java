package controller;

import model.Point;
import model.Polygon;
import rasterizer.*;
import view.Panel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class Controller2D {

    private final Panel panel;
    private boolean isShiftPressed = false;
    private LineRasterizer lineRasterizer;
    private LineRasterizerTrivial lineRasterizerTrivial;
    private Point startPoint = null;
    private Point currnetPoint = null;
    private boolean drawing = false;


    private Polygon polygon;
    public PolygonRasterizer polygonRasterizer;
    private Map<Integer, Point[]> linesMap = new HashMap<>();
    private int lineCounter = 0;

    public Controller2D(Panel panel) {
        this.panel = panel;

        initObjects(panel.getRasterImage());
        initListeners(panel);
        // init Listeners
    }

    public void initObjects(Raster raster){
        lineRasterizer = new LineRasterizerGraphics(raster);
        lineRasterizer.setColor(Color.YELLOW);

        lineRasterizerTrivial = new LineRasterizerTrivial(raster);
        lineRasterizerTrivial.setColor(Color.BLUE);

        polygon = new Polygon();
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);


        // ...
    }

    public void initListeners(Panel panel){
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(isShiftPressed){
                    panel.clear(0x000000);
                    polygon.addPoint(new Point(e.getX(), e.getY()));
                    polygonRasterizer.rasterize(polygon);

                    panel.repaint();
                }
                else{
                    startPoint = new Point(e.getX(), e.getY());
                    drawing = true;

                }

            }



            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point endPoint = new Point(e.getX(), e.getY());
                linesMap.put(lineCounter++, new Point[]{startPoint, endPoint});
                redrawAllLines();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e){
                if(drawing && startPoint != null){
                    currnetPoint = new Point(e.getX(),e.getY());
                    panel.clear(0x0000000);
                    redrawAllLines();
                    lineRasterizerTrivial.drawLine(startPoint.getX(),startPoint.getY(),currnetPoint.getX(),currnetPoint.getY());
                    panel.repaint();


                }
            }
        });


        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_C){
                    panel.clear(0x000000);
                    polygon.deletePolygon();
                    panel.repaint();
                }
                else if(e.getKeyCode() == KeyEvent.VK_SHIFT){
                    isShiftPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                isShiftPressed = false;
            }
        });



    }

    private void redrawAllLines() {
        panel.clear(0x000000);

        for (Map.Entry<Integer, Point[]> entry : linesMap.entrySet()) {
            Point[] linePoints = entry.getValue();
            Point p1 = linePoints[0];
            Point p2 = linePoints[1];
            lineRasterizerTrivial.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }
        panel.repaint();
    }
}
