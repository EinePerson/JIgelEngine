package de.igelstudios.jigelengine.client.rendering.data;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;

import java.nio.IntBuffer;

public class Texture {
    private String path;
    private int tex;
    private boolean transparent;

    Texture(String path,int tex){
        this.path = path;
        this.tex = tex;
    }

    public void bind(){
        GL40.glBindTexture(GL11.GL_TEXTURE_2D,tex);
    }

    public void unbind(){
        GL40.glBindTexture(GL11.GL_TEXTURE_2D,0);
    }

    public String getPath() {
        return path;
    }

    public int getID() {
        return tex;
    }

    public void delete() {
        unbind();
        GL40.glDeleteTextures(tex);
    }

    public boolean isTransparent() {
        return transparent;
    }

    void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public record TextureInfo(IntBuffer ip0, IntBuffer ip1, IntBuffer ip2){

        public TextureInfo(){
            this(BufferUtils.createIntBuffer(1),BufferUtils.createIntBuffer(1),BufferUtils.createIntBuffer(1));
        }
    }
}
