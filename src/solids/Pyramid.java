package solids;

import transforms.Mat4Identity;
import transforms.Point3D;


public class Pyramid extends Solid {

    public Pyramid() {
        // Geometrie
        vertexBuffer.add(new Point3D(0, 0, 1));     // 0 - Vrchol
        vertexBuffer.add(new Point3D(-1, -1, -1)); // 1
        vertexBuffer.add(new Point3D(1, -1, -1));  // 2
        vertexBuffer.add(new Point3D(1, 1, -1));   // 3
        vertexBuffer.add(new Point3D(-1, 1, -1));  // 4

        // Topologie
        addIndices(1, 2, 2, 3, 3, 4, 4, 1);
        addIndices(0, 1, 0, 2, 0, 3, 0, 4);

        model = new Mat4Identity();
    }
}
