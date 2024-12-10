package solids;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;

public class Axes extends Solid {

    public Axes() {
        // Geometrie
        vertexBuffer.add(new Point3D(0, 0, 0));
        vertexBuffer.add(new Point3D(10, 0, 0));
        vertexBuffer.add(new Point3D(-10, 0, 0));
        vertexBuffer.add(new Point3D(0, 10, 0));
        vertexBuffer.add(new Point3D(0, -10, 0));
        vertexBuffer.add(new Point3D(0, 0, 10));
        vertexBuffer.add(new Point3D(0, 0, -10));

        // Topologie
        addIndices(0, 1);
        addIndices(0, 2);
        addIndices(0, 3);
        addIndices(0, 4);
        addIndices(0, 5);
        addIndices(0, 6);

        model = new Mat4Identity();
    }

    public Color getColorForAxis(int index) {
        switch (index) {
            case 1:
            case 2:
                return Color.RED;
            case 3:
            case 4:
                return Color.GREEN;
            case 5:
            case 6:
                return Color.BLUE;
            default:
                return Color.BLACK;
        }
    }
}

