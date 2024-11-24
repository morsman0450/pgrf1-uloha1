package controller;

import model.Point;
import model.Polygon;
import model.RegularPentagon;
import model.fill.ScanLine;
import model.fill.Filler;
import model.cut.Cutter;
import model.fill.SeedFill;
import rasterizer.*;
import view.Panel;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<Polygon> polygons = new ArrayList<>();
    private Polygon currentPolygon;
    private PolygonRasterizer polygonRasterizer;
    private Map<Integer, Point[]> linesMap = new HashMap<>();
    private int lineCounter = 0;
    private Filler filler;
    private Cutter cutter;
    private Polygon cuttingPolygon = new Polygon();
    private Polygon pointsToCut = new Polygon();
    private boolean cuttingMode = false;

    public Controller2D(Panel panel) {
        this.panel = panel;
        initObjects(panel.getRasterImage());
        initListeners(panel);
    }

    public void initObjects(Raster raster) {
        lineRasterizer = new LineRasterizerGraphics(raster);
        lineRasterizer.setColor(Color.BLUE);
        lineRasterizerTrivial = new LineRasterizerTrivial(raster);
        lineRasterizerTrivial.setColor(Color.BLUE);
        thickLineRasterizer = new ThickLineRasterizer(raster, 5, Color.BLUE);
        currentPolygon = new Polygon();
        polygons.add(currentPolygon);
        polygonRasterizer = new PolygonRasterizer(lineRasterizer, Color.BLUE);
        cutter = new Cutter(lineRasterizer, raster);
    }

    public void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (cuttingMode) {
                    cuttingPolygon.addPoint(new Point(e.getX(), e.getY()));
                    redrawAllLinesAndPolygons();
                }else if(mode.equals("G")){
                    startPoint = new Point(e.getX(),e.getY());
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    filler = new SeedFill(panel.getRasterImage(), e.getX(), e.getY(), Color.GREEN.getRGB());
                    filler.fill();
                    panel.repaint();
                } else if (mode.equals("P") && !isShiftPressed) {
                    if (currentPolygon.getSize() == 0) {
                            currentPolygon.addPoint(new Point(e.getX(), e.getY()));
                        } else {
                            startPoint = currentPolygon.getPoint(currentPolygon.getSize() - 1);
                        }
                    }
                 else {
                    startPoint = new Point(e.getX(), e.getY());
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (cuttingMode) {
                    pointsToCut.addPoint(new Point(e.getX(), e.getY()));
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    return;
                } else if (mode.equals("P") && !isShiftPressed) {
                    currentPolygon.addPoint(new Point(e.getX(), e.getY()));
                }else if(mode.equals("G") && startPoint != null){
                    int radius = (int) Math.sqrt(Math.pow(startPoint.getX() - e.getX(), 2) +
                            Math.pow(startPoint.getY() - e.getY(), 2));
                    RegularPentagon pentagon = new RegularPentagon(startPoint, radius);
                    polygons.add(pentagon);
                    redrawAllLinesAndPolygons();
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
                if (cuttingMode) {
                    currentPoint = new Point(e.getX(), e.getY());
                    panel.clear(0x000000);
                    redrawAllLinesAndPolygons();
                    if (cuttingPolygon.getSize() > 0) {
                        Point lastPoint = cuttingPolygon.getPoint(cuttingPolygon.getSize() - 1);
                        lineRasterizerTrivial.drawLine(lastPoint.getX(), lastPoint.getY(), currentPoint.getX(), currentPoint.getY());
                    }
                    panel.repaint();
                } else if (drawing && startPoint != null && !isShiftPressed && mode.equals("L")) {
                    currentPoint = new Point(e.getX(), e.getY());
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
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    mode = "X";
                    cuttingMode = true;
                    panel.clear(0x000000);
                    redrawAllLinesAndPolygons();
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = true;
                }  else if (e.getKeyCode() == KeyEvent.VK_L) {
                    mode = "L";
                    cuttingMode = false;
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    lineRasterizer.setColor(Color.RED);
                    mode = "P";
                    cuttingMode = false;
                    currentPolygon = new Polygon();
                    polygons.add(currentPolygon);
                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    panel.clear(0x000000);
                    polygons.clear();
                    currentPolygon = new Polygon();
                    polygons.add(currentPolygon);
                    linesMap.clear();
                    cuttingPolygon.deletePolygon();
                    pointsToCut.deletePolygon();
                    panel.repaint();
                }else if(e.getKeyCode() == KeyEvent.VK_G){
                    mode = "G";
                    cuttingMode = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    cuttingMode = false;
                    if (cuttingPolygon.getSize() > 2) {
                            panel.clear(0x000000);
                            for (Polygon polygon : polygons) {
                                if (polygon.getSize() > 2) {
                                    cutter.cut(cuttingPolygon, polygon);
                                }
                            }
                            pointsToCut.deletePolygon();
                            cuttingPolygon.deletePolygon();
                            panel.repaint();
                        }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    isShiftPressed = false;
                }
            }
        });
    }

    public void redrawAllLinesAndPolygons() {
        panel.clear(0x000000);

        for (Map.Entry<Integer, Point[]> entry : linesMap.entrySet()) {
            Point[] linePoints = entry.getValue();
            Point p1 = linePoints[0];
            Point p2 = linePoints[1];
            thickLineRasterizer.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        }

        for (Polygon polygon : polygons) {
            if (polygon.getSize() > 1) {
                polygonRasterizer.rasterize(polygon);
                new ScanLine(polygonRasterizer, lineRasterizer, polygon, Color.RED).fill();
            }
        }

        if (cuttingPolygon.getSize() > 1) {
            polygonRasterizer.setOutlineColor(Color.WHITE);
            polygonRasterizer.rasterize(cuttingPolygon);
        }

        panel.repaint();
    }

}
