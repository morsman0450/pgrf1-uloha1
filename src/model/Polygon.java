package model;

import java.util.ArrayList;

public class Polygon {

    private ArrayList<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Point GetPoint(int index) {
        return points.get(index);
    }

    public int getSize(){
        return points.size();
    }

    public void deletePolygon(){
        points.clear();
    }
}
