package model.fill;

import model.Edge;
import model.Point;
import model.Polygon;
import rasterizer.LineRasterizer;
import rasterizer.PolygonRasterizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLine implements Filler {

    private LineRasterizer lineRasterizer;
    private Polygon polygon;
    private PolygonRasterizer polygonRasterizer;

    public ScanLine(PolygonRasterizer polygonRasterizer, LineRasterizer lineRasterizer, Polygon polygon) {
        this.polygonRasterizer = polygonRasterizer;
        this.lineRasterizer = lineRasterizer;
        this.polygon = polygon;
    }

    @Override
    public void fill() {
        scanLineFill();
    }

    private void scanLineFill() {
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < polygon.getSize(); i++) {
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i + 1) % polygon.getSize());

            Edge edge = new Edge(p1, p2);
            if (!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
        }

        int yMin = polygon.getPoint(0).getY();
        int yMax = yMin;

        for (int i = 0; i < polygon.getSize(); i++) {
            int y = polygon.getPoint(i).getY();
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
        }

        for (int y = yMin; y < yMax; y++) {
            List<Integer> intersections = new ArrayList<>();

            for (Edge edge : edges) {
                if (edge.intersectionExist(y)) {
                    intersections.add(edge.getIntersection(y));
                }
            }


            Collections.sort(intersections);

            for (int i = 0; i < intersections.size() - 1; i += 2) {
                int xStart = intersections.get(i);
                int xEnd = intersections.get(i + 1);
                lineRasterizer.rasterize(xStart,y,xEnd,y);
            }
        }

        polygonRasterizer.rasterize(polygon);
    }
}
