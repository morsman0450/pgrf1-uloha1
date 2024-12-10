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
    private Solid activeSolid;

    //camera
    private Camera camera;
    private final double cameraSpeed = 0.2;
    private boolean isFirstPerson = true;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRasterImage();
                Mat4 proj = new Mat4PerspRH(
                Math.toRadians(120),
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
        axes = new Axes();
        cube = new Cube();
        cuboid = new Cuboid(2, 2, 4);
        pyramid = new Pyramid();

        cube.translate(new Vec3D(5, 0, 1));
        cuboid.translate(new Vec3D(0,5,1));
        pyramid.translate(new Vec3D(0, 0, 5));

        activeSolid = null;

    }

    private void initCamera() {
        camera = new Camera(
                new Vec3D(20, 20, 10),
                Math.PI,
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
                    case KeyEvent.VK_1:
                        activeSolid = cube;
                        break;

                    case KeyEvent.VK_2:
                        activeSolid= cuboid;
                        break;

                    case KeyEvent.VK_3:
                        activeSolid = pyramid;
                        break;

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

                    case KeyEvent.VK_UP:
                        activeSolid.translate(new Vec3D(0, 0, 1));
                        break;// Posun nahoru

                    case KeyEvent.VK_DOWN:
                        activeSolid.translate(new Vec3D(0, 0, -1));
                         break;// Posun dolů

                    case KeyEvent.VK_LEFT:
                        activeSolid.translate(new Vec3D(1, 0, 0));
                        break;// Posun vlevo

                    case KeyEvent.VK_RIGHT:
                        activeSolid.translate(new Vec3D(-1, 0, 0));
                        break;// Posun vpravo

                    case KeyEvent.VK_X:
                        activeSolid.rotate(Math.toRadians(10), 0, 0); //
                        break;// Rotace kolem X

                    case KeyEvent.VK_Y:
                        activeSolid.rotate(0, Math.toRadians(10), 0);
                        break;// Rotace kolem Y

                    case KeyEvent.VK_Z:
                        activeSolid.rotate(0, 0, Math.toRadians(10));
                        break;

                    case KeyEvent.VK_M:
                        activeSolid.scale(1.1, 1.1, 1.1);
                        break;// Zvýšení měřítka

                    case KeyEvent.VK_N:
                        activeSolid.scale(0.9, 0.9, 0.9);
                        break; // Snížení měřítka

                }
                wiredRenderer.setActiveSolid(activeSolid);
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

                    camera = camera.addAzimuth(deltaX * 0.0015);
                    camera = camera.addZenith(deltaY * 0.0015);

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
        solids.add(cuboid);

        wiredRenderer.setView(camera.getViewMatrix());
        wiredRenderer.renderSolids(solids);

        panel.repaint();
    }



}
