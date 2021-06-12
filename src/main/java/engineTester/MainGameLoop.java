package engineTester;

import models.TexturedModel;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.io.File;

public class MainGameLoop {

    /**
     * tells lwjgl where to find the native libraries
     */
    private static void setupLwjglNativePath() {
        // see: https://stackoverflow.com/a/30347873/2397327
        File JGLLib;
        switch (LWJGLUtil.getPlatform()) {
            case LWJGLUtil.PLATFORM_WINDOWS:
                JGLLib = new File("./native/windows/");
                break;
            case LWJGLUtil.PLATFORM_LINUX:
                JGLLib = new File("./native/linux/");
                break;
            default:
            case LWJGLUtil.PLATFORM_MACOSX:
                JGLLib = new File("./native/macosx/");
                break;
        }
        System.setProperty("org.lwjgl.librarypath", JGLLib.getAbsolutePath());
    }

    public static void main(String[] args) {
        setupLwjglNativePath();

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
            -0.5f, 0.5f, 0f,    // v0
            -0.5f, -0.5f, 0f,   // v1
            0.5f, -0.5f, 0f,    // v2
            0.5f, 0.5f, 0f      // v3
        };

        int[] indices = {
            0, 1, 3,            // triangle 1
            3, 1, 2             // triangle 2
        };

        float[] textureCoords = {
            0, 0,               // v0
            0, 1,               // v1
            1, 1,               // v2
            1, 0                // v3
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("texture"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        // main game loop -- runs until user requests to close the window
        while (!Display.isCloseRequested()) {
            // game logic
            // TODO

            // render
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
