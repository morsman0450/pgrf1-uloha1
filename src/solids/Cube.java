package solids;

import transforms.Point3D;

public class Cube extends Solid {

    public Cube() {
        //Geometrie
        vertexBuffer.add(new Point3D(-1,-1,1)); //0
        vertexBuffer.add(new Point3D(1,-1,1));//1
        vertexBuffer.add(new Point3D(1,1,1));//2
        vertexBuffer.add(new Point3D(-1,1,1));//3
        //Topologie
        addIndices(0,1,1,2,2,3,3,0);
    }
}
