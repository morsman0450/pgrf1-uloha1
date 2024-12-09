package solids;

import transforms.Mat4Identity;
import transforms.Point3D;

public class Cuboid extends Solid {

    public Cuboid(double width, double height, double depth) {

        // Geometrie
        vertexBuffer.add(new Point3D(-width / 2, -height / 2, depth / 2));  // 0
        vertexBuffer.add(new Point3D(width / 2, -height / 2, depth / 2));   // 1
        vertexBuffer.add(new Point3D(width / 2, height / 2, depth / 2));    // 2
        vertexBuffer.add(new Point3D(-width / 2, height / 2, depth / 2));   // 3

        vertexBuffer.add(new Point3D(-width / 2, -height / 2, -depth / 2)); // 4
        vertexBuffer.add(new Point3D(width / 2, -height / 2, -depth / 2));  // 5
        vertexBuffer.add(new Point3D(width / 2, height / 2, -depth / 2));   // 6
        vertexBuffer.add(new Point3D(-width / 2, height / 2, -depth / 2));  // 7

        // Topologie
        addIndices(0, 1, 1, 2, 2, 3, 3, 0); // Přední stěna
        addIndices(4, 5, 5, 6, 6, 7, 7, 4); // Zadní stěna
        addIndices(0, 4, 1, 5, 2, 6, 3, 7); // Spojovací hrany

        model = new Mat4Identity();
    }

}
