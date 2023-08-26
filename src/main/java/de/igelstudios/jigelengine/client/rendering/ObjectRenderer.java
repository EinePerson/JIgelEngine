package de.igelstudios.jigelengine.client.rendering;

import de.igelstudios.jigelengine.client.rendering.data.Model;
import de.igelstudios.jigelengine.client.rendering.data.RenderCache;
import de.igelstudios.jigelengine.client.rendering.renderers.Renderer;
import de.igelstudios.jigelengine.client.rendering.shader.Shader;
import de.igelstudios.jigelengine.client.rendering.shader.ShaderData;
import de.igelstudios.jigelengine.common.scene.Camera;
import de.igelstudios.jigelengine.common.scene.Light;
import de.igelstudios.jigelengine.common.scene.Scene;
import de.igelstudios.jigelengine.common.scene.SceneObject;
import de.igelstudios.jigelengine.common.util.Test;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class ObjectRenderer extends Renderer {

    public ObjectRenderer(Scene scene, Camera camera){
        super(scene,camera,new Shader(ShaderData.simple("default")));
    }

    @Override
    public void renderSub(Shader shader) {
        if(scene.isSkyDirty())glClearColor(scene.getSkyColor().x,scene.getSkyColor().y,scene.getSkyColor().z,1.0f);
        load(scene.getLight(),shader);
        shader.putFloat("density",scene.getFogDensity());
        shader.putFloat("gradient",scene.getFogGradient());
        if(scene.isSkyDirty()) shader.putVec3("sky",scene.getSkyColor());
        actCulling();
        scene.getObjs().forEach((s,o) -> render(s,o,shader));
        deActCulling();
        scene.getTobjs().forEach((s,o) -> render(s,o,shader));
    }

    public void render(String id,List<SceneObject> objs,Shader shader){
        Model model = RenderCache.get(id);
        glBindVertexArray(model.getMesh().getVao());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D,model.getTexture().getID());

        objs.forEach(object ->{
            shader.putMat("transform",object.getMat());
            glDrawElements(GL_TRIANGLES,object.getModel().getMesh().getVertexCount(),GL_UNSIGNED_INT,0);
        });

        glBindTexture(GL_TEXTURE_2D,0);
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
