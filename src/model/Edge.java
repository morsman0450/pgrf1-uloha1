package model;

public class Edge {
   public Point p1, p2;


    public Edge(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

//    zjisti, zda je hrana vodorovna
//    @return true pokud je vodorovna, jinak false.

    public boolean isHorizontal() {
        return p1.y == p2.y;
    }

    public void orientate(){
        // TODO:
        // podle y1 a y2 -> prohodit
        if(p1.getY() > p2.getY()){
            Point temp = p1;
            p1 = p2;
            p2 = temp;
        }
    }

    public boolean intersectionExist(int y){

        //Zjistim, zda existuje pruseecnikscanLine s hranou
        //@param y - y-ova souradnice.
        // @return true pokud prusecnik existuje
        // TODO: dokoncit
        if(p1.getY() == p2.getY()){
            return false;
        }
        return y>=p1.getY() && y<p2.getY();
    }
    //Vypocita a vrati x-ovu souradnici prusecniku
    // @ param y y-ova souradnice
    //
    public int getIntersection(int y){
        // TODO: dokoncit
        double k = (double) (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
        double q = p1.getX() - k * p1.getY();
        return (int) (k * y + q);
    }

}
