package model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private ArrayList<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point getPoint(int index) {
        return points.get(index);
    }

    public int getSize(){
        return points.size();
    }

    public void deletePolygon(){
        points.clear();
    }

    public List<Point> getPoints() {
        return new ArrayList<>(points);
    }

    public void setPoints(List<Point> points) {
        this.points.clear();
        this.points.addAll(points);
    }



}

