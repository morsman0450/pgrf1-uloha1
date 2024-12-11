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
import javax.swing.Timer;

public class Controller3D implements Controller {
    private final Panel panel;
    private Raster raster;

    //renders
    private WiredRenderer wiredRenderer;
    private LineRasterizer lineRasterizer;

    // solids
    private Solid cube;
    private Solid animatedCube;
    private Solid axes;
    private Solid cuboid;
    private Solid pyramid;
    private Solid activeSolid;
    private Solid polygon3D;

    //curves
    private Cubid3D bezierCurve;
    private Cubid3D fergusonCurve;
    private Cubid3D coonsCurve;

    //camera
    private Camera camera;
    private final double cameraSpeed = 0.2;
    private boolean isFirstPerson = true;
    Mat4 proj;
    private int lastX = 0;
    private int lastY = 0;

    //animation
    private long lastTime = System.nanoTime();  // Čas poslední rotace
    private final double ROTATION_SPEED = 0.01; // Rychlost rotace
    private Timer animationTimer;


    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRasterImage();
                proj = new Mat4PerspRH(
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
        animationTimer = new Timer(33, new ActionListener() { //cca 30FPS
            @Override
            public void actionPerformed(ActionEvent e) {
                animateCubeRotation();
                renderScene();
            }
        });
        animationTimer.start();
    }

    @Override
    public void initObjects() {
        initCamera();
        axes = new Axes();
        cube = new Cube();
        animatedCube = new Cube();
        cuboid = new Cuboid(2, 2, 4);
        pyramid = new Pyramid();
        polygon3D = new Polygon3D();

        cube.translate(new Vec3D(5, 0, 1));
        animatedCube.translate(new Vec3D(0, 0, 0));
        cuboid.translate(new Vec3D(0,5,1));
        pyramid.translate(new Vec3D(0, 0, 5));
        polygon3D.translate(new Vec3D(5, 0, 5));

        // Curves
        bezierCurve = new Cubid3D("Bezier");
        fergusonCurve = new Cubid3D("Ferguson");
        coonsCurve = new Cubid3D("Coons");

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
                    case KeyEvent.VK_P:
                         proj = new Mat4PerspRH(
                                Math.toRadians(120),
                                (double) panel.getHeight() / panel.getWidth(),
                                0.1,
                                100
                        );
                        wiredRenderer.setProj(proj);
                         break;
                    case KeyEvent.VK_O:
                        proj = new Mat4OrthoRH(
                                50,
                                50,
                                0.5,
                                100
                        );
                        wiredRenderer.setProj(proj);
                        break;

                    case KeyEvent.VK_1:
                        activeSolid = cube;
                        break;

                    case KeyEvent.VK_2:
                        activeSolid= cuboid;
                        break;

                    case KeyEvent.VK_3:
                        activeSolid = pyramid;
                        break;

                    case KeyEvent.VK_4:
                        activeSolid = polygon3D;
                        break;

                    case KeyEvent.VK_5:
                        activeSolid = animatedCube;
                        break;

                    case KeyEvent.VK_6:
                        activeSolid = bezierCurve;
                        break;

                    case KeyEvent.VK_7:
                        activeSolid = fergusonCurve;
                        break;

                    case KeyEvent.VK_8:
                        activeSolid = coonsCurve;
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
                        break;// Zvětšení

                    case KeyEvent.VK_N:
                        activeSolid.scale(0.9, 0.9, 0.9);
                        break; // Zmenšení

                }
                wiredRenderer.setActiveSolid(activeSolid);
                renderScene();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - lastX;
                int deltaY = e.getY() - lastY;

                camera = camera.addAzimuth(deltaX * 0.0015);
                camera = camera.addZenith(deltaY * 0.0015);

                lastX = e.getX();
                lastY = e.getY();

                renderScene();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });
    }

    private void renderScene() {
        panel.clear();
        List<Solid> solids = new ArrayList<>();
        solids.add(axes);
        solids.add(cube);
        solids.add(animatedCube);
        solids.add(pyramid);
        solids.add(cuboid);
        solids.add(polygon3D);
        solids.add(bezierCurve);
        solids.add(fergusonCurve);
        solids.add(coonsCurve);
        wiredRenderer.setView(camera.getViewMatrix());
        wiredRenderer.renderSolids(solids);

        panel.repaint();
    }
    private void animateCubeRotation() {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1_000_000_000.0 ;
        lastTime = currentTime;

        double rotationAmount = ROTATION_SPEED * deltaTime * 360 * 0.1;

        animatedCube.rotate(Math.toRadians(rotationAmount), 0, 1);
    }
}
