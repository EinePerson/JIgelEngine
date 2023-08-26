package de.igelstudios.jigelengine.client.rendering.terrain;

import de.igelstudios.jigelengine.client.rendering.Projection;
import de.igelstudios.jigelengine.client.rendering.data.Texture;
import de.igelstudios.jigelengine.client.rendering.renderers.Renderer;
import de.igelstudios.jigelengine.client.rendering.shader.Shader;
import de.igelstudios.jigelengine.client.rendering.shader.ShaderData;
import de.igelstudios.jigelengine.common.scene.Camera;
import de.igelstudios.jigelengine.common.scene.Light;
import de.igelstudios.jigelengine.common.scene.Scene;
import de.igelstudios.jigelengine.common.util.Matrix;
import de.igelstudios.jigelengine.common.util.Test;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class TerrainRenderer extends Renderer {
    private Terrain terrain;

    public TerrainRenderer(Terrain terrain, Scene scene, Camera camera){
        super(scene,camera,new Shader(ShaderData.simple("terrain")));
        this.terrain = terrain;
    }

    @Override
    protected void renderSub(Shader shader) {
        if(scene.isSkyDirty())glClearColor(scene.getSkyColor().x,scene.getSkyColor().y,scene.getSkyColor().z,1.0f);
        load(scene.getLight(),shader);
        shader.putVec3("ambLig",scene.getAmbientLig());
        shader.putFloat("density",scene.getFogDensity());
        shader.putFloat("gradient",scene.getFogGradient());
        if(scene.isSkyDirty()) shader.putVec3("sky",scene.getSkyColor());
        renderTerrain(shader);
    }

    private void renderTerrain(Shader shader) {
        glBindVertexArray(terrain.getMesh().getVao());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,terrain.getTexture().getID());
        shader.putInt("map",0);
        for (int i = 0; i < terrain.getTex().length; i++) {
            glActiveTexture(GL_TEXTURE1 + i);
            glBindTexture(GL_TEXTURE_2D,terrain.getTex()[i].getID());
        }
        shader.putInt("samplerbl",1);
        shader.putInt("samplerr",2);
        shader.putInt("samplerg",3);
        shader.putInt("samplerb",4);

        shader.putMat("transform", Matrix.transform(new Vector3f(terrain.getX(),0,terrain.getZ())));
        glDrawElements(GL_TRIANGLES,terrain.getMesh().getVertexCount(),GL_UNSIGNED_INT,0);

        //glBindTexture(GL_TEXTURE_2D,0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    @Test
    public void load(Light light,Shader shader){
        shader.putVec3("ligPos",light.getPosition());
        shader.putVec3("ligCol",light.getColor());
    }


    public void resize(int width,int height){
        projection.resize(width, height);
    }
}
