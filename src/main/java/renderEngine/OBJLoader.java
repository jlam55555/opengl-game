package renderEngine;

import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OBJLoader {

    public static RawModel loadObjModel(String fileName, Loader loader) {

        List<Vector3f> vertices = new ArrayList<>(),
            normals = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float[] verticesArray = null,
            normalsArray = null,
            textureArray = null;
        int[] indicesArray = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(
            new File("src/main/resources/" + fileName + ".obj")))) {

            // parse v, vt, vn lines
            String line;
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    vertices.add(new Vector3f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2]),
                        Float.parseFloat(currentLine[3])
                    ));
                } else if (line.startsWith("vt ")) {
                    textures.add(new Vector2f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2])
                    ));
                } else if (line.startsWith("vn ")) {
                    normals.add(new Vector3f(
                        Float.parseFloat(currentLine[1]),
                        Float.parseFloat(currentLine[2]),
                        Float.parseFloat(currentLine[3])
                    ));
                } else if (line.startsWith("f ")) {
                    textureArray = new float[vertices.size()*2];
                    normalsArray = new float[vertices.size()*3];
                    break;
                }
            }

            // parse f lines
            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }

                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        return loader.loadToVAO(verticesArray, textureArray, indicesArray);
    }

    private static void processVertex(String[] vertexData,
                                      List<Integer> indices,
                                      List<Vector2f> textures,
                                      List<Vector3f> normals,
                                      float[] textureArray,
                                      float[] normalsArray) {

        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer*2] = currentTexture.x;
        textureArray[currentVertexPointer*2+1] = 1 - currentTexture.y;

        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer*3] = currentNormal.x;
        normalsArray[currentVertexPointer*3+1] = currentNormal.y;
        normalsArray[currentVertexPointer*3+2] = currentNormal.z;
    }
}
