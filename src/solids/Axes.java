package solids;

import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;

public class Axes extends Solid {

    public Axes() {
        int segments = 20; // Počet segmentů na každé ose
        double segmentLength = 1.0; // Délka každého segmentu

        // X-axis
        for (int i = -segments; i <= segments; i++) {
            vertexBuffer.add(new Point3D(i * segmentLength, 0, 0));
        }

        // Y-axis
        for (int i = -segments; i <= segments; i++) {
            vertexBuffer.add(new Point3D(0, i * segmentLength, 0));
        }

        // Z-axis
        for (int i = -segments; i <= segments; i++) {
            vertexBuffer.add(new Point3D(0, 0, i * segmentLength));
        }

        // Topologie - spojení bodů do linií
        int offsetX = 0;
        int offsetY = (2 * segments + 1);
        int offsetZ = (2 * segments + 1) * 2;

        for (int i = 0; i < 2 * segments; i++) {
            addIndices(offsetX + i, offsetX + i + 1); // X-axis
            addIndices(offsetY + i, offsetY + i + 1); // Y-axis
            addIndices(offsetZ + i, offsetZ + i + 1); // Z-axis
        }

        model = new Mat4Identity();
    }

    @Override
    public Color getColorForAxis(int index) {
        int totalSegments = (vertexBuffer.size() - 1) / 3;

        if (index < totalSegments) { // X-axis
            return Color.RED;
        } else if (index < 2 * totalSegments) { // Y-axis
            return Color.GREEN;
        } else { // Z-axis
            return Color.BLUE;
        }
    }


}

