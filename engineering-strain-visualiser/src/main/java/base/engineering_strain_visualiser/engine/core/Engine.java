package base.engineering_strain_visualiser.engine.core;

import base.engineering_strain_visualiser.engine.rendering.TransformedModel;
import base.engineering_strain_visualiser.engine.rendering.ModelLoader;
import base.engineering_strain_visualiser.engine.rendering.OriginalModel;
import base.engineering_strain_visualiser.engine.rendering.Renderer;
import base.engineering_strain_visualiser.engine.shaders.StaticShader;
import base.engineering_strain_visualiser.engine.window.Window;

public class Engine {

    private final Window window;
    Renderer renderer = new Renderer();
    ModelLoader modelLoader = new ModelLoader();
    private boolean running;
    TransformedModel heatmappedModel;
    String originalFilepath;
    String transformedFilepath;
    
    public Engine(String windowTitle, int width, int height, String originalFilepath, String transformedFilepath, IAppLogic appLogic) {
        window = new Window(windowTitle, width, height, true, appLogic);
        appLogic.init(window);
        this.originalFilepath = originalFilepath;
        this.transformedFilepath = transformedFilepath;
        running = true;
    }
    
    private void run() {
        OriginalModel originalModel = modelLoader.loadOriginalModel("src/res/models/" + originalFilepath + ".stl");
        TransformedModel transformedModel = modelLoader.loadTransformedModel("src/res/models/" + transformedFilepath + ".stl");
        
        //Halts Program if Vertex Counts are not Equal
        if(originalModel.getVertexCount() != transformedModel.getVertexCount()) {
        	System.err.println("Vertex counts are not equal");
        	System.exit(1);
        }        
        StaticShader shaderProgram = new StaticShader();

        //Engine Loop
        while (running && !window.closeRequest()) {
        	window.init();
        	shaderProgram.start();
        	renderer.render(transformedModel);
        	shaderProgram.stop();
        	window.update();
            window.waitEvents();
        }
        cleanUp();
    }
    
    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }
    
    private void cleanUp() {
        window.cleanUp();
    }
}