package de.igelstudios.jigelengine.client.rendering.shader;

import de.igelstudios.jigelengine.client.Client;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Objects;

import static org.lwjgl.opengl.GL46.*;

public class Shader {
    private int[] shaders;
    private int program;
    private boolean used = false;

    public Shader(ShaderData ... data){
        shaders = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            shaders[i] = load(data[i].type(),data[i].name());
        }
        program = glCreateProgram();
        for (int shader : shaders) {
            glAttachShader(program, shader);
        }
        glLinkProgram(program);
        glValidateProgram(program);

        int i = glGetProgrami(program, GL_LINK_STATUS);
        if (i == GL_FALSE) throw new IllegalStateException(getNames(data) + " Linking of shaders failed." + glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH)));

    }

    private static String getNames(ShaderData[] shader){
        String[] s = new String[shader.length];
        for (int i = 0; i < shader.length; i++) {
            s[i] = shader[i].name();
        }
        return Arrays.toString(s);
    }

    private static int load(int type,String name){
        try(InputStream stream = Objects.requireNonNull(Shader.class.getClassLoader().getResourceAsStream("shaders/" + name))) {
            int id = glCreateShader(type);
            glShaderSource(id,new String(stream.readAllBytes()));
            glCompileShader(id);
            int i = glGetShaderi(id, GL_COMPILE_STATUS);
            if (i == GL_FALSE) throw new IllegalStateException(name + " Shader compilation failed." + glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH)));

            return id;
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(){
        unUse();
        for (int shader : shaders) {
            glDetachShader(program,shader);
            glDeleteShader(shader);
        }
        glDeleteProgram(program);
    }

    public void putMat(String id, Matrix4f mat){
        int loc = glGetUniformLocation(program,id);
        use();
        FloatBuffer fp = BufferUtils.createFloatBuffer(16);
        mat.get(fp);
        glUniformMatrix4fv(loc,false,fp);
    }

    public void putVec4(String id, Vector4f vec){
        int loc = glGetUniformLocation(program,id);
        use();
        glUniform4f(loc,vec.x,vec.y,vec.z,vec.w);
    }

    public void putVec3(String id, Vector3f vec){
        int loc = glGetUniformLocation(program,id);
        use();
        glUniform3f(loc,vec.x,vec.y,vec.z);
    }

    public void putFloat(String id,float f){
        int loc = glGetUniformLocation(program,id);
        use();
        glUniform1f(loc,f);
    }

    public void putInt(String id,int i){
        int loc = glGetUniformLocation(program,id);
        use();
        glUniform1i(loc,i);
    }

    public void putTex(String id,int i){
        int loc = glGetUniformLocation(program,id);
        use();
        glUniform1i(loc,i);
    }

    public void putInt(String varName, int[] arr) {
        int pos = glGetUniformLocation(program, varName);
        use();
        glUniform1iv(pos, arr);
    }

    public void use(){
        if(used)return;
        glUseProgram(program);
        used = true;
    }

    public void unUse(){
        if(!used)return;
        glUseProgram(0);
        used = false;
    }

}
