package engineTester;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

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

        float[] vertices = {
            // left bottom triangle
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            // right top triangle
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
            -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadToVAO(vertices);

        // main game loop -- runs until user requests to close the window
        while (!Display.isCloseRequested()) {
            renderer.prepare();

            // game logic
            // TODO

            // render
            renderer.render(model);
            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
