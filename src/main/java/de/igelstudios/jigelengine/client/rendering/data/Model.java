package de.igelstudios.jigelengine.client.rendering.data;

public class Model {
    private Mesh mesh;
    private Texture texture;
    private String id;

    Model(String id,Mesh mesh,Texture texture){
        this.id  = id;
        this.mesh = mesh;
        this.texture = texture;
    }

    public void delete() {
        mesh.delete();
        texture.delete();
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getId() {
        return id;
    }
}
