package renderer;

import model.Line;
import rasterizer.LineRasterizer;
import solids.Solid;
import transforms.Point3D;
import transforms.Vec3D;


import java.util.List;

public class WiredRenderer {

    private LineRasterizer rasterizer;
    private int width, height;

    public WiredRenderer(LineRasterizer rasterizer, int height, int width) {
        this.rasterizer = rasterizer;
        this.height = height;
        this.width = width;
    }

    public void renderSolid(Solid solid) {
        // iteruji pres seznam indexBufferu
        for (int i = 0; i < solid.getIndexBuffer().size(); i+=2) {

            int indexA  = solid.getIndexBuffer().get(i);
            int indexB = solid.getIndexBuffer().get(i + 1);

            Point3D pointA = solid.getVertexBuffer().get(indexA);
            Point3D pointB = solid.getVertexBuffer().get(indexB);

            //transformace do okna
            Vec3D pointAToWindows = transformToWindows(new Vec3D(pointA));
            Vec3D pointBToWindows = transformToWindows(new Vec3D(pointB));

            // Line
            Line line = new Line(
                    (int) Math.round(pointAToWindows.getX()),
                    (int) Math.round(pointAToWindows.getY()),
                    (int) Math.round(pointBToWindows.getX()),
                    (int) Math.round(pointBToWindows.getY())
            );
            // Line rasterizer
            rasterizer.rasterize(line);
        }


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
        // TODO: dokoncit doma
        // for-each
            // renderSolid(solid);
        for(Solid solid : solids) {
            renderSolid(solid);
        }

    }

    public void setRasterizer(LineRasterizer rasterizer) {
        this.rasterizer = rasterizer;
    }
}
