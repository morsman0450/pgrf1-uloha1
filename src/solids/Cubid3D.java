package solids;

import transforms.Cubic;
import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Cubid3D extends Solid {
    private List<Point3D> controlPoints = new ArrayList<>();
    private List<Point3D> curvePoints = new ArrayList<>();
    private Color curveColor;

    public Cubid3D(String curveName) {
        switch (curveName) {
            case "Bezier":
                controlPoints.add(new Point3D(4, -1, 0)); // Začátek
                controlPoints.add(new Point3D(4.2, 0.5, -0.5)); // Zakřivení
                controlPoints.add(new Point3D(5.5, -0.3, 1.5)); // Zakřivení
                controlPoints.add(new Point3D(6, 1, 2)); // Konec
                createCurve(Cubic.BEZIER);
                curveColor = Color.PINK;
                break;

            case "Ferguson":
                controlPoints.add(new Point3D(4, -1, 2)); // Začátek
                controlPoints.add(new Point3D(4.8, 1.0, 1.8)); // Zakřivení
                controlPoints.add(new Point3D(5.2, -1.0, 0.2)); // Zakřivení
                controlPoints.add(new Point3D(6, 1, 0)); // Konec
                createCurve(Cubic.FERGUSON);
                curveColor = Color.GREEN;
                break;

            case "Coons":
                controlPoints.add(new Point3D(4, -1, 1)); // Začátek
                controlPoints.add(new Point3D(4.3, 0.3, 1.2)); // Zakřivení
                controlPoints.add(new Point3D(5.7, -0.3, 0.8)); // Zakřivení
                controlPoints.add(new Point3D(6, 1, 1)); // Konec
                createCurve(Cubic.COONS);
                curveColor = Color.ORANGE;
                break;
        }
        model = new Mat4Identity();
    }

    private void createCurve(Mat4 baseMatrix) {
        // Vytvoření kubické křivky s danými kontrolními body
        Cubic cubic = new Cubic(baseMatrix, controlPoints.toArray(new Point3D[0]));

        // Vytvoření seznamu bodů na křivce
        curvePoints = new ArrayList<>();

        // Vypočítání bodů s parametrem t
        for (double t = 0; t <= 1; t += 0.05) {
            curvePoints.add(cubic.compute(t));
        }

        vertexBuffer.addAll(curvePoints);

       // spojení bodů
        for (int i = 0; i < curvePoints.size() - 1; i++) {
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }
    }
    public Color getCurveColor() {
        return curveColor;
    }
}
