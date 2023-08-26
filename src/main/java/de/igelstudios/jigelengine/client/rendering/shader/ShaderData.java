package de.igelstudios.jigelengine.client.rendering.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public record ShaderData(String name, int type) {

    public static ShaderData[] simple(String name){
        return new ShaderData[]{new ShaderData(name + ".vert",GL_VERTEX_SHADER),new ShaderData(name + ".frag",GL_FRAGMENT_SHADER)};
    }
}
