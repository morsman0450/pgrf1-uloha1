package renderer;

import model.Line;
import rasterizer.LineRasterizer;
import solids.Axes;
import solids.Cube;
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
    private Solid activeSolid;

    public WiredRenderer(LineRasterizer rasterizer, int width, int height, Mat4 view, Mat4 proj) {
        this.rasterizer = rasterizer;
        this.width = width;
        this.height = height;
        this.view = view;
        this.proj = proj;

    }

    public void renderSolid(Solid solid) {
        Mat4 mvp = new Mat4(solid.getModel()).mul(view).mul(proj);

        for (int i = 0; i < solid.getIndexBuffer().size(); i += 2) {
            int indexA = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            Point3D pointA = solid.getVertexBuffer().get(indexA);
            Point3D pointB = solid.getVertexBuffer().get(indexB);

            pointA = pointA.mul(mvp);
            pointB = pointB.mul(mvp);

            if (isInView(pointA, pointB)) {
                Point3D aDehomo = pointA.mul(1 / pointA.getW());
                Point3D bDehomo = pointB.mul(1 / pointB.getW());

                Vec3D pointAToWindows = transformToWindows(new Vec3D(aDehomo));
                Vec3D pointBToWindows = transformToWindows(new Vec3D(bDehomo));

                Line line = new Line(
                        (int) Math.round(pointAToWindows.getX()),
                        (int) Math.round(pointAToWindows.getY()),
                        (int) Math.round(pointBToWindows.getX()),
                        (int) Math.round(pointBToWindows.getY())
                );

                if (solid == activeSolid) {
                    rasterizer.setColor(Color.WHITE);
                } else if (solid instanceof Cube) {
                    rasterizer.setColor(Color.YELLOW);
                } else {
                    Color axisColor = solid.getColorForAxis(indexB);
                    rasterizer.setColor(axisColor);
                }

                rasterizer.rasterize(line);
            }
        }
    }





    private boolean isInView(Point3D pointA, Point3D pointB) {
        return pointA.getW() > 0 && pointB.getW() > 0 &&
                pointA.getX() > -pointA.getW() && pointA.getX() < pointA.getW() &&
                pointA.getY() > -pointA.getW() && pointA.getY() < pointA.getW() &&
                pointA.getZ() > 0 && pointA.getZ() < pointA.getW() &&
                pointB.getX() > -pointB.getW() && pointB.getX() < pointB.getW() &&
                pointB.getY() > -pointB.getW() && pointB.getY() < pointB.getW() &&
                pointB.getZ() > 0 && pointB.getZ() < pointB.getW();
    }


    private Vec3D transformToWindows(Vec3D v) {
        // A * Vec3D(1,-1,1)
        // A + Vec3D(1,1,0)
        // A * Vec3D((w-1)/2, (h-1)/2,1
        return v
                .mul(new Vec3D(1,-1,1) )
                .add(new Vec3D(1,1,0))
                .mul(new Vec3D((double) (width - 1) /2, (double) (height - 1) /2,1));
    }

    public void renderSolids(List<Solid> solids) {
        for(Solid solid : solids) {
            renderSolid(solid);
        }
    }
    public void setActiveSolid(Solid solid){
        this.activeSolid = solid;
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
