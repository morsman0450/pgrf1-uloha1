package solids;

import transforms.Point3D;

public class Axes extends Solid {

    public Axes() {
        // Geometrie
        vertexBuffer.add(new Point3D(0,0,0)); // 0
        vertexBuffer.add(new Point3D(1,0,0)); // 1 - X
        vertexBuffer.add(new Point3D(0,1,0)); // 2 - Y
        vertexBuffer.add(new Point3D(0,0,1)); // 3 - Z

        // Topologie
        addIndices(0,1);
        addIndices(0,2);
        addIndices(0,3);

    }
    //TODO: doma dokoncit.

}
