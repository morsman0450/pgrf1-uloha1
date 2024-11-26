package model.cut;

import model.Edge;
import model.Line;
import model.Point;
import model.Polygon;
import rasterizer.LineRasterizer;
import rasterizer.Raster;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cutter {
    private LineRasterizer rasterizer;
    private Raster raster;

    public Cutter(LineRasterizer rasterizer, Raster raster) {
        this.rasterizer = rasterizer;
        this.raster = raster;
    }

    public void cut(Polygon polyCutter, Polygon polyCut) {
        ArrayList<Point> cutPoints = polygonPoints(polyCutter);
        ArrayList<Point> polygonPoints = polygonPoints(polyCut);

        ArrayList<Point> result = new ArrayList<>();
           if(cutPoints.size()<polygonPoints.size()) {
               for(Point point:cutPoints){
                   if(polygonPoints.contains(point)){
                       result.add(point);
                   }
               }
           }else{
               for(Point point:polygonPoints){
                   if(cutPoints.contains(point)){
                       result.add(point);
                   }
               }
           }
        for(Point point:result){
            raster.setPixel(point.getX(), point.getY(), Color.GREEN.getRGB());
        }

    }

    private ArrayList<Point> polygonPoints(Polygon polygon) {
        ArrayList<Point> polygonPoints = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();

        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;

        for (int i = 0; i < polygon.getSize(); i++) {
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i + 1) % polygon.getSize());
            Edge edge = new Edge(p1, p2);
            if (!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
            yMin = Math.min(yMin, p1.getY());
            yMax = Math.max(yMax, p2.getY());
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
                for (int x = xStart; x <= xEnd; x++) {
                    polygonPoints.add(new Point(x, y));
                }
            }
        }

        return polygonPoints;
    }

}


