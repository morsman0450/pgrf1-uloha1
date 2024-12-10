package solids;

import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Cubid3D extends Solid{
    private List<Point3D> points = new ArrayList<>();

    public Cubid3D(String curveName) {
        switch(curveName){
            case "Bezier":
                points.add(new Point3D(0,1,0));
                points.add(new Point3D(0,-1,0));
                points.add(new Point3D(1,0,0));
                points.add(new Point3D(1,1,0));
                break;
        }
    }
    private void createCurve(Mat4 matrix){
        //new Cubic()

        // compute()
            //vb.add
            //ib.add
    }
}
