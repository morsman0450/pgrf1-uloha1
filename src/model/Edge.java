package model;

public class Edge {
   public Point p1, p2;


    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    public boolean isHorizontal() {
        return p1.y == p2.y;
    }

    public void orientate(){
        if(p1.getY() > p2.getY()){
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }
    }

    public boolean intersectionExist(int y){
        if(p1.getY() == p2.getY()){
            return false;
        }
        return y>=p1.getY() && y<p2.getY();
    }
    public int getIntersection(int y){
        double k = (double) (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
        double q = p1.getX() - k * p1.getY();
        return (int) (k * y + q);
    }

}
