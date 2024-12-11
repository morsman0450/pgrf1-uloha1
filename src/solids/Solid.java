package solids;

import transforms.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solid {
    protected List<Point3D> vertexBuffer = new ArrayList<>();
    protected List<Integer> indexBuffer = new ArrayList<>();
    protected Mat4 model;

    public Solid() {
        model = new Mat4();
    }


   protected void addIndices(Integer... indices) {
       indexBuffer.addAll(Arrays.asList(indices));
   }


    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }
    public void translate(Vec3D translation) {
        model = model.mul(new Mat4Transl(translation));
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }
    public void rotate(double angleX, double angleY, double angleZ) {
        model = model.mul(new Mat4RotX(angleX).mul(new Mat4RotY(angleY)).mul(new Mat4RotZ(angleZ)));
    }
    public void scale(double scaleX, double scaleY, double scaleZ) {
        model = model.mul(new Mat4Scale(scaleX, scaleY, scaleZ));
    }
    public Color getColorForAxis(int index) {
        switch (index) {
            case 1: return Color.RED;   // X
            case 2: return Color.GREEN; // Y
            case 3: return Color.BLUE;  // Z
            default: return Color.WHITE;
        }
    }
}
