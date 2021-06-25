package engineTester;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
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
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

//        float[] vertices = {
//            -0.5f,0.5f,-0.5f,
//            -0.5f,-0.5f,-0.5f,
//            0.5f,-0.5f,-0.5f,
//            0.5f,0.5f,-0.5f,
//
//            -0.5f,0.5f,0.5f,
//            -0.5f,-0.5f,0.5f,
//            0.5f,-0.5f,0.5f,
//            0.5f,0.5f,0.5f,
//
//            0.5f,0.5f,-0.5f,
//            0.5f,-0.5f,-0.5f,
//            0.5f,-0.5f,0.5f,
//            0.5f,0.5f,0.5f,
//
//            -0.5f,0.5f,-0.5f,
//            -0.5f,-0.5f,-0.5f,
//            -0.5f,-0.5f,0.5f,
//            -0.5f,0.5f,0.5f,
//
//            -0.5f,0.5f,0.5f,
//            -0.5f,0.5f,-0.5f,
//            0.5f,0.5f,-0.5f,
//            0.5f,0.5f,0.5f,
//
//            -0.5f,-0.5f,0.5f,
//            -0.5f,-0.5f,-0.5f,
//            0.5f,-0.5f,-0.5f,
//            0.5f,-0.5f,0.5f
//
//        };
//
//        float[] textureCoords = {
//            0,0,
//            0,1,
//            1,1,
//            1,0,
//            0,0,
//            0,1,
//            1,1,
//            1,0,
//            0,0,
//            0,1,
//            1,1,
//            1,0,
//            0,0,
//            0,1,
//            1,1,
//            1,0,
//            0,0,
//            0,1,
//            1,1,
//            1,0,
//            0,0,
//            0,1,
//            1,1,
//            1,0
//        };
//
//        int[] indices = {
//            0,1,3,
//            3,1,2,
//            4,5,7,
//            7,5,6,
//            8,9,11,
//            11,9,10,
//            12,13,15,
//            15,13,14,
//            16,17,19,
//            19,17,18,
//            20,21,23,
//            23,21,22
//        };


//        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);

        RawModel model = OBJLoader.loadObjModel("stall", loader);

        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -5), 0, 0, 0, 1);

        Camera camera = new Camera();

        // main game loop -- runs until user requests to close the window
        while (!Display.isCloseRequested()) {
            // game logic
            // TODO
//            entity.translate(0, 0, -0.01f);
            entity.rotate(0, 1, 0);

            camera.move();

            // render
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
