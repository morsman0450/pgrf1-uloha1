package rasterizer;

import model.Point;

public class LineSnapper {

    public static Point snapToNearestLine(Point start, Point current) {
        int dx = current.getX() - start.getX();
        int dy = current.getY() - start.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            if (Math.abs(dy) < Math.abs(dx) / 2) {
                return new Point(current.getX(), start.getY());
            } else {
                return new Point(current.getX(), start.getY() + (dx > 0 ? dy : -dy));
            }
        } else {
            if (Math.abs(dx) < Math.abs(dy) / 2) {
                return new Point(start.getX(), current.getY());
            } else {
                return new Point(start.getX() + (dy > 0 ? dx : -dx), current.getY());
            }
        }
    }
}
