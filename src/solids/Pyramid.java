package solids;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;

public class Pyramid extends Solid {

    public Pyramid() {
        // Geometrie
        vertexBuffer.add(new Point3D(0, 0, 1));     // 0: Vrchol jehlanu
        vertexBuffer.add(new Point3D(-1, -1, -1)); // 1: Spodní levý přední
        vertexBuffer.add(new Point3D(1, -1, -1));  // 2: Spodní pravý přední
        vertexBuffer.add(new Point3D(1, 1, -1));   // 3: Spodní pravý zadní
        vertexBuffer.add(new Point3D(-1, 1, -1));  // 4: Spodní levý zadní

        // Topologie
        addIndices(1, 2, 2, 3, 3, 4, 4, 1);

        // Spojení vrcholu s body základny
        addIndices(0, 1, 0, 2, 0, 3, 0, 4);

        model = new Mat4Identity();
    }

    @Override
    public Color getColorForAxis(int index) {
        return Color.MAGENTA;
    }
}
