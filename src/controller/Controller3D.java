package controller;

import rasterizer.LineRasterizer;
import rasterizer.LineRasterizerGraphics;
import rasterizer.Raster;
import renderer.WiredRenderer;
import solids.*;
import transforms.*;
import view.Panel;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller3D implements Controller {
    private final Panel panel;
    private Raster raster;

    //renders
    private WiredRenderer wiredRenderer;
    private LineRasterizer lineRasterizer;

    // solids
    private Solid cube;
    private Solid axes;
    private Solid cuboid;
    private Solid pyramid;

    //camera
    private Camera camera;
    private final double cameraSpeed = 0.2;
    private boolean isFirstPerson = false;



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
        initListener();
        renderScene();
    }

    @Override
    public void initObjects() {
        initCamera();
        cube = new Cube();
        cube.translate(new Vec3D(-3, 0, 0));

        cuboid = new Cuboid(2, 2, 4);
        pyramid = new Pyramid();
        pyramid.translate(new Vec3D(3, 0, 0));
        axes = new Axes();
    }

    private void initCamera() {
        camera = new Camera(
                new Vec3D(0, 0, 0),
                Math.PI, //180
                Math.PI * -0.125, //-22.5
                5,
                isFirstPerson
        );
    }

    @Override
    public void initListener() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        camera = camera.forward(cameraSpeed);
                        break;
                    case KeyEvent.VK_S:
                        camera = camera.backward(cameraSpeed);
                        break;
                    case KeyEvent.VK_A:
                        camera = camera.left(cameraSpeed);
                        break;
                    case KeyEvent.VK_D:
                        camera = camera.right(cameraSpeed);
                        break;
                }
                renderScene();
            }
        });


        // Mouse motion listener for camera rotation
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            private int lastX = 0;
            private int lastY = 0;

            @Override
            public void mouseMoved(MouseEvent e) {

                    System.out.println("Mouse moved");

                    int deltaX = e.getX() - lastX;
                    int deltaY = e.getY() - lastY;

                    camera = camera.addAzimuth(deltaX * 0.005);
                    camera = camera.addZenith(deltaY * 0.005);

                    lastX = e.getX();
                    lastY = e.getY();

                    renderScene();
                }

        });
        panel.setFocusable(true);
    }


    private void renderScene() {
        panel.clear();

        List<Solid> solids = new ArrayList<>();
        solids.add(axes);
        solids.add(cube);
        solids.add(pyramid);

        wiredRenderer.setView(camera.getViewMatrix());
        wiredRenderer.renderSolids(solids);

        panel.repaint();
    }

}
