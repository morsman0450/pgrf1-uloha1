package controller;

import model.Point;
import model.Polygon;
import model.fill.Filler;
import model.fill.SeedFill;
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
    private ThickLineRasterizer thickLineRasterizer;
    private String mode = "L";
    private Point startPoint = null;
    private Point currentPoint = null;
    private boolean drawing = false;
    private Polygon polygon;
    public PolygonRasterizer polygonRasterizer;
    private Map<Integer, Point[]> linesMap = new HashMap<>();
    private int lineCounter = 0;
    private int lineThickness = 30;
    private Filler filler;

    public Controller2D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRasterImage());
        initListeners(panel);
    }

    public void initObjects(Raster raster) {
        lineRasterizer = new LineRasterizerGraphics(raster);
        lineRasterizer.setColor(Color.YELLOW);
        lineRasterizerTrivial = new LineRasterizerTrivial(raster);
        lineRasterizerTrivial.setColor(Color.BLUE);
        thickLineRasterizer = new ThickLineRasterizer(raster, 5, Color.BLUE);
        polygon = new Polygon();
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);
    }

    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    filler = new SeedFill(panel.getRasterImage(), e.getX(), e.getY(), Color.GREEN.getRGB());
                    filler.fill();
                    panel.repaint();
                }
                else if (mode.equals("P") && !isShiftPressed) {
                    if (polygon.getSize() == 0) {
                        polygon.addPoint(new Point(e.getX(), e.getY()));
                    } else {
                        startPoint = polygon.getPoint(polygon.getSize() - 1);
                    }
                } else {
                    startPoint = new Point(e.getX(), e.getY());
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    return;
                }
                if (mode.equals("P") && !isShiftPressed) {
                    polygon.addPoint(new Point(e.getX(), e.getY()));
                } else {
                    Point endPoint = new Point(e.getX(), e.getY());
                    if (isShiftPressed) {
                        endPoint = LineSnapper.snapToNearestLine(startPoint, endPoint);
                    }
                    linesMap.put(lineCounter++, new Point[]{startPoint, endPoint});
                }
                redrawAllLinesAndPolygons();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawing && startPoint != null && !isShiftPressed && mode.equals("L")) {
                    currentPoint = new Point(e.getX(), e.getY());
                    panel.clear(0x000000);
                    redrawAllLinesAndPolygons();
                    lineRasterizerTrivial.drawLine(startPoint.getX(), startPoint.getY(), currentPoint.getX(), currentPoint.getY());
                    panel.repaint();
                } else if (mode.equals("P") && !isShiftPressed && startPoint != null) {
                    currentPoint = new Point(e.getX(), e.getY());
                    panel.clear(0x000000);
                    redrawAllLinesAndPolygons();
                    if (polygon.getSize() > 1) {
                        polygonRasterizer.rasterize(polygon);
                    }
                    Point lastPoint = polygon.getPoint(polygon.getSize() - 1);
                    lineRasterizerTrivial.drawLine(lastPoint.getX(), lastPoint.getY(), currentPoint.getX(), currentPoint.getY());
                    panel.repaint();
                }

                if (isShiftPressed && startPoint != null) {
                    currentPoint = LineSnapper.snapToNearestLine(startPoint, new Point(e.getX(), e.getY()));
                    panel.clear(0x000000);
                    redrawAllLinesAndPolygons();
                    lineRasterizerTrivial.drawLine(startPoint.getX(), startPoint.getY(), currentPoint.getX(), currentPoint.getY());
                    panel.repaint();
                }
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    panel.clear(0x000000);
                    polygon.deletePolygon();
                    linesMap.clear();
                    panel.repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = true;
                } else if (e.getKeyCode() == KeyEvent.VK_L) {
                    mode = "L";
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    mode = "P";
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = false;
                }
            }
        });
    }

    private void redrawAllLinesAndPolygons() {
        panel.clear(0x000000);
        for (Map.Entry<Integer, Point[]> entry : linesMap.entrySet()) {
            Point[] linePoints = entry.getValue();
            Point p1 = linePoints[0];
            Point p2 = linePoints[1];
            thickLineRasterizer.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

        if (polygon.getSize() > 1) {
            polygonRasterizer.rasterize(polygon);
        }
        panel.repaint();
    }
}

