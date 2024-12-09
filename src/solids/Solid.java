package solids;

import transforms.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solid {
    protected List<Point3D> vertexBuffer = new ArrayList<Point3D>();
    protected List<Integer> indexBuffer = new ArrayList<>();
    protected Mat4 model;

    // List colors
    // boolean isSelected
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
    public Color getColorForAxis(int index) {
        // Nastavení barvy podle indexu osy
        switch (index) {
            case 1: return Color.RED;   // Osa X - červená
            case 2: return Color.GREEN; // Osa Y - zelená
            case 3: return Color.BLUE;  // Osa Z - modrá
            default: return Color.WHITE;
        }
    }
}
