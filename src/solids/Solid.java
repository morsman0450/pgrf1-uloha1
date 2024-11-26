package solids;

import transforms.Point3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solid {
    protected List<Point3D> vertexBuffer = new ArrayList<Point3D>();
   protected List<Integer> indexBuffer = new ArrayList<>();

   protected void addIndices(Integer... indices) {
       indexBuffer.addAll(Arrays.asList(indices));
   }
}
