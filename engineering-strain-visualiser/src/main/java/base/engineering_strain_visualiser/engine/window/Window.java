package base.engineering_strain_visualiser.engine.window;

import base.engineering_strain_visualiser.engine.core.IAppLogic;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.opengl.GL;

public class Window {

    private final long windowHandle;

    public Window(String title, int width, int height, boolean compatibleProfile, IAppLogic appLogic) {
    	
    	//GLFW Initialisation and Error-Handling
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        
        if (compatibleProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }
        
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
        
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        
        glfwMakeContextCurrent(windowHandle);
        glfwShowWindow(windowHandle);
        GL.createCapabilities();
    }
    
    public void init() {
    	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    	glClearColor(0.2f, 0.2f, 0.2f, 0); //Dark Grey Background
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
    }
    
    public void waitEvents() {
        glfwWaitEvents();
    }

    public boolean closeRequest() {
        return glfwWindowShouldClose(windowHandle);
    }
    
    public void cleanUp() {
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }
}