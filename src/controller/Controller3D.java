package controller;

import rasterizer.LineRasterizer;
import rasterizer.LineRasterizerGraphics;
import rasterizer.Raster;
import renderer.WiredRenderer;
import solids.Axes;
import solids.Cube;
import solids.Solid;
import view.Panel;

public class Controller3D implements Controller{
    private final Panel panel;
    private Raster raster;

    //renders
    private WiredRenderer wiredRenderer;
    private LineRasterizer lineRasterizer;

    // solids
    private Solid cube;
    private Solid axes;


    public Controller3D(Panel panel) {
        this.panel = panel;
        this.raster = panel.getRasterImage();

        wiredRenderer = new WiredRenderer(new LineRasterizerGraphics(raster),panel.getWidth(),panel.getHeight());

        initObjects();

        renderScene();
    }

    @Override
     public void initObjects(){
        cube = new Cube();
        axes = new Axes();

    }

    @Override
        public void initListener(){

    }

    private void renderScene(){
        panel.clear();

        wiredRenderer.renderSolid(cube);

        panel.repaint();
    }
}
