package engineTester;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

import java.io.File;

public class MainGameLoop {
    public static void setupLwjglNativePath() {
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

        while (!Display.isCloseRequested()) {
            // game logic
            // TODO

            // render
            DisplayManager.updateDisplay();
        }

        DisplayManager.closeDisplay();
    }
}
