package renderer;

import model.Line;
import rasterizer.LineRasterizer;
import solids.Solid;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WiredRenderer {

    private LineRasterizer rasterizer;
    private int width, height;
    private Mat4 view, proj;

    public WiredRenderer(LineRasterizer rasterizer, int width, int height, Mat4 view, Mat4 proj) {
        this.rasterizer = rasterizer;
        this.width = width;
        this.height = height;
        this.view = view;
        this.proj = proj;
    }

    public void renderSolid(Solid solid) {
        // iteruji pres seznam indexBufferu
        Mat4 mvp = new Mat4(solid.getModel()).mul(view).mul(proj); //MVP poradi
        for (int i = 0; i < solid.getIndexBuffer().size(); i+=2) {

            int indexA  = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            Point3D pointA = solid.getVertexBuffer().get(indexA);
            Point3D pointB = solid.getVertexBuffer().get(indexB);

            pointA = pointA.mul(mvp);
            pointB = pointB.mul(mvp);

            // orezani
            if(isInView(pointA,pointB)){
                //dehomogenizace
                Point3D aDehomoq = pointA.mul(1/pointA.getW());
                Point3D bDehomoq = pointB.mul(1/pointB.getW());


                //transformace do okna
                Vec3D pointAToWindows = transformToWindows(new Vec3D(aDehomoq));
                Vec3D pointBToWindows = transformToWindows(new Vec3D(bDehomoq));

                // Line
                Line line = new Line(
                        (int) Math.round(pointAToWindows.getX()),
                        (int) Math.round(pointAToWindows.getY()),
                        (int) Math.round(pointBToWindows.getX()),
                        (int) Math.round(pointBToWindows.getY())
                );
                // Line rasterizer
                rasterizer.setColor(Color.WHITE);
                rasterizer.rasterize(line);
                }

        }


    }

    private boolean isInView(Point3D pointA, Point3D pointB) {
//      všechna x jsou vetši než -w a
//      všechna x jsou menší než w a
//      všechna y sjou větší než -w a
//      všechna y jsou menší než w a
//      všechna z jsou větší než 0 a
//      všecna z jsou vštší než w
        if(
                pointA.getX()> -pointA.getW() &&
                pointA.getX()< pointA.getW() &&
                pointA.getY() > -pointA.getW() &&
                pointA.getY() < pointA.getW() &&
                pointA.getZ() > 0 &&
                pointA.getZ() < pointA.getW() &&
                        pointB.getX()> -pointB.getW() &&
                        pointB.getX()< pointB.getW() &&
                        pointB.getY() > -pointB.getW() &&
                        pointB.getY() < pointB.getW() &&
                        pointB.getZ() > 0 &&
                        pointB.getZ() < pointB.getW()


        ){
            return true;
        }


        return true;
    }

    private Vec3D transformToWindows(Vec3D v) {
        // A * Vec3D(1,-1,1)
        // A + Vec3D(1,1,0)
        // A * Vec3D((w-1)/2, (h-1)/2,1
        return v
                .mul(new Vec3D(1,-1,1) )
                .add(new Vec3D(1,1,0))
                .mul(new Vec3D((width-1)/2, (height-1)/2,1));
    }

    public void renderSolids(List<Solid> solids) {


        for(Solid solid : solids) {
            renderSolid(solid);
        }

    }

    public void setRasterizer(LineRasterizer rasterizer) {
        this.rasterizer = rasterizer;
    }

    public Mat4 getView() {
        return view;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public Mat4 getProj() {
        return proj;
    }

    public void setProj(Mat4 proj) {
        this.proj = proj;
    }
}
