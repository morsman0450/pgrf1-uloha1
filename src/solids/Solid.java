package solids;

import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solid {
    protected List<Point3D> vertexBuffer = new ArrayList<Point3D>();
    protected List<Integer> indexBuffer = new ArrayList<>();
    protected Mat4 model;
    // List colors
    // boolean isSelected


   protected void addIndices(Integer... indices) {
       indexBuffer.addAll(Arrays.asList(indices));
   }

    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }
}
