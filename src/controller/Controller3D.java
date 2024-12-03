package controller;

import rasterizer.LineRasterizer;
import rasterizer.LineRasterizerGraphics;
import rasterizer.Raster;
import renderer.WiredRenderer;
import solids.Axes;
import solids.Cube;
import solids.Solid;
import transforms.Camera;
import transforms.Mat4;
import transforms.Mat4PerspRH;
import transforms.Vec3D;
import view.Panel;

import java.util.ArrayList;
import java.util.List;

public class Controller3D implements Controller{
    private final Panel panel;
    private Raster raster;

    //renders
    private WiredRenderer wiredRenderer;
    private LineRasterizer lineRasterizer;

    // solids
    private Solid cube;
    private Solid axes;

    //camera
    private Camera camera;
    private final double cameraSpeed= 0.5;
    private boolean isFirstPeson = false;


    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRasterImage();
        Mat4 proj = new Mat4PerspRH(
                Math.toRadians(90),
                (double) panel.getHeight() / panel.getWidth(),
                0.1,
                100
                );

        lineRasterizer = new LineRasterizerGraphics(raster);

         wiredRenderer = new WiredRenderer(
              lineRasterizer,
               panel.getWidth(),
               panel.getHeight(),
               new Mat4(),
               proj

       );

        initObjects();

        renderScene();
    }

    @Override
     public void initObjects(){
        initCamera();
        cube = new Cube();
        axes = new Axes();

    }
    private void initCamera(){
        camera = new Camera(
                new Vec3D(0,0,0),
                Math.PI, //180
                Math.PI * -0.125, //-22.5
                5,
                isFirstPeson
        );
    }

    @Override
        public void initListener(){

    }

    private void renderScene(){
        panel.clear();

        List<Solid> solids = new ArrayList<>();
        solids.add(axes);
        solids.add(cube);

        wiredRenderer.setView(camera.getViewMatrix());
        wiredRenderer.renderSolids(solids);

        panel.repaint();
    }
}
