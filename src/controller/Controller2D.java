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
    private boolean isCtrlPressed = false;
    private LineRasterizer lineRasterizer;
    private LineRasterizerTrivial lineRasterizerTrivial;
    private FilledLineRasterizer filledLineRasterizer;
    private ThickLineRasterizer thickLineRasterizer;
    private Point startPoint = null;
    private Point currentPoint = null;
    private boolean drawing = false;

    private Polygon polygon;
    public PolygonRasterizer polygonRasterizer;
    private Map<Integer, Point[]> linesMap = new HashMap<>();
    private int lineCounter = 0;
    private int lineThickness = 30;

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

        filledLineRasterizer = new FilledLineRasterizer(raster);

        thickLineRasterizer = new ThickLineRasterizer(raster, 5,Color.blue);

        polygon = new Polygon();
        polygonRasterizer = new PolygonRasterizer(lineRasterizer);
    }

    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isShiftPressed) {
                    if (polygon.getSize() == 0) {
                        polygon.addPoint(new Point(e.getX(), e.getY()));
                    } else {
                        startPoint = polygon.GetPoint(polygon.getSize() - 1);
                    }
                } else {
                    startPoint = new Point(e.getX(), e.getY());
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isShiftPressed) {
                    polygon.addPoint(new Point(e.getX(), e.getY()));
                } else {
                    Point endPoint = new Point(e.getX(), e.getY());
                    linesMap.put(lineCounter++, new Point[]{startPoint, endPoint});



                }

                redrawAllLinesAndPolygons();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawing && startPoint != null && !isShiftPressed) {
                    currentPoint = new Point(e.getX(), e.getY());
                    panel.clear(0x000000);
                    redrawAllLinesAndPolygons();


                    lineRasterizerTrivial.drawLine(startPoint.getX(), startPoint.getY(), currentPoint.getX(), currentPoint.getY());
                    panel.repaint();
                } else if (isShiftPressed && startPoint != null) {
                    currentPoint = new Point(e.getX(), e.getY());
                    panel.clear(0x000000);

                    redrawAllLinesAndPolygons();

                    if (polygon.getSize() > 1) {
                        polygonRasterizer.rasterize(polygon);
                    }

                    Point lastPoint = polygon.GetPoint(polygon.getSize() - 1);
                    lineRasterizerTrivial.drawLine(lastPoint.getX(), lastPoint.getY(), currentPoint.getX(), currentPoint.getY());
                    panel.repaint();
                }

                if (isCtrlPressed && startPoint != null) {
                    currentPoint = snapToNearestLine(startPoint, new Point(e.getX(), e.getY()));

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
                }
                else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = true;
                }
                else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    isCtrlPressed = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = false;
                }
                else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    isCtrlPressed = false;
                }
            }
        });
    }

    private Point snapToNearestLine(Point start, Point current) {
        int dx = current.getX() - start.getX();
        int dy = current.getY() - start.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            if (Math.abs(dy) < Math.abs(dx) / 2) {
                return new Point(current.getX(), start.getY());
            } else {
                return new Point(current.getX(), start.getY() + dx);
            }
        } else {
            if (Math.abs(dx) < Math.abs(dy) / 2) {
                return new Point(start.getX(), current.getY());
            } else {
                return new Point(start.getX() + dy, current.getY());
            }
        }
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
