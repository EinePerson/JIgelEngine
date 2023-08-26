package de.igelstudios.jigelengine.client.rendering.data;

import de.igelstudios.jigelengine.common.util.Numbers;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.assimp.Assimp.*;
import static org.lwjgl.assimp.Assimp.aiProcess_PreTransformVertices;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;

public class DataLoader {

    static Model loadModel(String id,String modelPath,String texPath){
        return loadModel(id,modelPath,texPath,aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals |
                aiProcess_CalcTangentSpace | aiProcess_PreTransformVertices);
    }

    private static Model loadModel(String id, String modelPath, String texPath, int i) {
        return new Model(id,loadMesh(modelPath,i),loadTexture(texPath));
    }

    private static Mesh loadMesh(String modelPath,int i){
        try (InputStream stream = DataLoader.class.getClassLoader().getResourceAsStream(modelPath)){
            if(stream == null)throw new IllegalArgumentException("The model " + modelPath + " could not be found");
            byte[] b = stream.readAllBytes();
            ByteBuffer bp = BufferUtils.createByteBuffer(b.length);
            bp.put(b);
            bp.flip();
            AIScene scene = aiImportFileFromMemory(bp,i, (ByteBuffer) null);
            if (scene == null) throw new RuntimeException("Could not load model" + modelPath);

            PointerBuffer pp = scene.mMeshes();
            AIMesh mesh = AIMesh.create(pp.get(0));
            float[] vertices = Numbers.toFArr(processVertices(mesh));
            float[] tex = Numbers.toFArr(processTexCoords(mesh));
            float[] normals = Numbers.toFArr(processNormals(mesh));
            int[] indecies = Numbers.toIArr(processIndicies(mesh));

            aiReleaseImport(scene);
            return loadVAO(vertices, tex,normals, indecies);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Texture loadTexture(String path){
        int tex = glGenTextures();
        GL40.glBindTexture(GL11.GL_TEXTURE_2D, tex);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        Texture.TextureInfo info = new Texture.TextureInfo();
        ByteBuffer img = loadImage(path,info);
        if (img != null){
            //if(info.ip2().get(0) == 3) GL40.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, info.ip0().get(0), info.ip1().get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img);
            //else if(info.ip2().get(0) == 4)
            GL40.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, info.ip0().get(0), info.ip1().get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img);
            stbi_image_free(img);
        }
        else throw new IllegalArgumentException("Could not load image:" + path);
        glGenerateMipmap(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR_MIPMAP_LINEAR);
        glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_LOD_BIAS,-1.0f);

        Texture texture = new Texture(path,tex);
        texture.setTransparent(info.ip2().get(0) >= 4);
        return texture;
    }

    public static ByteBuffer loadImage(String name, Texture.TextureInfo info){
        try (InputStream stream = Texture.class.getClassLoader().getResourceAsStream(name)){
            byte[] bytes = Objects.requireNonNull(stream).readAllBytes();
            ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.rewind();
            ByteBuffer img = STBImage.stbi_load_from_memory(byteBuffer, info.ip0(), info.ip1(), info.ip2(), 4);
            if(img == null)throw new RuntimeException("Could not load image " + name + " " + stbi_failure_reason());
            return img;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static boolean isTransparent(ByteBuffer bp) {
        boolean transp = false;
        for (int i = 0; i < bp.capacity(); i += 4) {
            if((0xFF & bp.get(i + 3)) < 255){
                transp = true;
                break;
            }
        }
        return transp;
    }

    private static int store(int i,int j,float[] f){
        int vbo = GL30.glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,vbo);
        glBufferData(GL_ARRAY_BUFFER,Numbers.store(f),GL_STATIC_DRAW);
        glVertexAttribPointer(i,j,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        return vbo;
    }

    private static int bindIndices(int[] i){
        int vbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,vbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Numbers.store(i),GL_STATIC_DRAW);
        return vbo;
    }

    public static Mesh loadVAO(float[] pos, float[] tex,float[] normals, int[] indices){
        int vao = getVAO();
        int vbo1 = bindIndices(indices);
        int vbo = store(0,3,pos);
        int vbo2 = store(1,2,tex);
        int vbo3 = store(2,3,normals);
        glBindVertexArray(0);
        //System.out.println(Arrays.toString(pos) + "\nA       A" + Arrays.toString(tex)  + "\nA       A" + Arrays.toString(normals) + "\nA       A" + Arrays.toString(indices));
        return new Mesh(vao,indices.length,vbo,vbo1,vbo2,vbo3);
    }

    private static int getVAO(){
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        return vao;
    }

    private static List<Integer> processIndicies(AIMesh aiMesh) {
        List<Integer> indices = new ArrayList<>();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        for (int i = 0; i < aiMesh.mNumFaces(); i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer ip = aiFace.mIndices();
            while (ip.remaining() > 0) indices.add(ip.get());

        }
        return indices;
    }

    private static List<Float> processTexCoords(AIMesh mesh) {
        List<Float> texCoords = new ArrayList<>();
        AIVector3D.Buffer aiTexCoords = mesh.mTextureCoords(0);
        int count = aiTexCoords != null ? aiTexCoords.remaining() : 0;
        for (int i = 0; i < count; i++) {
            AIVector3D texCoord = aiTexCoords.get();
            texCoords.add(texCoord.x());
            texCoords.add(1 - texCoord.y());
        }
        return texCoords;
    }

    private static List<Float> processVertices(AIMesh aiMesh) {
        List<Float> vertices = new ArrayList<>();
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        while (aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertices.add(aiVertex.x());
            vertices.add(aiVertex.y());
            vertices.add(aiVertex.z());
        }
        return vertices;
    }

    private static List<Float> processNormals(AIMesh mesh) {
        List<Float> normals = new ArrayList<>();

        AIVector3D.Buffer aiNormals = mesh.mNormals();
        while (aiNormals != null && aiNormals.remaining() > 0) {
            AIVector3D aiNormal = aiNormals.get();
            normals.add(aiNormal.x());
            normals.add(aiNormal.y());
            normals.add(aiNormal.z());
        }
        return normals;
    }
}
