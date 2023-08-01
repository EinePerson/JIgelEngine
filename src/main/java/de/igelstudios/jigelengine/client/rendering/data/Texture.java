package de.igelstudios.jigelengine.client.rendering.data;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;

public class Texture {
    public static final Vector2f[][] TEX_COORDS = {
            new Vector2f[]{
                    new Vector2f(0, 0),
                    new Vector2f(0, 1),
                    new Vector2f(1, 1),
                    new Vector2f(1, 0),
            },
            new Vector2f[]{
                    new Vector2f(1, 0),
                    new Vector2f(0, 0),
                    new Vector2f(0, 1),
                    new Vector2f(1, 1),
            },
            new Vector2f[]{
                    new Vector2f(1, 1),
                    new Vector2f(1, 0),
                    new Vector2f(0, 0),
                    new Vector2f(0, 1),
            },
            new Vector2f[]{
                    new Vector2f(0, 1),
                    new Vector2f(1, 1),
                    new Vector2f(1, 0),
                    new Vector2f(0, 0),
            }
    };
    private String path;
    private int tex;

    Texture(String path,int tex){
        this.path = path;

        this.tex = tex;
        /*GL40.glBindTexture(GL11.GL_TEXTURE_2D, tex);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL40.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        TextureInfo info = new TextureInfo();
        ByteBuffer img = read(path,info);
        if (img != null){
            if(info.ip2.get(0) == 3) GL40.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, info.ip0.get(0), info.ip1.get(0), 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, img);
            else if(info.ip2.get(0) == 4)GL40.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, info.ip0.get(0), info.ip1.get(0), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, img);
            stbi_image_free(img);
        }
        else throw new IllegalArgumentException("Could not load image:" + this.path);*/
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

    }

    public record TextureInfo(IntBuffer ip0, IntBuffer ip1, IntBuffer ip2){

        public TextureInfo(){
            this(BufferUtils.createIntBuffer(1),BufferUtils.createIntBuffer(1),BufferUtils.createIntBuffer(1));
        }
    }
}
