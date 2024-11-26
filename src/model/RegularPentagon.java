package model;



public class RegularPentagon extends Polygon {
    private Point center;
    private int radius;

    public RegularPentagon(Point center, int radius) {
        this.center = center;
        this.radius = radius;
        generateVertices();
    }

    private void generateVertices() {
        int sides = 5;
        double angleStep = 2 * Math.PI / sides;

        for (int i = 0; i < sides; i++) {
            double angle = i * angleStep;
            int x = center.getX() + (int) (radius * Math.cos(angle));
            int y = center.getY() + (int) (radius * Math.sin(angle));
            addPoint(new Point(x, y));
        }
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point center) {
        this.center = center;
        generateVertices();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        generateVertices();
    }
}
