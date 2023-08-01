package de.igelstudios.jigelengine.client.rendering.data;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class Mesh {
    private int vao;
    private int[] vbo;
    private int vertexI;

    Mesh(int vao,int vertexI,int ... vbo){
        this.vao = vao;
        this.vbo = vbo;
        this.vertexI = vertexI;
    }

    public int getVao() {
        return vao;
    }

    public int getVertexCount() {
        return vertexI;
    }

    void delete(){
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        for (int i : vbo) {
            glDeleteBuffers(i);
        }
    }
}
