package de.igelstudios.jigelengine.client.rendering.terrain;

import de.igelstudios.jigelengine.client.rendering.data.DataLoader;
import de.igelstudios.jigelengine.client.rendering.data.Mesh;
import de.igelstudios.jigelengine.client.rendering.data.Texture;

public class Terrain {

    private static final int VERTEX_COUNT = 16;
    private static final float SIZE = VERTEX_COUNT * 6.25f;

    private float x,z;
    private Texture texture;
    private Texture[] tex;
    private Mesh mesh;

    public Terrain(int x,int z,Texture map,Texture bl,Texture r,Texture g,Texture b){
        this.x = SIZE * x;
        this.z = SIZE * z;
        this.mesh = generateTerrain();
        this.texture = map;
        tex = new Texture[]{bl,r,g,b};
    }

    private Mesh generateTerrain(){
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count*2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = (float) (Math.random() * 2 - 1);
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return DataLoader.loadVAO(vertices, textureCoords, normals, indices);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    public Texture[] getTex() {
        return tex;
    }

    public float getZ() {
        return z;
    }

    public float getX() {
        return x;
    }
}
