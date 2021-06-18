package shaders;

import entities.Camera;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/main/resources/Vertex.shader";
    private static final String FRAGMENT_FILE = "src/main/resources/Fragment.shader";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {
        transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
        projectionMatrixLocation = super.getUniformLocation("projectionMatrix");
        viewMatrixLocation = super.getUniformLocation("viewMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(transformationMatrixLocation, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(viewMatrixLocation, toolbox.Math.createViewMatrix(camera));
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(projectionMatrixLocation, matrix);
    }
}
