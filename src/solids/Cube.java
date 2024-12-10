package solids;

import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Cube extends Solid {
    private List<Point3D> curvePoints;

    public Cube() {
        // Geometrie krychle
        vertexBuffer.add(new Point3D(-1, -1, -1)); // 0
        vertexBuffer.add(new Point3D(1, -1, -1));  // 1
        vertexBuffer.add(new Point3D(1, 1, -1));   // 2
        vertexBuffer.add(new Point3D(-1, 1, -1));  // 3
        vertexBuffer.add(new Point3D(-1, -1, 1));  // 4
        vertexBuffer.add(new Point3D(1, -1, 1));   // 5
        vertexBuffer.add(new Point3D(1, 1, 1));    // 6
        vertexBuffer.add(new Point3D(-1, 1, 1));   // 7

        // Topologie krychle (hrany)
        addIndices(0, 1, 1, 2, 2, 3, 3, 0); // spodní čtverec
        addIndices(4, 5, 5, 6, 6, 7, 7, 4); // horní čtverec
        addIndices(0, 4, 1, 5, 2, 6, 3, 7); // propojení

        model = new Mat4Identity();
    }


}
