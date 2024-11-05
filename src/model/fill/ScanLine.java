package model.fill;

import model.Edge;
import model.Point;
import model.Polygon;
import rasterizer.LineRasterizer;
import rasterizer.LineRasterizerTrivial;
import rasterizer.PolygonRasterizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ScanLine implements Filler{

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

    private void scanLineFill(){
        List<Edge> edges = new ArrayList<>();

        //projdu body polygonu a pro kazde 2 body pridam hranu

        for(int i = 0; i < polygon.getSize(); i++){
            Point p1 = polygon.getPoint(i);
            Point p2 = polygon.getPoint((i+1) % polygon.getSize());

            Edge edge = new Edge(p1, p2);
            if(!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
        }

        int yMin = polygon.getPoint(0).getY();
        int yMax = yMin;

        //TODO : projet vsechy body a najit min a max Y
        // for cyklus (Poin p : polygon points)
        //podminka pro hledani minima
        //podminka pro hledani maxima
        for (int i = 0; i < polygon.getSize(); i++) {
            int y = polygon.getPoint(i).getY();
            if(y < yMin)
                yMin = y;
            if(y > yMax)
                yMax = y;
        }

        for(int y = yMin; y < yMax; y++){
            List<Integer> intersections = new ArrayList<>();

            for(Edge edge : edges){
                if(edge.intersectionExist(y)){
                    intersections.add(edge.getIntersection(y));
                }
                // TODO: existuje prusecnik?
                //edge.intersectionExist(y);

                Collections.sort(intersections);

                for (int i = 0; i < intersections.size(); i+= 2) {
                    int xStart = intersections.get(i);
                    int xEnd = intersections.get(i+1);

                }




                // TODO: pokud ano, tak ho spocitam
                // TODO: ulozim hodnotu do seznamu
            }



            // TODO: seradit zleva do prava

            // TODO: vykreslim usecku mezi kazdym lichym a sudym prusecnikem

        }

        // TODO: obtahnu hranici polygonu


    }
}
