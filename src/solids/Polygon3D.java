package solids;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;

public class Polygon3D extends Solid {
    public Polygon3D() {
        vertexBuffer.add(new Point3D(1, 1, 1)); //0
        vertexBuffer.add(new Point3D(1, -1, 1)); //1
        vertexBuffer.add(new Point3D(-1, -1, 1)); //2
        vertexBuffer.add(new Point3D(-1, 1, 1)); //3
        vertexBuffer.add(new Point3D(1, 1, -1)); //4
        vertexBuffer.add(new Point3D(1, -1, -1)); //5
        vertexBuffer.add(new Point3D(-1, -1, -1)); //6
        vertexBuffer.add(new Point3D(-1, 1, -1)); //7


        addIndices(0, 1, 2);  // 1. trojúhelník
        addIndices(0, 2, 3);  // 2. trojúhelník
        addIndices(4, 5, 6);  // 3. trojúhelník
        addIndices(4, 6, 7);  // 4. trojúhelník
        addIndices(0, 1, 5);  // 5. trojúhelník
        addIndices(0, 5, 4);  // 6. trojúhelník
        addIndices(1, 2, 6);  // 7. trojúhelník
        addIndices(1, 6, 5);  // 8. trojúhelník
        addIndices(2, 3, 7);  // 9. trojúhelník
        addIndices(2, 7, 6);  // 10. trojúhelník
        addIndices(3, 0, 4);  // 11. trojúhelník
        addIndices(3, 4, 7);  // 12. trojúhelník

        model = new Mat4Identity();
    }
}
